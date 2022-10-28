package com.deliysuf.newtddd.vievModelTest

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.deliysuf.newtddd.MainCoroutineRule
import com.deliysuf.newtddd.getOrAwaitValueTest
import com.deliysuf.newtddd.repo.FakeArtRepository
import com.deliysuf.newtddd.util.Status
import com.deliysuf.newtddd.viewModel.ArtViewModel
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@ExperimentalCoroutinesApi
class ArtViewModelTest {
@get:Rule
var instantExecutorRule=InstantTaskExecutorRule()


    @get:Rule
    val mainCoroutineRule=MainCoroutineRule()
    private lateinit var viewModel:ArtViewModel

    @Before
    fun setup(){
        viewModel= ArtViewModel(FakeArtRepository())
    }
    @Test
    fun `insert art without year Func`(){
        viewModel.makeArt("monaLisa","davinci","")
      val value= viewModel.insertArtMassage.getOrAwaitValueTest()
       assertThat(value.status).isEqualTo(Status.ERROR)
    }
    @Test
    fun `insert art without name Func`(){
        viewModel.makeArt("","davinci","12345")
        val value= viewModel.insertArtMassage.getOrAwaitValueTest()
        assertThat(value.status).isEqualTo(Status.ERROR)
    }
    @Test
    fun `insert art without artistName Func`(){
        viewModel.makeArt("monaLisa","","222")
        val value= viewModel.insertArtMassage.getOrAwaitValueTest()
        assertThat(value.status).isEqualTo(Status.ERROR)
    }
}