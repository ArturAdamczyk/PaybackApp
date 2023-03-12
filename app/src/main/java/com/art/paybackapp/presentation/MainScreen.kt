package com.art.paybackapp.presentation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.runtime.Composable
import androidx.navigation.navArgument
import com.art.paybackapp.presentation.screens.detail.PhotoDetailScreen
import com.art.paybackapp.presentation.screens.detail.PhotoDetailViewModel
import com.art.paybackapp.presentation.screens.search.PhotoSearchScreen
import com.art.paybackapp.presentation.screens.search.PhotoSearchViewModel
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.navigation.animation.composable

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MainScreen(
    photoSearchViewModel: PhotoSearchViewModel,
    photoDetailViewModel: PhotoDetailViewModel
) {

    val navController = rememberAnimatedNavController()
    AnimatedNavHost(navController, startDestination = Screen.Search.route) {

        composable(
            route = Screen.Search.route,
            enterTransition = null,
        ) {
            PhotoSearchScreen(photoSearchViewModel) {
                navController.navigate("photo/$it")
            }
        }

        composable(
            route = Screen.Details.route,
            enterTransition = {
                fadeIn(animationSpec = tween(2000))
            },
            arguments = listOf(navArgument(Screen.Details.param) { defaultValue = 1 })
        ) { navBackStackEntry ->
            val photoIdArg = navBackStackEntry.arguments?.getInt(Screen.Details.param)!!
            PhotoDetailScreen(photoDetailViewModel, photoIdArg)
        }

    }

}
