package dev.five_star.boringlist.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.five_star.boringlist.model.BoringItem
import dev.five_star.boringlist.model.BoringRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

const val TAG = "MainViewModel"

class MainViewModel(private val boringRepository: BoringRepository) : ViewModel() {

    private val _viewState = MutableStateFlow(MainViewState())
    val stateView: StateFlow<MainViewState> = _viewState

    init {
        fetchBoringItems()
    }

    private fun fetchBoringItems() {
        viewModelScope.launch {
            val boringItems = boringRepository.getAllItems()

            _viewState.value = _viewState.value.copy(
                boringList = boringItems,
            )
        }
    }

    fun addListItem(todo: String, description: String) {
        val boringItems: MutableList<BoringItem> = _viewState.value.boringList.toMutableList()
        val boringItem = BoringItem(todo = todo, description = description)
        boringItems.add(boringItem)

        viewModelScope.launch(Dispatchers.IO) {
            boringRepository.addBoringItem(boringItem)
            onNameChanges()
        }
    }

    fun removeListItem(boringItem: BoringItem) {
        Log.d(TAG, "removeListItem $boringItem")
//        val boringItems: MutableList<BoringItem> = _viewState.value.boringList.toMutableList()
//        boringItems.remove(boringItem)

        viewModelScope.launch(Dispatchers.IO) {
            boringRepository.removeBoringItem(boringItem)
            onNameChanges()
        }
    }

    private fun onNameChanges() {
        viewModelScope.launch {
            _viewState.value = _viewState.value.copy(
                boringList = boringRepository.getAllItems()
            )
        }
    }
}