package com.deliysuf.newtddd.viewModel

import android.text.Editable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deliysuf.newtddd.model.ImageResponse
import com.deliysuf.newtddd.repo.ArtRepositoryInterface
import com.deliysuf.newtddd.roomdb.Art
import com.deliysuf.newtddd.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ArtViewModel  @Inject constructor(
    private val repository:ArtRepositoryInterface

) :ViewModel() {

    val artList=repository.getArt()

    private val images=MutableLiveData<Resource<ImageResponse>>()
    val imageList:LiveData<Resource<ImageResponse>>
    get() =images

    private val selectedImage=MutableLiveData<String>()
    val selectedImageUrl:LiveData<String>
    get() = selectedImage

    private var insertArtMsg=MutableLiveData<Resource<Art>>()
    val insertArtMassage:LiveData<Resource<Art>>
    get() = insertArtMsg

    fun resetInsertArtMassage(){
        insertArtMsg=MutableLiveData<Resource<Art>>()
    }

    fun setSelectedImage(url:String){
        selectedImage.value=url
    }
    fun deleteArt(art: Art)=viewModelScope.launch {
        repository.deleteArt(art)
    }
    fun insertArt(art: Art)=viewModelScope.launch {
        repository.insertArt(art)
    }

    fun makeArt(name:String,artistName:String,year:String){
         if(name.isEmpty()||artistName.isEmpty()||year.isEmpty()){
             insertArtMsg.value=Resource.error("Enter name Artist and year"
                 ,null)
             return
         }
        val yearInt=try {
             year.toInt()

        }catch (e:java.lang.Exception){
            insertArtMsg.value=Resource.error("you must enter int",null)
        return
        }
         val art=Art(name,artistName, yearInt ,selectedImage.value?:"",)
       insertArt(art)
        setSelectedImage("")
        insertArtMsg.value=Resource.success(art)

    }


    fun searchForImage(searchString: String) {
        if (searchString.isEmpty()) {
            return
        }

            images.value = Resource.loading(null)
            viewModelScope.launch {
                val response = repository.searchImage(searchString)
                images.value = response
            }


        }


    override fun onCleared() {
        super.onCleared()

    }


}