package dev.all4.snapkit.ui.creative

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CreativeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Creative Kit"
    }

    val text: LiveData<String> = _text

}