package com.mcmouse88.appnavigation.ui

import android.net.Uri
import com.mcmouse88.appnavigation.ui.screen.ItemScreenArgs
import com.mcmouse88.links.DeepLinkHandler
import com.mcmouse88.links.MultiStackState

object AppDeepLinkHandler : DeepLinkHandler {
    override fun handleDeepLink(uri: Uri, inputState: MultiStackState): MultiStackState {
        var outputState = inputState
        if (uri.scheme == "nav") {
            if (uri.host == "settings") {
                outputState = inputState.copy(activeStackIndex = 1)
            } else if (uri.host == "items") {
                val itemIndex = uri.pathSegments?.firstOrNull()?.toIntOrNull()
                if (itemIndex != null) {
                    val editItemRoute = AppRoute.Item(ItemScreenArgs.Edit(itemIndex))
                    outputState = inputState.withNewRoutes(stackIndex = 0, route = editItemRoute)
                }
            }
        }
        return outputState
    }
}