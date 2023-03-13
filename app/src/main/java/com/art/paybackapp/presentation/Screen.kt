package com.art.paybackapp.presentation

sealed class Screen(val name: String, val route: String, val param: String = "") {

    object Search : Screen("Search","photoSearch")

    object Details : Screen("Details","photo/{photoId}", "photoId" )

}
