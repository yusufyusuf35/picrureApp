package com.deliysuf.newtddd.view

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.MediumTest
import com.deliysuf.newtddd.R
import com.deliysuf.newtddd.getOrAwaitValue
import com.deliysuf.newtddd.launchFragmentInHiltContainer
import com.deliysuf.newtddd.repo.FakeArtRepositoryTest
import com.deliysuf.newtddd.roomdb.Art
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
class ArtDetailFragmentTest {
    @get:Rule
     var hiltRule=HiltAndroidRule(this)
    @get:Rule
    var instantTaskExecutorRule=InstantTaskExecutorRule()


    @Inject
    lateinit var fragmentFactory: ArtFragmentFActory
    @Before
    fun setup(){
        hiltRule.inject()
    }
    @Test
    fun testNavigationArtDetails(){
        val navController=Mockito.mock(NavController::class.java)

        launchFragmentInHiltContainer<ArtDetailedFragment>(

            factory = fragmentFactory)
        {
            Navigation.setViewNavController(requireView(),navController)

        }

       Espresso.onView(ViewMatchers.withId(R.id.ArtImageView)).perform(ViewActions.click())

        Mockito.verify(navController).
        navigate(ArtDetailedFragmentDirections.
        actionArtDetailedFragment2ToImageApiFragment2())

        Espresso.pressBack()
        Mockito.verify(navController).popBackStack()

    }
      @Test
      fun testSave(){
          val testViewModel=ArtViewModel(FakeArtRepositoryTest())
          launchFragmentInHiltContainer<ArtDetailedFragment>(factory = fragmentFactory)
          {
              viewModel=testViewModel
          }
          Espresso.onView(withId(R.id.artNameText)).perform(ViewActions.replaceText("mona lisa"))
          Espresso.onView(withId(R.id.artistNameText)).perform(ViewActions.replaceText("davinci"))
          Espresso.onView(withId(R.id.artYearText)).perform(ViewActions.replaceText("152"))
          Espresso.onView(withId(R.id.artSaveButton)).perform(ViewActions.click())

          assertThat(testViewModel.artList.getOrAwaitValue()).contains(Art("mona lisa","davinci",152,""))
      }
}