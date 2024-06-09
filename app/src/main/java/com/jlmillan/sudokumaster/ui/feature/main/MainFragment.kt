package com.jlmillan.sudokumaster.ui.feature.main

import android.os.Bundle
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.jlmillan.sudokumaster.R
import com.jlmillan.sudokumaster.ui.theme.Black
import com.jlmillan.sudokumaster.ui.theme.VeryLightBlue

class MainFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                MainScreen(navController = findNavController())
            }
        }
    }
}

@Composable
fun MainScreen(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = {
                navController.navigate(R.id.action_mainFragment_to_levelSelectionFragment)
            },
            colors = ButtonDefaults.buttonColors(containerColor = VeryLightBlue),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .aspectRatio(7f)
                .clip(RoundedCornerShape(15.dp, 15.dp, 15.dp, 15.dp))
        ) {
            Text(
                text = "Play Sudoku",
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
                .aspectRatio(7f)
                .clip(RoundedCornerShape(15.dp, 15.dp, 15.dp, 15.dp))
        ) {
            Text(
                text = "View Statistics",
                color = Black,
                fontWeight = FontWeight.ExtraBold
            )
        }
    }
}

