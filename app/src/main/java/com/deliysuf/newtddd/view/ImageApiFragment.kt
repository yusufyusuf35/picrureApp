package com.deliysuf.newtddd.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.deliysuf.newtddd.R
import com.deliysuf.newtddd.adapter.ImageRecyclerAdapter
import com.deliysuf.newtddd.databinding.FragmentImageApiBinding
import com.deliysuf.newtddd.util.Status
import com.deliysuf.newtddd.viewModel.ArtViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class ImageApiFragment @Inject constructor( private val imageRecyclerAdapter: ImageRecyclerAdapter):Fragment(R.layout.fragment_image_api) {

   lateinit var viewModel:ArtViewModel
   private lateinit var fragmentBinding:FragmentImageApiBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel=ViewModelProvider(requireActivity()).get(ArtViewModel::class.java)
       val binding= FragmentImageApiBinding.bind(view)
        fragmentBinding=binding
        var job: Job?=null

        binding.searcText.addTextChangedListener {
            job?.cancel()
            job=lifecycleScope.launch{
                delay(1000)
                it?.let {
                    if(it.toString().isNotEmpty()){
                        viewModel.searchForImage(it.toString())
                    }
                }


             }

        }

        subscribeToObserver()
        binding.imageRecyclerAdapter.adapter=imageRecyclerAdapter
        binding.imageRecyclerAdapter.layoutManager=GridLayoutManager(requireContext(),3)
         imageRecyclerAdapter.setOnItemClickListener {
             findNavController().popBackStack()
             viewModel.setSelectedImage(it)
         }
    }
    private fun subscribeToObserver(){
        viewModel.imageList.observe(viewLifecycleOwner, Observer {
            when(it.status){
                Status.SUCCESS-> {
                    val url= it.data?.hits?.map {imageResult ->
                       imageResult.previewURL
                    }
                    imageRecyclerAdapter.images=url?: listOf()
                    fragmentBinding.ProgressBar.visibility=View.GONE
                }

                Status.ERROR->{
                    Toast.makeText(requireContext(), "${ it.message } : Error",Toast.LENGTH_LONG).show()
                    fragmentBinding.ProgressBar.visibility=View.GONE
                }

                Status.LOADING->{
                    fragmentBinding.ProgressBar.visibility=View.VISIBLE
                }
            }


        })
    }
}