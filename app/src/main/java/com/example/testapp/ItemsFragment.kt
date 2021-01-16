package com.example.testapp

import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.testapp.databinding.FragmentItemsBinding

class ItemsFragment : Fragment(), ItemAdapter.Listener {

    private val itemAdapter = ItemAdapter(this)

    private val viewModel: ItemsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentItemsBinding.inflate(inflater, container, false)
        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(context, getSpanCount())
            adapter = itemAdapter
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.items.observe(viewLifecycleOwner, Observer {
            itemAdapter.submitList(it)
        })
    }

    override fun onClick(pos: Int){
        viewModel.deleteItem(pos)
    }

    private fun getSpanCount() : Int {
        return if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT)
            2 else 4
    }

}