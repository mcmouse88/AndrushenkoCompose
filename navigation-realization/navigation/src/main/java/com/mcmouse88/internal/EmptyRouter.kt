package com.mcmouse88.internal

import com.mcmouse88.navigation.Route
import com.mcmouse88.navigation.Router

object EmptyRouter : Router {

    override fun launch(route: Route) = Unit

    override fun pop(response: Any?) = Unit

    override fun restart(rootRoutes: List<Route>, initialIndex: Int) = Unit

    override fun switchStack(index: Int) = Unit
}