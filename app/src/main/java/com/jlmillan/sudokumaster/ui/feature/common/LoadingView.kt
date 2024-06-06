package com.jlmillan.sudokumaster.ui.feature.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.jlmillan.sudokumaster.R

@Composable
fun LoadingView(
    alpha: Float
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
            .background(color = colorResource(id = R.color.white_sudoku).copy(alpha = alpha))
            .alpha(alpha)
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .size(60.dp)
                .align(Alignment.Center)
        )
    }
}