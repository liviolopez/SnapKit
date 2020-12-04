package dev.all4.snapkit.ui.bitmoji

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import dev.all4.snapkit.R
import dev.all4.snapkit.databinding.FragmentBitmojiBinding

class BitmojiFragment : Fragment(R.layout.fragment_bitmoji) {

    private val bitmojiViewModel by viewModels<BitmojiViewModel>()
    private lateinit var binding: FragmentBitmojiBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentBitmojiBinding.bind(view)

        bitmojiViewModel.text.observe(viewLifecycleOwner, Observer {
            binding.textBitmoji.text = it
        })
    }
}