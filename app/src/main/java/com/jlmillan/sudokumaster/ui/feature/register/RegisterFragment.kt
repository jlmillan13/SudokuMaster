package com.jlmillan.sudokumaster.ui.feature.register

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.jlmillan.sudokumaster.R
import com.jlmillan.sudokumaster.databinding.FragmentRegisterBinding
import com.jlmillan.sudokumaster.domain.model.AuthErrorException
import com.jlmillan.sudokumaster.ui.common.extension.showToast
import com.jlmillan.sudokumaster.ui.common.extension.validateEmail

class RegisterFragment : Fragment() {
    private var binding: FragmentRegisterBinding? = null
    val viewModel: RegisterViewModel by viewModels()

    private fun getEmail() = binding?.emailRegister?.text.toString()
    private fun getRepeatEmail() = binding?.repeatEmailRegister?.text.toString()
    private fun getPassword() = binding?.passwordRegister?.text.toString()
    private fun getRepeatPassword() = binding?.repeatPasswordRegister?.text.toString()

    private fun showError(exception: AuthErrorException) {
        @StringRes val errorResId = when (exception) {
            AuthErrorException.EMAIL_EXIST -> R.string.error_email_already_exist
            AuthErrorException.INVALID_EMAIL_OR_PASSWORD -> R.string.error_invalid_email_password
            AuthErrorException.UNKNOWN -> R.string.error_unknown
            else -> R.string.error_unknown
        }
        showToast(getString(errorResId))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        setUpClickListener()

        viewModel.registerSuccessLiveData().removeObservers(viewLifecycleOwner)
        viewModel.registerSuccessLiveData().observe(viewLifecycleOwner) { (success, exception) ->
            if (success) {
                findNavController().navigate(R.id.action_registerFragment_to_mainFragment)
            } else {
                showError(exception ?: AuthErrorException.UNKNOWN)
            }
        }

        return binding?.root
    }

    private fun setUpClickListener() {
        binding?.alreadyAccount?.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }
        binding?.registerBtn?.setOnClickListener {
            if (validateForm()) {
                viewModel.register(getPassword(), getEmail())
            }
        }
    }

    private fun validateForm(): Boolean {
        var isValid = true

        if (getEmail().isEmpty()) {
            binding?.emailRegister?.error = context?.getString(R.string.required_field)
            isValid = false
        } else if (!getEmail().validateEmail()) {
            binding?.emailRegister?.error = context?.getString(R.string.invalid_email_address)
            isValid = false
        }

        if (getRepeatEmail().isEmpty()) {
            binding?.repeatEmailRegister?.error = context?.getString(R.string.required_field)
            isValid = false
        } else if (getRepeatEmail() != getEmail()) {
            binding?.repeatEmailRegister?.error = context?.getString(R.string.emails_do_not_match)
            isValid = false
        }

        if (getPassword().isEmpty()) {
            binding?.passwordRegister?.error = context?.getString(R.string.required_field)
            isValid = false
        } else if (getPassword().length < 8) {
            binding?.passwordRegister?.error = context?.getString(R.string.password_min_length)
            isValid = false
        }

        if (getRepeatPassword().isEmpty()) {
            binding?.repeatPasswordRegister?.error = context?.getString(R.string.required_field)
            isValid = false
        } else if (getRepeatPassword() != getPassword()) {
            binding?.repeatPasswordRegister?.error = context?.getString(R.string.passwords_do_not_match)
            isValid = false
        }

        return isValid
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
