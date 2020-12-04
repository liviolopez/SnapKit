package dev.all4.snapkit.ui.ad

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AdViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Ad Kit"
    }

    val text: LiveData<String> = _text

}