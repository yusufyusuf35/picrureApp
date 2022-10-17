package com.deliysuf.newtddd.repo

import androidx.lifecycle.LiveData
import com.deliysuf.newtddd.api.RetrofitAPI
import com.deliysuf.newtddd.model.ImageResponse
import com.deliysuf.newtddd.roomdb.Art
import com.deliysuf.newtddd.roomdb.ArtDao
import com.deliysuf.newtddd.util.Resource
import javax.inject.Inject

class ArtRepository @Inject constructor(private val artDao:ArtDao,

                        private val retrofitAPI: RetrofitAPI               ):ArtRepositoryInterface {
    override suspend fun insertArt(art: Art) {
        artDao.insertArt(art)
    }

    override suspend fun deleteArt(art: Art) {
artDao.deleteArt(art)    }

    override fun getArt(): LiveData<List<Art>> {
     return artDao.observeArts()
    }

    override suspend fun searchImage(imageString: String): Resource<ImageResponse> {
       return try {
           val response=retrofitAPI.imageSearch(imageString)
           if (response.isSuccessful){
               response.body()?.let {
                   return@let Resource.success(it)
               }?:Resource.error("error",null)
           }else{
               Resource.error("error",null)
           }
       }
       catch (e:java.lang.Exception){
           Resource.error("error",null)
       }
    }
}