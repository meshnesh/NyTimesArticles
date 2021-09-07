package com.munene.nyTimePost.viewmodel

import org.junit.Test

class MainViewModelTest {

    private fun createViewModel(
        venueId: String = "fake_id",
        mainRepo: MainRepo
    ) = MainViewModel(mainRepo)
}