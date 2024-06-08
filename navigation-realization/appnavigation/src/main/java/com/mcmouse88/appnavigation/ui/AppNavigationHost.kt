package com.mcmouse88.appnavigation.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mcmouse88.navigation.Navigation
import com.mcmouse88.navigation.NavigationHost

@Composable
fun AppNavigationHost(
    navigation: Navigation,
    modifier: Modifier = Modifier
) {
    NavigationHost(
        navigation = navigation,
        modifier = modifier
    )
}