package com.deliysuf.newtddd.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.deliysuf.newtddd.model.ImageResponse
import com.deliysuf.newtddd.roomdb.Art
import com.deliysuf.newtddd.util.Resource

class FakeArtRepositoryTest:ArtRepositoryInterface {

    private val arts= mutableListOf<Art>()
    private val artLiveData=MutableLiveData<List<Art>>(arts)
    override suspend fun insertArt(art: Art) {
       arts.add(art)
        refreshData()
    }

    override suspend fun deleteArt(art: Art) {
          arts.remove(art)
        refreshData()
    }

    override fun getArt(): LiveData<List<Art>> {
return artLiveData    }

    override suspend fun searchImage(imageString: String): Resource<ImageResponse> {
       return Resource.success(ImageResponse(listOf(),0,0))
    }

    private fun refreshData(){
        artLiveData.value=arts
    }
}