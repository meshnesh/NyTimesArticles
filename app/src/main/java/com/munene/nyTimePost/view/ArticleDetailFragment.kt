package com.munene.nyTimePost.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.android.material.appbar.AppBarLayout
import com.munene.nyTimePost.R
import com.munene.nyTimePost.databinding.FragmentArticleDetailBinding
import com.munene.nyTimePost.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import kotlin.math.abs

@AndroidEntryPoint
class ArticleDetailFragment : Fragment() {

    val args: ArticleDetailFragmentArgs by navArgs()

    lateinit var binding: FragmentArticleDetailBinding
    private val mainViewModel: MainViewModel by viewModels()

    private var imageUrl: String? = ""
    private var isHideToolbarView = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_article_detail, container, false)

        // binding.appbar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset -> {
        //         val maxScroll: Int = appBarLayout.totalScrollRange
        //         val percentage = abs(0).toFloat() / maxScroll.toFloat()
        //         if (percentage == 1f && isHideToolbarView) {
        //             binding.dateBehavior.visibility = View.GONE
        //             binding.titleAppbar.visibility = View.VISIBLE
        //             isHideToolbarView = !isHideToolbarView
        //         } else if (percentage < 1f && !isHideToolbarView) {
        //             binding.dateBehavior.visibility = View.VISIBLE
        //             binding.titleAppbar.visibility = View.GONE
        //             isHideToolbarView = !isHideToolbarView
        //         }
        //     }
        // })

        binding.appbar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->

            when {
                //  State Expanded
                verticalOffset == 0 -> {

                    binding.dateBehavior.visibility = View.VISIBLE
                    binding.titleAppbar.visibility = View.GONE
                    isHideToolbarView = !isHideToolbarView
                }//  Do anything for Expanded State
                //  State Collapsed
                abs(verticalOffset) >= appBarLayout.totalScrollRange -> {
                    binding.dateBehavior.visibility = View.GONE
                    binding.titleAppbar.visibility = View.VISIBLE
                    isHideToolbarView = !isHideToolbarView
                }//  Do anything for Collapse State
                else -> {
                    binding.dateBehavior.visibility = View.VISIBLE
                    binding.titleAppbar.visibility = View.GONE
                    isHideToolbarView = !isHideToolbarView
                }//  Do anything for Ideal State
            }
        })

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.viewModel = mainViewModel

        Timber.d("Passed args $args")

        when {
            args.popularArticle != null -> {
                mainViewModel.articleTitle.value = args.popularArticle!!.title
                mainViewModel.articleTime.value = args.popularArticle!!.updated
                mainViewModel.articleSource.value = args.popularArticle!!.source
                mainViewModel.articleDate.value = args.popularArticle!!.published_date
                mainViewModel.articleAuthor.value = args.popularArticle!!.byline
                mainViewModel.articleUrl.value = args.popularArticle!!.url
                imageUrl = args.popularArticle!!.media[0].mediaMetadata[2].url
                initWebView(args.popularArticle!!.url)
            }
        }

        Glide.with(this)
            .load(imageUrl)
            .centerCrop()
            .placeholder(R.drawable.sound_cloud)
            .into(binding.backdrop)
    }

    private fun initWebView(url: String) {
        val webView = binding.webView
        webView.settings.loadsImagesAutomatically = true
        webView.settings.javaScriptEnabled = true
        webView.settings.domStorageEnabled = true
        webView.settings.setSupportZoom(true)
        webView.settings.builtInZoomControls = true
        webView.settings.displayZoomControls = false
        webView.scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY
        webView.webViewClient = WebViewClient()
        webView.loadUrl(url)
    }
}