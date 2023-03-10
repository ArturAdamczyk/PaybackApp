package com.art.paybackapp.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.art.paybackapp.presentation.detail.PhotoDetailScreen
import com.art.paybackapp.presentation.detail.PhotoDetailViewModel
import com.art.paybackapp.presentation.search.PhotoSearchScreen
import com.art.paybackapp.presentation.search.PhotoSearchViewModel

@Composable
fun MainScreen(
    photoSearchViewModel: PhotoSearchViewModel,
    photoDetailViewModel: PhotoDetailViewModel
) {

    val navController = rememberNavController()
    NavHost(navController, startDestination = "photoSearch") {

        composable("photoSearch") {
            PhotoSearchScreen(photoSearchViewModel) {
                navController.navigate("photo/$it")
            }
        }

        composable(
            route = "photo/{photoId}",
            arguments = listOf(navArgument("photoId") { defaultValue = 1 })
        ) { navBackStackEntry ->
            val photoIdArg = navBackStackEntry.arguments?.getInt("photoId")!!
            PhotoDetailScreen(photoDetailViewModel, photoIdArg)
        }

    }

}