package com.deliysuf.newtddd.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.deliysuf.newtddd.R
import com.deliysuf.newtddd.databinding.ArtRowBinding
import com.deliysuf.newtddd.databinding.FragmentArtBinding
import com.deliysuf.newtddd.roomdb.Art
import javax.inject.Inject

class ArtRecyclerAdapter @Inject constructor(val glide:RequestManager): RecyclerView.Adapter<ArtRecyclerAdapter.ViewHolder>() {


    class ViewHolder(view:View):RecyclerView.ViewHolder(view)



    private val diffUtil=object : DiffUtil.ItemCallback<Art>() {
        override fun areItemsTheSame(oldItem: Art, newItem: Art): Boolean {
            return oldItem==newItem
        }

        override fun areContentsTheSame(oldItem: Art, newItem: Art): Boolean {
            return oldItem==newItem
        }

    }
    private val recyclerDiffer=AsyncListDiffer(this,diffUtil)
   var arts:List<Art>
    get() = recyclerDiffer.currentList
    set(value) = recyclerDiffer.submitList(value)



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view =LayoutInflater.from(parent.context).inflate(R.layout.art_row,parent,false)

        return ViewHolder(view)
    }



    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
      val artName=holder.itemView.findViewById<TextView>(R.id.artRowNameText)
      val artisName=holder.itemView.findViewById<TextView>(R.id.artRowArtistNameText)
      val year=holder.itemView.findViewById<TextView>(R.id.artRowYearText)
        val imageArt=holder.itemView.findViewById<ImageView>(R.id.artRowImageView)
      val art=arts[position]

holder.itemView.apply {
    artName.text="Name: ${art.artName}"
    artisName.text="Artist Name:${art.artistName}"
    year.text="Year:${art.year}"
    glide.load(art.imageUrl).into(imageArt)
}

    }

    override fun getItemCount(): Int {
return arts.size
    }


}
