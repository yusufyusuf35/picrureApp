package com.deliysuf.newtddd.view

import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.RequestManager
import com.deliysuf.newtddd.R
import com.deliysuf.newtddd.databinding.FragmentArtDetailsBinding
import com.deliysuf.newtddd.roomdb.Art
import com.deliysuf.newtddd.util.Status
import com.deliysuf.newtddd.viewModel.ArtViewModel
import javax.inject.Inject

class ArtDetailedFragment @Inject constructor( val glide:RequestManager):Fragment(R.layout.fragment_art_details) {
    private var fragmentBinding:FragmentArtDetailsBinding?=null
      lateinit var viewModel:ArtViewModel
    lateinit var art:Art
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding=FragmentArtDetailsBinding.bind(view)
        fragmentBinding=binding
        viewModel=ViewModelProvider(requireActivity()).get(ArtViewModel::class.java)
        subscribeViewModelObserver()
        binding.ArtImageView.setOnClickListener {
            findNavController().navigate(ArtDetailedFragmentDirections.actionArtDetailedFragment2ToImageApiFragment2())
        }
        val callback=object :OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }

        }
    requireActivity().onBackPressedDispatcher.addCallback(callback)


        binding.artSaveButton.setOnClickListener{
          val artName= binding.artNameText.text.toString()
            val artistName=binding.artistNameText.text.toString()
            val year=binding.artYearText.text.toString()

            viewModel.makeArt(artName,artistName,year)
        }

    }
   private fun subscribeViewModelObserver(){
       viewModel.selectedImageUrl.observe(viewLifecycleOwner, Observer {url ->

           fragmentBinding?.let {

                   glide.load(url).into(it.ArtImageView)

           }
       })
       viewModel.insertArtMassage.observe(viewLifecycleOwner, Observer {
         when(it.status){
             Status.SUCCESS->{
                 Toast.makeText(requireContext(),"Success",Toast.LENGTH_LONG).show()
                 findNavController().popBackStack()
                 viewModel.resetInsertArtMassage()
             }
             Status.ERROR->{
                 Toast.makeText(requireContext(),"${it.message} : Error",Toast.LENGTH_LONG).show()


             }
             Status.LOADING -> {}
         }
       })
   }


    override fun onDestroyView() {
        super.onDestroyView()
        fragmentBinding=null
    }
}