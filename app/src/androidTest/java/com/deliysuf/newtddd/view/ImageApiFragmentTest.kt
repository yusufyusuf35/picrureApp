package com.deliysuf.newtddd.view

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.filters.MediumTest
import com.deliysuf.newtddd.R
import com.deliysuf.newtddd.adapter.ImageRecyclerAdapter
import com.deliysuf.newtddd.getOrAwaitValue
import com.deliysuf.newtddd.launchFragmentInHiltContainer
import com.deliysuf.newtddd.repo.FakeArtRepositoryTest
import com.deliysuf.newtddd.viewModel.ArtViewModel
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import javax.inject.Inject


@MediumTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class ImageApiFragmentTest {
    @get:Rule
    var hiltRule=HiltAndroidRule(this)
    @get:Rule
    var instantTaskExecutorRule=InstantTaskExecutorRule()
     var selectedImage="yusufpqu@outlook.com"
    @Inject
    lateinit var fragmentFactory:ArtFragmentFActory
    @Before
    fun setup()
    {
        hiltRule.inject()
    }
    @Test
    fun selectImage(){
        val navController=Mockito.mock(NavController::class.java)
        val testViewModel=ArtViewModel(FakeArtRepositoryTest())
        launchFragmentInHiltContainer<ImageApiFragment>(factory = fragmentFactory)
        {
            Navigation.setViewNavController(requireView(),navController)

         viewModel=testViewModel
            imageRecyclerAdapter.images= listOf(selectedImage)
        }

        Espresso.onView(ViewMatchers.
        withId(R.id.imageRecyclerAdapter)).
        perform(RecyclerViewActions.
        actionOnItemAtPosition<ImageRecyclerAdapter.ViewHolder>(0,
            click()
        ))
        Mockito.verify(navController).popBackStack()
        assertThat(testViewModel.selectedImageUrl.getOrAwaitValue()).isEqualTo(selectedImage)
    }
}