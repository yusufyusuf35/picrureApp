package com.deliysuf.newtddd.view

import androidx.fragment.app.FragmentFactory
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.filters.MediumTest
import com.deliysuf.newtddd.R
import com.deliysuf.newtddd.launchFragmentInHiltContainer
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
class ArtFragmentTest {
    @get:Rule
    var hiltirule=HiltAndroidRule(this)

    @Inject
    lateinit var fragmentFactory:ArtFragmentFActory

    @Before
    fun setup(){
        hiltirule.inject()
    }
    @Test
    fun testNavigationFragment(){

        val navController= Mockito.mock(NavController::class.java)
        launchFragmentInHiltContainer<ArtFragment>(

            factory = fragmentFactory ){

            Navigation.setViewNavController(requireView(),navController)
        }

       Espresso.onView(ViewMatchers.withId(R.id.fab)).perform(ViewActions.click())
            Mockito.verify(navController).navigate(ArtFragmentDirections.actionArtFragment2ToArtDetailedFragment2())

    }
}