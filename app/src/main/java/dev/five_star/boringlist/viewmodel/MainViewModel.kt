package dev.five_star.boringlist.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.five_star.boringlist.model.BoringItem
import dev.five_star.boringlist.model.BoringRepository
import dev.five_star.boringlist.model.fakeData
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
            val boringItems = boringRepository.fetchBoringItems()

            _viewState.value = _viewState.value.copy(
                boringList = boringItems,
            )
        }
    }

    fun addListItem(todo: String, description: String) {

        val boringItems: MutableList<BoringItem> = _viewState.value.boringList.toMutableList()
        boringItems.add(BoringItem(todo, description))
        onNameChanges(newNames = boringItems)
    }

    private fun onNameChanges(newNames: MutableList<BoringItem>) {
        _viewState.value = _viewState.value.copy(
            boringList = newNames
        )
    }
}