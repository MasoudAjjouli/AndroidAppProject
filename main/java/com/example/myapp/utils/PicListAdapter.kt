package com.example.myapp.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp.databinding.PicCellFragmentBinding
import com.example.myapp.databinding.RecyclerListRowBinding
import com.example.myapp.models.PicInfo
import com.example.myapp.models.PicsList
import java.util.ArrayList

class PicListAdapter(
    private val picList:List<PicsList> ,
    private val clickListener: UserClick2
    ): RecyclerView.Adapter<PicListAdapter.PicViewHolder>() {


    class PicViewHolder(
            private val binding: PicCellFragmentBinding,
            private val clickListener: UserClick2
        ):RecyclerView.ViewHolder(binding.root){
        val img: ImageView = binding.pic
        val namee: TextView = binding.picName

        fun bindPic(picture: PicsList) {
            binding.cardView.setOnClickListener {
                clickListener.onClick(picture)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PicViewHolder {
        val from = LayoutInflater.from(parent.context)
        val binding = PicCellFragmentBinding.inflate(from, parent, false)
        return  PicViewHolder(binding, clickListener)
    }

    override fun onBindViewHolder(holder: PicViewHolder, position: Int) {
        val currentItem = picList[position]
        holder.namee.text = currentItem.name
        holder.img.setImageURI(currentItem.picture)

        holder.bindPic(picList[position])
    }



    override fun getItemCount() = picList.size

}

