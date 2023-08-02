package com.example.myapp.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp.databinding.RecyclerListRow2Binding
import com.example.myapp.databinding.RecyclerListRowBinding
import com.example.myapp.models.UserInfoElement
import java.util.ArrayList

private const val TYPE_EVEN: Int = 1
private const val TYPE_ODD: Int = 0


class RecycleViewAdapter(
    private val clickListener: UserClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var items = ArrayList<UserInfoElement>()
    fun setUpdatedData(items: List<UserInfoElement>) {
        this.items.addAll(items)
        notifyDataSetChanged()

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return if(viewType ==  TYPE_EVEN){
            val from = LayoutInflater.from(parent.context)
            val binding = RecyclerListRowBinding.inflate(from, parent, false)
            EvenViewHolder(binding, clickListener)
        }
        else{
            val from = LayoutInflater.from(parent.context)
            val binding2 = RecyclerListRow2Binding.inflate(from, parent, false)
            OddViewHolder(binding2, clickListener)
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if(getItemViewType(position)== TYPE_EVEN){
            (holder as EvenViewHolder).bindUser(items[position])
        }else{
            (holder as OddViewHolder).bindUser(items[position])
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun getItemViewType(position: Int):Int {
        return if (position % 2 == 0  ){
            TYPE_EVEN
        }else{
            TYPE_ODD
        }
    }

    class EvenViewHolder(
        private val recyclerListRowBinding: RecyclerListRowBinding,
        private val clickListener: UserClickListener
    ) : RecyclerView.ViewHolder(recyclerListRowBinding.root) {

        fun bindUser(User: UserInfoElement) {
            recyclerListRowBinding.fullName.text = User.fullName
            recyclerListRowBinding.emailAddress.text = User.emailAddress

            recyclerListRowBinding.cardView.setOnClickListener {
                clickListener.onClick(User)
            }
        }
    }

    class OddViewHolder(
        private val recyclerListRow2Binding: RecyclerListRow2Binding,
        private val clickListener: UserClickListener
    ) : RecyclerView.ViewHolder(recyclerListRow2Binding.root) {

        fun bindUser(User: UserInfoElement) {
            recyclerListRow2Binding.fullName.text = User.fullName
            recyclerListRow2Binding.emailAddress.text = User.emailAddress

            recyclerListRow2Binding.cardView2.setOnClickListener {
                clickListener.onClick(User)
            }
        }
    }



}