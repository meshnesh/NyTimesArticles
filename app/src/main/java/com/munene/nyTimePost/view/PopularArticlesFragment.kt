package com.munene.nyTimePost.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.munene.nyTimePost.R
import com.munene.nyTimePost.databinding.FragmentHomeBinding
import com.munene.nyTimePost.helper.EndPoints
import com.munene.nyTimePost.helper.Resource
import com.munene.nyTimePost.helper.Utility
import com.munene.nyTimePost.model.PopularArticle
import com.munene.nyTimePost.view.adapter.PopularArticlesAdapter
import com.munene.nyTimePost.view.base.RecyclerItemClickListener
import com.munene.nyTimePost.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class PopularArticlesFragment : Fragment(), RecyclerItemClickListener {

    private lateinit var binding: FragmentHomeBinding

    private val mainViewModel: MainViewModel by viewModels()
    private lateinit var popularArticlesAdapter: PopularArticlesAdapter
    private lateinit var articlesRecyclerView: RecyclerView

    lateinit var popularArticle: PopularArticle

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)

        popularArticlesAdapter = PopularArticlesAdapter(requireContext(), this)
        articlesRecyclerView = binding.articlesRecyclerView
        articlesRecyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(
                context,
                LinearLayoutManager.VERTICAL,
                false
            )
            adapter = popularArticlesAdapter
        }

        //make progress bar visible
        binding.prgLoading.visibility = View.VISIBLE

        val apiKey = EndPoints.ARTICLES_API_KEY

        mainViewModel.getPopularArticles()

        fetchPopularArticles()

        return binding.root
    }

    private fun fetchPopularArticles() {

        Timber.d("PopularArticlesFragment")


        if (!Utility.isNetworkAvailable(requireContext())) {
            Snackbar.make(binding.mainLayout, "You are not connected to the internet", Snackbar.LENGTH_LONG)
                .withColor(ContextCompat.getColor(requireContext(), R.color.dark_red))
                .setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                .show()
        }

        //fetch the playlist
        else {
            mainViewModel.articleResponseData.observe(this, { result ->
                when (result.status) {
                    Resource.Status.SUCCESS -> {
                        binding.articleResponse = result.data

                        Timber.d("PlayList Result ${result.data?.results}")

                        popularArticlesAdapter.updatePopularArticles(result.data?.results as ArrayList<PopularArticle>)

                        //stop progress bar
                        binding.prgLoading.visibility = View.GONE
                    }
                    Resource.Status.ERROR -> {

                        val layout = binding.mainLayout
                        Snackbar.make(layout, "Oopps! Something went wrong, Try again", Snackbar.LENGTH_LONG)
                            .withColor(ContextCompat.getColor(requireContext(), R.color.dark_red))
                            .setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                            .show()
                        //stop progress bar
                        binding.prgLoading.visibility = View.GONE
                    }

                    Resource.Status.LOADING -> {
                        //stop progress bar
                        binding.prgLoading.visibility = View.VISIBLE
                    }
                }
            })
        }
    }

    private fun Snackbar.withColor(@ColorInt colorInt: Int): Snackbar {
        this.view.setBackgroundColor(colorInt)
        return this
    }

    override fun onItemClicked(data: Any?) {
        popularArticle = data as PopularArticle
        Snackbar.make(binding.mainLayout, "You clicked ${popularArticle.title}", Snackbar.LENGTH_LONG).show()

        findNavController().navigate(R.id.action_homeFragment_to_articleDetailFragment)
    }
}