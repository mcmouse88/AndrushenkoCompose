package com.mcmouse88.internal

import android.os.Parcel
import android.os.Parcelable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.core.os.ParcelCompat
import com.mcmouse88.navigation.NavigationState
import com.mcmouse88.navigation.Route
import com.mcmouse88.navigation.Router
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

internal class ScreenStack(
    private val routes: SnapshotStateList<Route>
) : NavigationState, Router, InternalNavigationState, Parcelable {

    override val isRoot: Boolean
        get() = routes.size == 1
    override val currentRoute: Route
        get() = routes.last()

    private val eventsFlow = MutableSharedFlow<NavigationEvent>(
        extraBufferCapacity = Int.MAX_VALUE
    )

    constructor(parcel: Parcel) : this(
        SnapshotStateList<Route>().also { newList ->
            ParcelCompat.readList(
                parcel,
                newList,
                Route::class.java.classLoader,
                Route::class.java
            )
        }
    )

    override fun launch(route: Route) {
        routes.add(route)
    }

    override fun pop() {
        val removed =  routes.removeLastOrNull()
        if (removed != null) {
            eventsFlow.tryEmit(NavigationEvent.Removed(removed))
        }
    }

    override fun restart(route: Route) {
        routes.apply {
            routes.forEach {
                eventsFlow.tryEmit(NavigationEvent.Removed(it))
            }
            clear()
            add(route)
        }
    }

    override fun observe(): Flow<NavigationEvent> {
        return eventsFlow
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeList(routes)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ScreenStack> {
        override fun createFromParcel(parcel: Parcel): ScreenStack {
            return ScreenStack(parcel)
        }

        override fun newArray(size: Int): Array<ScreenStack?> {
            return arrayOfNulls(size)
        }
    }
}