package com.jlmillan.sudokumaster.ui.feature.login

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.fragment.findNavController
import com.jlmillan.sudokumaster.R
import com.jlmillan.sudokumaster.domain.model.AuthErrorException
import com.jlmillan.sudokumaster.ui.common.MainActivity
import com.jlmillan.sudokumaster.ui.common.extension.showToast
import com.jlmillan.sudokumaster.ui.common.extension.validateEmail
import com.jlmillan.sudokumaster.ui.feature.common.LoadingView
import com.jlmillan.sudokumaster.ui.theme.BackgroundInput
import com.jlmillan.sudokumaster.ui.theme.Black
import com.jlmillan.sudokumaster.ui.theme.LighterBlack
import com.jlmillan.sudokumaster.ui.theme.TextDescription
import com.jlmillan.sudokumaster.ui.theme.VeryLightBlue


class LoginFragment : Fragment() {

    private val viewModel: LoginViewModel by viewModels()

    @SuppressLint("NewApi")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                val stateSuccess by viewModel.stateSuccess.collectAsStateWithLifecycle()
                val stateLoading by viewModel.stateLoading.collectAsStateWithLifecycle()
                Log.e("Login_state", "Recomposition")
                when {
                    stateSuccess.first -> {
                        Log.e("Login_state", "Login ok")
                        findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToMainFragment())
                    }
                    stateSuccess.second != null -> {
                        Log.e("Login_state", "Login ko")
                        LoginScreenIdle(stateLoading = stateLoading)
                        showError(stateSuccess.second ?: AuthErrorException.UNKNOWN)
                    }
                    else -> {
                        Log.e("Login_state", "Waiting login: $stateLoading")
                        LoginScreenIdle(stateLoading = stateLoading)
                    }
                }
            }
        }

    }

    @Composable
    private fun LoginScreenIdle(stateLoading: Boolean) {
        val loadingAlpha = if (stateLoading) 0.5f else 0f
        LoginScreen()
        LoadingView(alpha = loadingAlpha)
    }

    private fun showError(exception: AuthErrorException) {
        @StringRes val errorResId = when(exception) {
            AuthErrorException.EMAIL_EXIST -> R.string.error_email_exist
            AuthErrorException.WRONG_PASSWORD -> R.string.error_wrong_password
            AuthErrorException.USERNAME_EXIST -> R.string.error_username_exist
            AuthErrorException.USERNAME_NOT_EXIST -> R.string.error_username_not_exist
            AuthErrorException.UNKNOWN -> R.string.error_unknown
            else -> R.string.error_unknown
        }
        showToast(getString(errorResId))
    }


    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun CustomTextField(
        value: String,
        onValueChange: (String) -> Unit,
        label: String,
        visualTransformation: VisualTransformation = VisualTransformation.None,
        keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
        keyboardActions: KeyboardActions = KeyboardActions.Default,
        isError: Boolean = false
    ) {
        Column(
            modifier = Modifier
                .padding(4.dp)
        ) {
            TextField(
                value = value,
                onValueChange = onValueChange,
                label = { Text(label, color = TextDescription) },
                visualTransformation = visualTransformation,
                keyboardOptions = keyboardOptions,
                keyboardActions = keyboardActions,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(15.dp, 15.dp, 15.dp, 15.dp))
                    .background(
                        color = if (isError) Color.Red else BackgroundInput,
                    )
                    .padding(1.dp)
                ,
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = BackgroundInput,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent
                )
            )

            if (isError) {
                Text(
                    text = stringResource(R.string.login_error),
                    color = Color.Red,
                    modifier = Modifier
                        .padding(start = 8.dp, top = 4.dp)
                )
            }
        }
    }
    @Composable
    @Preview
    fun LoginScreen() {
        var username by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }

        var emailError by remember { mutableStateOf(false) }
        var passwordError by remember { mutableStateOf(false) }

        fun checkEmailAndPasswordError() : Boolean {
            if (username.isEmpty() || !username.validateEmail()) {
                emailError = true
            }

            if (password.isEmpty()) {
                passwordError = true
            }
            return emailError || passwordError
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(R.string.login_button),
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.ExtraBold
            )

            Spacer(modifier = Modifier.height(20.dp))

            CustomTextField(
                value = username,
                onValueChange = {
                    username = it
                    // Reinicia el error cuando se comienza a escribir
                    emailError = false
                },

                label = stringResource(R.string.email_hint),
                isError = emailError
            )

            Spacer(modifier = Modifier.height(10.dp))

            CustomTextField(
                value = password,
                onValueChange = {
                    password = it
                    passwordError = false
                },
                label = stringResource(R.string.password_hint),
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        checkEmailAndPasswordError()
                    }
                ),
                isError = passwordError
            )

            Spacer(modifier = Modifier.height(10.dp))


            Button(
                onClick = {
                    if (checkEmailAndPasswordError().not()) {
                        viewModel.login(username, password)
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = VeryLightBlue),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .aspectRatio(7f)
                    .clip(RoundedCornerShape(15.dp, 15.dp, 15.dp, 15.dp))

            ) {
                Text(
                    text = stringResource(R.string.login_button),
                    color = Black,
                    fontWeight = FontWeight.ExtraBold
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                color = LighterBlack,
                text = stringResource(R.string.have_account_login),
                textDecoration = TextDecoration.Underline,
                modifier = Modifier
                    .padding(top = 8.dp)
                    .clickable {
                        findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
                    }

            )

        }
    }

    @Composable
    @Preview
    fun LoadingViewPreview() {
        LoadingView(alpha = 1f)
    }

    @Composable
    @Preview
    fun LoginScreenWithStatePreview() {
        val loadingAlpha = 0.5f
        LoginScreen()
        LoadingView(alpha = loadingAlpha)
    }
}