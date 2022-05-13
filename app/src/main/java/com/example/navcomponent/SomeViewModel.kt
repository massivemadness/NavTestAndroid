package com.example.navcomponent

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import java.util.*

class SomeViewModel : ViewModel() {

    val itemsFlow = MutableStateFlow<List<Item>>(emptyList())

    fun fetchItems() {
        val list = mutableListOf<Item>()
        for (i in 0..100) {
            val item = Item(
                id = UUID.randomUUID().toString(),
                text = i.toString()
            )
            list.add(item)
        }
        itemsFlow.value = list
    }
}