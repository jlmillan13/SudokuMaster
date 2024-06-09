package com.jlmillan.sudokumaster.ui.feature.statistics

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import com.jlmillan.sudokumaster.data.common.CacheManager
import com.jlmillan.sudokumaster.ui.theme.Black
import com.jlmillan.sudokumaster.ui.theme.TextBlack
import com.jlmillan.sudokumaster.ui.theme.TextDescription
import com.jlmillan.sudokumaster.ui.theme.VeryLightCyan
import com.jlmillan.sudokumaster.ui.theme.WhiteSudoku


class StatisticsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                val easyPoints = CacheManager.getPoints(requireContext(), 35)
                val easyTime = CacheManager.getTime(requireContext(), 35)
                val mediumPoints = CacheManager.getPoints(requireContext(), 45)
                val mediumTime = CacheManager.getTime(requireContext(), 45)
                val hardPoints = CacheManager.getPoints(requireContext(), 60)
                val hardTime = CacheManager.getTime(requireContext(), 60)
                StatisticsScreen(easyPoints, easyTime, mediumPoints, mediumTime, hardPoints, hardTime)
            }
        }
    }
}

@Composable
fun StatisticsScreen(easyPoints: Int, easyTime: Int, mediumPoints: Int, mediumTime: Int, hardPoints: Int, hardTime: Int) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(WhiteSudoku)
    ) {
        Text(
            text = "User Statistics",
            color = TextBlack,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        StatisticItem(level = "Easy", points = easyPoints, time = easyTime)
        StatisticItem(level = "Medium", points = mediumPoints, time = mediumTime)
        StatisticItem(level = "Hard", points = hardPoints, time = hardTime)
    }
}

@Composable
fun StatisticItem(level: String, points: Int, time: Int) {
    val timeText = if (time == Int.MAX_VALUE) "N/A" else "$time seconds"
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .background(VeryLightCyan, shape = RoundedCornerShape(8.dp))
            .padding(16.dp)
    ) {
        Text(
            text = "$level Level",
            color = Black,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Points: $points",
            color = TextDescription,
            fontSize = 16.sp
        )
        Text(
            text = "Time: $timeText",
            color = TextDescription,
            fontSize = 16.sp
        )
    }
}
