package dev.all4.snapkit.ui.login

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.snapchat.kit.sdk.SnapLogin
import com.snapchat.kit.sdk.core.controller.LoginStateController.OnLoginStateChangedListener
import dev.all4.snapkit.R
import dev.all4.snapkit.databinding.FragmentLoginBinding

class LoginFragment : Fragment(R.layout.fragment_login) {

    private val loginViewModel by viewModels<LoginViewModel>()
    private lateinit var binding: FragmentLoginBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLoginBinding.bind(view)

        loginViewModel.text.observe(viewLifecycleOwner, Observer {
            binding.textLogin.text = it
        })

        setupSnapLogin()
    }

    private fun setupSnapLogin(){
        SnapLogin.getButton(requireContext(), binding.snapLoginButtonContainer)

        val onLoginStateChangedListener = object : OnLoginStateChangedListener {
            override fun onLoginSucceeded() { }

            override fun onLoginFailed() { }

            override fun onLogout() { }
        }

        SnapLogin.getLoginStateController(requireContext()).addOnLoginStateChangedListener(onLoginStateChangedListener)
    }
}