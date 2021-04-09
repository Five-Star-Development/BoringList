package dev.five_star.boringlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    private val _names: MutableLiveData<List<String>> = MutableLiveData(mutableListOf())
    val names: LiveData<List<String>> = _names

    fun addListItem(name: String) {
        val newNames: MutableList<String> = _names.value?.toMutableList() ?: mutableListOf()
        newNames.add(name)
        onNameChanges(newNames = newNames)

    }

    private fun onNameChanges(newNames: MutableList<String>) {
        _names.value = newNames
    }
}