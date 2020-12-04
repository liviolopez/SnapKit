package dev.all4.snapkit.ui.creative

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import dev.all4.snapkit.R
import dev.all4.snapkit.databinding.FragmentCreativeBinding

class CreativeFragment : Fragment(R.layout.fragment_creative) {

    private val creativeViewModel by viewModels<CreativeViewModel>()
    private lateinit var binding: FragmentCreativeBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCreativeBinding.bind(view)

        creativeViewModel.text.observe(viewLifecycleOwner, Observer {
            binding.textCreative.text = it
        })
    }
}

