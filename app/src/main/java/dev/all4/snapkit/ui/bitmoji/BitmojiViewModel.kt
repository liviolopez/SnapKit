package dev.all4.snapkit.ui.bitmoji

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class BitmojiViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Bitmoji Kit"
    }

    val text: LiveData<String> = _text

}