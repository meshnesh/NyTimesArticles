package com.munene.nyTimePost.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.munene.nyTimePost.databinding.ActivityHomeBinding
import com.munene.nyTimePost.model.PopularArticle
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    lateinit var popularArticle: PopularArticle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }
}