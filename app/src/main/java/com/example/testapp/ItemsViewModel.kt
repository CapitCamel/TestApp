package com.example.testapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

class ItemsViewModel: ViewModel() {

    private val _items = MutableLiveData<List<DataItem>>()
    val items: LiveData<List<DataItem>>
        get()=_items

    private var numItem: Int = 1
    private val deletedItems: MutableList<DataItem> = mutableListOf()

    init {
        viewModelScope.launch {
            val itemsL: ArrayList<DataItem> = ArrayList()
            for (i in 1..15) {
                itemsL.add(DataItem(numItem))
                numItem++
            }
            _items.value = itemsL
            newItem()
        }
    }

    private fun addItem(position: Int){
        _items.value?.let {
            val list = mutableListOf<DataItem>().apply { addAll(it) }
            if(deletedItems.isEmpty()){
                list.add(position, DataItem(numItem))
                numItem++
                _items.value = list
            }else{
                list.add(position, deletedItems[0])
                deletedItems.removeAt(0)
                _items.value = list
            }
        }
    }

    private suspend fun newItem(){
        while (true){
            delay(5000)
            val position = Random.nextInt(_items.value?.size ?: 15)
            addItem(position)
        }
    }

    fun deleteItem(pos: Int){
        _items.value?.let{
            val list = mutableListOf<DataItem>().apply { addAll(it) }
            deletedItems.add(list.elementAt(pos))
            list.removeAt(pos)
            _items.value = list
        }

    }
}