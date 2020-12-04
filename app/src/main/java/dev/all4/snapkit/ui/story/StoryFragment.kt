package dev.all4.snapkit.ui.story

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import dev.all4.snapkit.R
import dev.all4.snapkit.databinding.FragmentStoryBinding

class StoryFragment : Fragment(R.layout.fragment_story) {

    private val storyViewModel by viewModels<StoryViewModel>()
    private lateinit var binding: FragmentStoryBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentStoryBinding.bind(view)

        storyViewModel.text.observe(viewLifecycleOwner, Observer {
            binding.textStory.text = it
        })
    }
}