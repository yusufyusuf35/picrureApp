package com.deliysuf.newtddd.dependencyinjection

import android.content.Context
import androidx.room.Room
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.deliysuf.newtddd.R
import com.deliysuf.newtddd.api.RetrofitAPI
import com.deliysuf.newtddd.repo.ArtRepository
import com.deliysuf.newtddd.repo.ArtRepositoryInterface
import com.deliysuf.newtddd.roomdb.ArtDao
import com.deliysuf.newtddd.roomdb.ArtDatabase
import com.deliysuf.newtddd.util.Util.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun injectRoomDatabase
                (@ApplicationContext context:Context)= Room.
    databaseBuilder(context,ArtDatabase::class.
    java,"ArtBookDB").build()

    @Singleton
    @Provides
    fun injectDao(database: ArtDatabase)=database.artDao()

    @Singleton
    @Provides
    fun injectRetrofitApi():RetrofitAPI=Retrofit.Builder().
    baseUrl(BASE_URL).
    addConverterFactory(GsonConverterFactory.create()).build()
        .create(RetrofitAPI::class.java)


    @Singleton
    @Provides
    fun injectNormalRepo(dao: ArtDao,api:RetrofitAPI)=ArtRepository(dao,api) as ArtRepositoryInterface

    @Singleton
    @Provides
    fun injectGlide(@ApplicationContext context: Context) = Glide
        .with(context).setDefaultRequestOptions(
            RequestOptions().placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_foreground)
        )

}