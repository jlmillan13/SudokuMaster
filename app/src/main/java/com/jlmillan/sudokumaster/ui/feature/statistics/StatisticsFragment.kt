package com.jlmillan.sudokumaster.ui.feature.statistics

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels

class StatisticsFragment : Fragment() {

    private val viewModel: StatisticsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                StatisticsScreen(viewModel)
            }
        }
    }
}

@Composable
fun StatisticsScreen(viewModel: StatisticsViewModel) {
    val userStats by viewModel.userStats.observeAsState(emptyList())

    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "User Statistics")
        userStats.forEach { stat ->
            Text(text = "Score: ${stat.score}, Time: ${stat.timeTaken}")
        }
    }
}
