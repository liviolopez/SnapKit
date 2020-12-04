package dev.all4.snapkit.ui.story

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class StoryViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Story Kit"
    }

    val text: LiveData<String> = _text

}