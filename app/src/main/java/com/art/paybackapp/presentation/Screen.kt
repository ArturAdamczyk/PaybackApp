package com.art.paybackapp.presentation

sealed class Screen(val route: String, val param: String = "") {

    object Search : Screen("photoSearch")

    object Details : Screen("photo/{photoId}", "photoId" )

}
