package com.deliysuf.newtddd.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.bumptech.glide.RequestManager
import com.deliysuf.newtddd.adapter.ArtRecyclerAdapter
import com.deliysuf.newtddd.adapter.ImageRecyclerAdapter
import javax.inject.Inject

class ArtFragmentFActory @Inject constructor(
    private val artRecyclerAdapter: ArtRecyclerAdapter,
    val glide:RequestManager,
    private val imageRecyclerAdapter: ImageRecyclerAdapter):FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {

        return when(className) {
            ArtFragment::class.java.name->ArtFragment(artRecyclerAdapter)
            ArtDetailedFragment::class.java.name -> ArtDetailedFragment(glide)
            ImageApiFragment::class.java.name->ImageApiFragment(imageRecyclerAdapter)
            else->return super.instantiate(classLoader, className)}





    }
}