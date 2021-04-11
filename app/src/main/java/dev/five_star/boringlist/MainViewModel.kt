package dev.five_star.boringlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    private val _names: MutableLiveData<List<BoringItem>> = MutableLiveData(mutableListOf())
    val names: LiveData<List<BoringItem>> = _names

    fun addListItem(todo: String, description: String) {
        val newNames: MutableList<BoringItem> = _names.value?.toMutableList() ?: mutableListOf()
        newNames.add(BoringItem(todo, description))
        onNameChanges(newNames = newNames)

    }

    private fun onNameChanges(newNames: MutableList<BoringItem>) {
        _names.value = newNames
    }
}