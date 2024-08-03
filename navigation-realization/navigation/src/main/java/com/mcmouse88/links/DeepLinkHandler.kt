package com.mcmouse88.links

import android.net.Uri

fun interface DeepLinkHandler {
    fun handleDeepLink(
        uri: Uri,
        inputState: MultiStackState
    ): MultiStackState

    companion object {
        val DEFAULT = DeepLinkHandler { _, inputState -> inputState }
    }
}