package com.anushka.roomdemo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.anushka.roomdemo.databinding.ActivityMainBinding
import com.anushka.roomdemo.db.Subscriber
import com.anushka.roomdemo.db.SubscriberDatabase
import com.anushka.roomdemo.db.SubscriberRepository
import com.anushka.roomdemo.db.SubscriberViewModel
import com.anushka.roomdemo.db.SubscriberViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var subscriberViewModel: SubscriberViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        //TODO: Step 7: Initialize the ViewModel and ViewModelFactory
        val dao = SubscriberDatabase.getInstance(application).subscriberDAO
        val repository = SubscriberRepository(dao)
        val factory = SubscriberViewModelFactory(repository)
        subscriberViewModel = ViewModelProvider(this, factory)[SubscriberViewModel::class.java]
        binding.mViewModel = subscriberViewModel
        binding.lifecycleOwner = this

        initRecyclerView()
    }

    private fun initRecyclerView() {
        binding.subscriberRecyclerView.layoutManager = LinearLayoutManager(this)
        displaySubscribersList()
    }

    private fun displaySubscribersList() {
        subscriberViewModel.subscribers.observe(this, Observer {
            binding.subscriberRecyclerView.adapter =
                MRecyclerViewAdapter(it) { selectedItem: Subscriber ->
                    listItemClicked(
                        selectedItem
                    )
                }
        })
    }

    private fun listItemClicked(subscriber: Subscriber) {
        subscriberViewModel.initUpdateAndDelete(subscriber)
    }
}