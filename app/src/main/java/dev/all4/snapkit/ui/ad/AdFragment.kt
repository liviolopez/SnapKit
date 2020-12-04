package dev.all4.snapkit.ui.ad

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import dev.all4.snapkit.R
import dev.all4.snapkit.databinding.FragmentAdBinding

class AdFragment : Fragment(R.layout.fragment_ad) {

    private val adViewModel by viewModels<AdViewModel>()
    private lateinit var binding: FragmentAdBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAdBinding.bind(view)

        adViewModel.text.observe(viewLifecycleOwner, Observer {
            binding.textAd.text = it
        })
    }
}