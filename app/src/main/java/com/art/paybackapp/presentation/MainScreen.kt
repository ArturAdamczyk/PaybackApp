package com.art.paybackapp.presentation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.art.paybackapp.presentation.detail.PhotoDetailScreen
import com.art.paybackapp.presentation.detail.PhotoDetailViewModel
import com.art.paybackapp.presentation.search.PhotoSearchScreen
import com.art.paybackapp.presentation.search.PhotoSearchViewModel

@Composable
fun MainScreen() {

    val navController = rememberNavController()
    NavHost(navController, startDestination = "photos") {

        composable("photoSearch") {
            val viewModel: PhotoSearchViewModel = hiltViewModel()
            PhotoSearchScreen(viewModel) {
                navController.navigate("photo/$it")
            }
        }

        composable(
            route = "photo/{photoId}",
            arguments = listOf(navArgument("photoId") { defaultValue = 1 })
        ) { navBackStackEntry ->
            val viewModel: PhotoDetailViewModel = hiltViewModel(navBackStackEntry)
            val photoIdArg = navBackStackEntry.arguments?.getInt("photoId")!!
            PhotoDetailScreen(viewModel, photoIdArg)
        }

    }

}