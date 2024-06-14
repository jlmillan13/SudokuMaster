package com.jlmillan.sudokumaster.ui.feature.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.jlmillan.sudokumaster.R
import com.jlmillan.sudokumaster.ui.feature.common.LoadingView
import com.jlmillan.sudokumaster.ui.theme.Black
import com.jlmillan.sudokumaster.ui.theme.VeryLightBlue

class MainFragment : Fragment() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                val loading by viewModel.stateLoading.collectAsState()
                viewModel.setLoading(false)
                MainScreen(navController = findNavController(), loading = loading)
            }
        }
    }
}

@Composable
fun MainScreen(navController: NavController, loading: Boolean) {
    val loadingAlpha = if (loading) 0.5f else 0f
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.fondosudoku),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.matchParentSize()
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(80.dp)) // Ajusta esta altura según necesites

            Button(
                onClick = {
                    navController.navigate(R.id.action_mainFragment_to_levelSelectionFragment)
                },
                colors = ButtonDefaults.buttonColors(containerColor = VeryLightBlue),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .clip(RoundedCornerShape(15.dp))
            ) {
                Text(
                    text = stringResource(R.string.play_select_level),
                    color = Black,
                    fontWeight = FontWeight.ExtraBold
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    navController.navigate(R.id.action_mainFragment_to_statisticsFragment)
                },
                colors = ButtonDefaults.buttonColors(containerColor = VeryLightBlue),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .clip(RoundedCornerShape(15.dp))
            ) {
                Text(
                    text = stringResource(R.string.statistics_select_level),
                    color = Black,
                    fontWeight = FontWeight.ExtraBold
                )
            }
        }
        LoadingView(alpha = loadingAlpha)
    }
}
