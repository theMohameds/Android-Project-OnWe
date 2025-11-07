package com.example.android_project_onwe.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout

@Composable
fun HomeScreen () {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("You´re in ", style = MaterialTheme.typography.headlineSmall)
    }
}