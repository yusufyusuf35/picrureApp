package com.deliysuf.newtddd.roomdp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room

import androidx.test.core.app.ApplicationProvider
import com.deliysuf.newtddd.HiltTestRunner
import com.deliysuf.newtddd.getOrAwaitValue
import com.deliysuf.newtddd.roomdb.Art
import com.deliysuf.newtddd.roomdb.ArtDao
import com.deliysuf.newtddd.roomdb.ArtDatabase
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

@ExperimentalCoroutinesApi
@HiltAndroidTest
class ArtDaoTest {
    @get:Rule
    var instantTestExecutorRule=InstantTaskExecutorRule()
    @get:Rule
    var hiltRule=HiltAndroidRule(this)

    @Inject
    @Named("testDb")
    lateinit var database: ArtDatabase


    private lateinit var dao:ArtDao
    @Before
    fun setup(){
        /*database= Room.
        inMemoryDatabaseBuilder(ApplicationProvider.
        getApplicationContext(),ArtDatabase::class.java)
            .allowMainThreadQueries().build()*/
     hiltRule.inject()
        dao=database.artDao()
    }
    @After
    fun tearDown(){
        database.close()
    }

    @Test
    fun insertArtTesting()= runTest {
       val  art=Art("monalisa","Davinci",1258,"dsfsd",1)

        dao.insertArt(art)
        val value=dao.observeArts().getOrAwaitValue()
       assertThat(value).contains(art)
    }
  @Test
  fun deleteArtTesting()= runTest {
      val art=Art("monalisa","Davinci",1258,"dsfsd",1)
      dao.insertArt(art)
      dao.deleteArt(art)
     val value= dao.observeArts().getOrAwaitValue()
      assertThat(value).doesNotContain(art)
  }




}