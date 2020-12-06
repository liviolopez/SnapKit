package dev.all4.snapkit.ui.login

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.snapchat.kit.sdk.SnapLogin
import com.snapchat.kit.sdk.core.controller.LoginStateController.OnLoginStateChangedListener
import com.snapchat.kit.sdk.login.models.UserDataResponse
import com.snapchat.kit.sdk.login.networking.FetchUserDataCallback
import dev.all4.snapkit.R
import dev.all4.snapkit.databinding.FragmentLoginBinding
import dev.all4.snapkit.utils._log

/**
 * Created by Livio Lopez on 12/05/20.
 */

class LoginFragment : Fragment(R.layout.fragment_login) {

    private val loginViewModel by viewModels<LoginViewModel>()
    private lateinit var binding: FragmentLoginBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLoginBinding.bind(view)

        loginViewModel.text.observe(viewLifecycleOwner, Observer {
            binding.textLogin.text = it
        })
    }

    override fun onResume() {
        super.onResume()
        showLoading()

        if(SnapLogin.isUserLoggedIn(requireContext())){
            getUserDetails()
        } else {
            showSignedOut()
        }
    }

    private fun showSignedIn(){
        binding.btSignOut.setOnClickListener { signOutUser() }
        binding.progressBarContainer.visibility = View.GONE
        binding.logInContainer.visibility = View.VISIBLE
        binding.logOutContainer.visibility = View.GONE
    }

    private fun showSignedOut(){
        setupSnapLogin()
        binding.progressBarContainer.visibility = View.GONE
        binding.logInContainer.visibility = View.GONE
        binding.logOutContainer.visibility = View.VISIBLE
    }

    private fun showLoading(){
        binding.progressBarContainer.visibility = View.VISIBLE
        binding.logInContainer.visibility = View.GONE
        binding.logOutContainer.visibility = View.GONE
    }

    private fun setupSnapLogin(){
        SnapLogin.getButton(requireContext(), binding.btSignIn)

        val onLoginStateChangedListener = object : OnLoginStateChangedListener {
            override fun onLoginSucceeded() {
                "Login Succeeded"._log()
                getUserDetails()
            }

            override fun onLoginFailed() {
                "Login Failed"._log()
                showSignedOut()
            }

            override fun onLogout() {
                "Logout"._log()
                showSignedOut()
            }
        }

        SnapLogin.getLoginStateController(requireActivity().applicationContext)
                 .addOnLoginStateChangedListener(onLoginStateChangedListener)
    }

    private fun signOutUser(){
        "The user is unlinking their profile"._log()
        SnapLogin.getAuthTokenManager(requireContext()).clearToken()
        showSignedOut()
    }

    private fun getUserDetails() {
        val isUserLoggedIn = SnapLogin.isUserLoggedIn(requireContext())
        if (isUserLoggedIn) {
            "The user is logged in"._log()

            val query = "{me{bitmoji{avatar},displayName,externalId}}"
            SnapLogin.fetchUserData(requireContext(), query, null, object : FetchUserDataCallback {
                override fun onSuccess(userDataResponse: UserDataResponse?) {
                    "Fetch UserData success"._log()

                    if (userDataResponse == null || userDataResponse.data == null) {
                        "Error: UserData is NULL"._log()
                        return
                    }

                    val meData = userDataResponse.data.me

                    binding.tvUserName.text = meData.displayName
                    binding.tvExternalId.text = meData.externalId

                    meData.bitmojiData?.let {
                        Glide.with(requireContext()).load(it.avatar).into(binding.ivAvatar)
                    }

                    showSignedIn()
                }

                override fun onFailure(isNetworkError: Boolean, statusCode: Int) {
                    "Error on fetch UserData: isNetworkError: $isNetworkError, statusCode: $statusCode"._log()
                    showSignedOut()
                }
            })
        }
    }
}