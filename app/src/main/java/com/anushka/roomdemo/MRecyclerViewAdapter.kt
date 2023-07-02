package com.anushka.roomdemo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.anushka.roomdemo.databinding.ListItemBinding
import com.anushka.roomdemo.db.Subscriber

class MRecyclerViewAdapter(private val subscribersList: List<Subscriber>) :
    RecyclerView.Adapter<MViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ListItemBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.list_item, parent, false)
        return MViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return subscribersList.size
    }

    override fun onBindViewHolder(holder: MViewHolder, position: Int) {
        holder.bind(subscribersList[position])
    }
}

class MViewHolder(private val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(subscriber: Subscriber) {
        binding.nameTextView.text = subscriber.name
        binding.emailTextView.text = subscriber.email
    }
}