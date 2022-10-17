package com.deliysuf.newtddd.repo

import androidx.lifecycle.LiveData
import com.deliysuf.newtddd.model.ImageResponse
import com.deliysuf.newtddd.roomdb.Art
import com.deliysuf.newtddd.util.Resource

interface ArtRepositoryInterface {
    suspend fun insertArt(art:Art)
    suspend fun deleteArt(art: Art)
    fun getArt():LiveData<List<Art>>
    suspend fun searchImage(imageString:String):Resource<ImageResponse>
}