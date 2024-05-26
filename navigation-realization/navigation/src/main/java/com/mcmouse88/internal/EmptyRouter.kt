package com.mcmouse88.internal

import com.mcmouse88.navigation.Route
import com.mcmouse88.navigation.Router

object EmptyRouter : Router {

    override fun launch(route: Route) = Unit

    override fun pop() = Unit

    override fun restart(route: Route) = Unit
}