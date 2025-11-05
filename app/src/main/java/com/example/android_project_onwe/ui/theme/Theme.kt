package com.example.android_project_onwe.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF2196F3),      // Nice blue
    secondary = Color(0xFF03A9F4),    // Lighter blue
    tertiary = Color(0xFF4CAF50),     // Green accent
    background = Color(0xFFF5F5F5),   // Light gray background
    surface = Color(0xFFFFFFFF),      // White surface
    onPrimary = Color(0xFFFFFFFF),    // White text on primary
    onSecondary = Color(0xFFFFFFFF),  // White text on secondary
    onBackground = Color(0xFF000000), // Black text on background
    onSurface = Color(0xFF000000),    // Black text on surface
)

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF2196F3),      // Same blue
    secondary = Color(0xFF03A9F4),    // Same lighter blue
    tertiary = Color(0xFF4CAF50),     // Same green accent
    background = Color(0xFF121212),   // Dark background
    surface = Color(0xFF1E1E1E),      // Dark surface
    onPrimary = Color(0xFFFFFFFF),    // White text on primary
    onSecondary = Color(0xFFFFFFFF),  // White text on secondary
    onBackground = Color(0xFFFFFFFF), // White text on background
    onSurface = Color(0xFFFFFFFF),    // White text on surface
)

@Composable
fun AndroidProjectOnWeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}