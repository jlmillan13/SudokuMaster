package com.jlmillan.sudokumaster.ui.feature.levelselection

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
import androidx.compose.ui.platform.ComposeView
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.jlmillan.sudokumaster.R

class LevelSelectionFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                LevelSelectionScreen(navController = findNavController())
            }
        }
    }
}

@Composable
fun LevelSelectionScreen(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = {
                val bundle = Bundle().apply { putInt("emptySpaces", 35) }
                navController.navigate(R.id.action_levelSelectionFragment_to_sudokuFragment, bundle)
            },
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        ) {
            Text("Fácil")
        }

        Button(
            onClick = {
                val bundle = Bundle().apply { putInt("emptySpaces", 45) }
                navController.navigate(R.id.action_levelSelectionFragment_to_sudokuFragment, bundle)
            },
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        ) {
            Text("Intermedio")
        }

        Button(
            onClick = {
                val bundle = Bundle().apply { putInt("emptySpaces", 60) }
                navController.navigate(R.id.action_levelSelectionFragment_to_sudokuFragment, bundle)
            },
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        ) {
            Text("Experto")
        }
    }
}
