package com.deliysuf.newtddd.dependencyinjection

import android.content.Context
import androidx.room.Room
import com.deliysuf.newtddd.roomdb.ArtDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TestAppModule {

    @Singleton
    @Provides
    @Named("testDb")
    fun InjectRoomDb(@ApplicationContext context:Context)=
        Room.
        inMemoryDatabaseBuilder(context,ArtDatabase::class.java).
        allowMainThreadQueries().build()
}