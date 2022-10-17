package com.deliysuf.newtddd.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.deliysuf.newtddd.R
import com.deliysuf.newtddd.adapter.ArtRecyclerAdapter
import com.deliysuf.newtddd.databinding.FragmentArtBinding
import com.deliysuf.newtddd.viewModel.ArtViewModel
import javax.inject.Inject

class ArtFragment @Inject constructor(
     val artRecyclerAdapter: ArtRecyclerAdapter
): Fragment(R.layout.fragment_art) {
    private  var fragmenBinding:FragmentArtBinding?=null
   lateinit var viewModel: ArtViewModel





    private val swipeCallBack=object :ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
           return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val layoutPosition = viewHolder.layoutPosition
            val selectedArt=artRecyclerAdapter.arts[layoutPosition]
            viewModel.deleteArt(selectedArt)
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding=FragmentArtBinding.bind(view)

        //viewModel=ViewModelProvider(requireActivity()).get(ArtViewModel::class.java)
        fragmenBinding=binding
        subscribeObserver()
        binding.recyclerViewArt.adapter=artRecyclerAdapter
        binding.recyclerViewArt.layoutManager=LinearLayoutManager(requireContext())

        binding.fab.setOnClickListener{
            findNavController().navigate(ArtFragmentDirections.actionArtFragment2ToArtDetailedFragment2())
        }
    }
    private fun subscribeObserver(){
        viewModel.artList.observe(viewLifecycleOwner, Observer {
             artRecyclerAdapter.arts=it
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        fragmenBinding=null
    }
}