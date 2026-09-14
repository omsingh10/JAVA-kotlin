package com.example.one

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class GroceryItem(
    val id: Long = System.nanoTime(),
    val name: String,
    val quantity: Int,
    val isBought: Boolean = false
)

class GroceryViewModel : ViewModel() {

    private val _items = MutableStateFlow<List<GroceryItem>>(emptyList())
    val items: StateFlow<List<GroceryItem>> = _items.asStateFlow()

    fun addItem(name: String, quantity: Int) {
        if (name.isBlank()) return
        _items.update { current ->
            current + GroceryItem(name = name.trim(), quantity = quantity)
        }
    }

    fun updateItem(id: Long, name: String, quantity: Int) {
        if (name.isBlank()) return
        _items.update { current ->
            current.map { item ->
                if (item.id == id) item.copy(name = name.trim(), quantity = quantity)
                else item
            }
        }
    }

    fun toggleBought(id: Long) {
        _items.update { current ->
            current.map { if (it.id == id) it.copy(isBought = !it.isBought) else it }
        }
    }

    fun deleteItem(id: Long) {
        _items.update { current -> current.filterNot { it.id == id } }
    }

    fun clearAll() {
        _items.update { emptyList() }
    }
}