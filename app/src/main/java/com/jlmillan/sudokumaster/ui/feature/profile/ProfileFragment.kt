package com.jlmillan.sudokumaster.ui.feature.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.fragment.findNavController
import com.jlmillan.sudokumaster.R
import com.jlmillan.sudokumaster.domain.model.UserModel
import com.jlmillan.sudokumaster.ui.common.MainActivity
import com.jlmillan.sudokumaster.ui.feature.common.LoadingView


class ProfileFragment : Fragment() {

    private val viewModel: ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                val user by viewModel.userState.collectAsStateWithLifecycle()
                val loading by viewModel.loadingState.collectAsStateWithLifecycle()
                ProfileScreen(user = user)
                LoadingView(alpha = if (loading) 0.5f else 0f)
            }
        }

    }

    override fun onResume() {
        super.onResume()
        viewModel.loadUser()
        (activity as? MainActivity)?.hideEndIcon()
        (activity as? MainActivity)?.hideStartIcon()
    }

    @Composable
    private fun ProfileScreen(user: UserModel?) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(
                    id = R.drawable.ic_account_circle
                ),
                colorFilter = ColorFilter.tint(Color.Black),
                contentDescription = "",
                modifier = Modifier.size(160.dp)
            )
            TitleValueFiled(
                title = stringResource(id = R.string.name_hint),
                value = user?.name.orEmpty()
            )
            TitleValueFiled(
                title = stringResource(id = R.string.email_hint),
                value = user?.email.orEmpty()
            )
            TitleValueFiled(
                title = stringResource(id = R.string.username_hint),
                value = user?.username.orEmpty(),
                iconResource = R.drawable.ic_edit
            ) {
                findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToEditProfileFragment())
            }
        }
    }

    @OptIn(ExperimentalLayoutApi::class)
    @Composable
    private fun TitleValueFiled(
        title: String,
        value: String,
        iconResource: Int = 0,
        iconAction: (() -> Unit)? = null
    ) {
        Column(
            modifier = Modifier
                .padding(vertical = 8.dp)
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = title,
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp
            )
            FlowRow(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    modifier = Modifier
                        .weight(0.8f)
                        .wrapContentWidth(align = Alignment.Start),
                    text = value,
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp
                )
                if (iconResource != 0) {
                    Image(
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .size(24.dp)
                            .clickable {
                                iconAction?.invoke()
                            },
                        painter = painterResource(id = iconResource),
                        contentDescription = "",
                        colorFilter = ColorFilter.tint(Color.Black),
                        alignment = Alignment.CenterStart
                    )
                }
            }
        }
    }

    @Preview
    @Composable
    private fun ProfileScreenPreview() {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            ProfileScreen(user = UserModel(
                name = "fake name",
                username = "fake user name",
                email = "fakeEmail@fakeEmail.com"
            ))
        }
    }

    @Preview
    @Composable
    private fun TitleValueFiledPreview() {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            TitleValueFiled(title = "Title", value = "Value")
        }
    }
}