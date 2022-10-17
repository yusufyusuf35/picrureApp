package com.deliysuf.newtddd.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.deliysuf.newtddd.R
import com.deliysuf.newtddd.roomdb.Art
import javax.inject.Inject

class ImageRecyclerAdapter @Inject constructor(val glide:RequestManager): RecyclerView.Adapter<ImageRecyclerAdapter.ViewHolder>() {
    class ViewHolder(view: View):RecyclerView.ViewHolder(view) {

    }

    private var onItemClickListener:((String)->Unit)?=null
    private val diffUtil=object : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem==newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem==newItem
        }

    }
    private val recyclerDiffer= AsyncListDiffer(this,diffUtil)
    var images:List<String>
        get() = recyclerDiffer.currentList
        set(value) = recyclerDiffer.submitList(value)

    fun setOnItemClickListener(listener:((String)->Unit)){
        onItemClickListener=listener
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.image_row,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
      val imageView=holder.itemView.findViewById<ImageView>(R.id.SearingImagesRecycler)
        val url=images[position]
        holder.itemView.apply {
            glide.load(url).into(imageView)
            setOnClickListener {
                onItemClickListener?.let {
                    it(url)
                }
            }

        }

    }

    override fun getItemCount(): Int {
        return images.size

    }
}