package com.jlmillan.sudokumaster.ui.feature.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.core.content.res.ResourcesCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.jlmillan.sudokumaster.R
import com.jlmillan.sudokumaster.databinding.FragmentRegisterBinding
import com.jlmillan.sudokumaster.domain.model.AuthErrorException
import com.jlmillan.sudokumaster.ui.common.MainActivity
import com.jlmillan.sudokumaster.ui.common.extension.isTrue
import com.jlmillan.sudokumaster.ui.common.extension.showToast

class RegisterFragment : Fragment() {

    private var binding: FragmentRegisterBinding? = null
    private val viewModel: RegisterViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(inflater)
        setUpView()
        return binding?.root
    }

    private fun setUpView() {
        binding?.registerButton?.setOnClickListener {
            if (validateFields()) {
                viewModel.register(
                    email = getEmail(),
                    password = getPassword()
                )
            }
        }

        compareFieldsWhenTextChanged(ValidateField.EMAIL_REPEAT, ValidateField.EMAIL)
        compareFieldsWhenTextChanged(ValidateField.PASSWORD_REPEAT, ValidateField.PASSWORD)
    }

    private fun getEmail() = binding?.email?.text.toString().trim()

    private fun getPassword() = binding?.password?.text.toString().trim()

    private fun getPrivacyCheck() = binding?.checkbox

    private fun isPrivacyChecked() = getPrivacyCheck()?.isChecked.isTrue()

    private fun validateFields(): Boolean {
        var result = true
        ValidateField.entries.forEach { field ->
            val fieldEditText = getField(field)
            when(field) {
                ValidateField.USERNAME,
                ValidateField.NAME,
                ValidateField.EMAIL,
                ValidateField.PASSWORD -> {
                    if (validateIsNotBlank(fieldEditText).not()){
                        result = false
                        return@forEach
                    }
                }
                ValidateField.EMAIL_REPEAT -> {
                    val validateIsNotBlank = validateIsNotBlank(fieldEditText)
                    if(validateIsNotBlank.not() || validateIsEqual(fieldEditText, getEmail(), R.string.emails).not()) {
                        result = false
                        return@forEach
                    }
                }
                ValidateField.PASSWORD_REPEAT -> {
                    val validateIsNotBlank = validateIsNotBlank(fieldEditText)
                    if(validateIsNotBlank.not() || validateIsEqual(fieldEditText, getPassword(), R.string.passwords).not()) {
                        result = false
                        return@forEach
                    }
                }
                ValidateField.PRIVACY -> {
                    val errorText = if (isPrivacyChecked().isTrue().not()) {
                        result = false
                        getString(R.string.error_privacy_check)
                    } else {
                        null
                    }
                    getPrivacyCheck()?.error = errorText
                    if (result.not()) {
                        return@forEach
                    }
                }
            }
        }
        return result
    }

    private fun validateIsNotBlank(fieldEditText: EditText?) : Boolean {
        var result = true
        fieldEditText?.error = if (fieldEditText?.text.isNullOrBlank()) {
            result = false
            getString(R.string.error_empty_field)
        } else {
            null
        }
        return result
    }

    private fun validateIsEqual(fieldEditText: EditText?, textToCompare: String, @StringRes fieldResId: Int) : Boolean {
        var result = true
        fieldEditText?.error = if (textToCompare.equals(fieldEditText?.text?.trim().toString(), true)) {
            null
        } else {
            result = false
            getString(R.string.error_not_equals_field, getString(fieldResId))
        }
        return result
    }

    private fun getField(field: ValidateField) : EditText? {
        return when(field) {
            ValidateField.EMAIL -> binding?.email
            ValidateField.EMAIL_REPEAT -> binding?.repeatEmail
            ValidateField.PASSWORD -> binding?.password
            ValidateField.PASSWORD_REPEAT -> binding?.repeatPassword
            else -> null
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.registerSuccessLiveData().removeObservers(this)
        viewModel.registerSuccessLiveData().observe(viewLifecycleOwner) { (success, exception) ->
            if (success) {
                // navigate to main fragment
            } else {
                showError(exception)
            }
        }

        viewModel.registerLoadingLiveData().removeObservers(this)
        viewModel.registerLoadingLiveData().observe(viewLifecycleOwner) { loading ->
            (activity as? MainActivity)?.showLoading(loading)
        }
    }

    private fun showError(exception: AuthErrorException?) {
        @StringRes val errorResId = when(exception) {
            AuthErrorException.EMAIL_EXIST -> R.string.error_email_exist
            AuthErrorException.WRONG_PASSWORD -> R.string.error_wrong_password
            AuthErrorException.USERNAME_EXIST -> R.string.error_username_exist
            AuthErrorException.USERNAME_NOT_EXIST -> R.string.error_username_not_exist
            else -> R.string.error_unknown
        }
        showToast(getString(errorResId))
    }

    private fun compareFieldsWhenTextChanged(field: ValidateField, fieldToCompare: ValidateField) {
        val fieldToValidate = getField(field)
        val fieldToCompareEditText = getField(fieldToCompare)
        fieldToValidate?.addTextChangedListener { textRepeat ->
            val textColor = if (textRepeat.toString() == fieldToCompareEditText?.text.toString()) {
                R.color.black
            } else {
                R.color.red_app
            }
            fieldToValidate.setTextColor(ResourcesCompat.getColor(resources, textColor, null))
        }
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    enum class ValidateField {
        USERNAME, NAME, EMAIL, EMAIL_REPEAT, PASSWORD, PASSWORD_REPEAT, PRIVACY
    }
}