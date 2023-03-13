package com.art.paybackapp.presentation.ui.styles

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val DarkColorScheme = darkColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40,
    surface = TextColorLight
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40,
    surface = TextColorDark
)

@Composable
fun PaybackAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {

    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    val systemUiController = rememberSystemUiController()

    if(darkTheme){
        systemUiController.setSystemBarsColor(color = colorScheme.background, darkIcons = false)
        systemUiController.setNavigationBarColor( color = colorScheme.background, darkIcons = false)
    }else{
        systemUiController.setSystemBarsColor(color = colorScheme.background, darkIcons = true)
        systemUiController.setNavigationBarColor( color = colorScheme.background, darkIcons = true)
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}