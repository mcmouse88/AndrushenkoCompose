package com.mcmouse88.internal

import android.os.Parcel
import android.os.Parcelable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.core.os.ParcelCompat
import com.mcmouse88.navigation.NavigationState
import com.mcmouse88.navigation.Route
import com.mcmouse88.navigation.Router
import com.mcmouse88.navigation.Screen
import com.mcmouse88.navigation.ScreenResponseReceiver
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

internal class ScreenMultiStack(
    private val stacks: SnapshotStateList<ScreenStack>,
    initialIndex: Int
) : NavigationState, Router, InternalNavigationState, Parcelable {

    override var currentStackIndex: Int by mutableIntStateOf(initialIndex)
    private val currentStack: ScreenStack get() = stacks[currentStackIndex]

    override val isRoot: Boolean get() = currentStack.isRoot
    override val currentRoute: Route get() = currentStack.currentRoute
    override val currentScreen: Screen get() = currentStack.currentScreen
    override val currentUuid: String get() = currentStack.currentUuid
    override val screenResponseReceiver: ScreenResponseReceiver
        get() = currentStack.screenResponseReceiver

    private val eventsFlow = MutableSharedFlow<NavigationEvent>(
        extraBufferCapacity = Int.MAX_VALUE
    )

    constructor(parcel: Parcel) : this(
        stacks = SnapshotStateList<ScreenStack>().also { newList ->
            ParcelCompat.readList(
                parcel,
                newList,
                ScreenStack::class.java.classLoader,
                ScreenStack::class.java
            )
        },
        initialIndex = parcel.readInt()
    )

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeList(stacks)
        dest.writeInt(currentStackIndex)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun launch(route: Route) {
        currentStack.launch(route)
    }

    override fun pop(response: Any?) {
        val removedRecord = currentStack.pop(response)
        if (removedRecord != null) {
            eventsFlow.tryEmit(NavigationEvent.Removed(removedRecord))
        }
    }

    override fun restart(rootRoutes: List<Route>, initialIndex: Int) {
        stacks.flatMap { it.getAllRouteRecords() }
            .map { NavigationEvent.Removed(it) }
            .forEach(eventsFlow::tryEmit)
        stacks.clear()
        stacks.addAll(rootRoutes.map(::ScreenStack))
        switchStack(initialIndex)
    }

    override fun switchStack(index: Int) {
        currentStackIndex = index
    }

    override fun observe(): Flow<NavigationEvent> {
        return eventsFlow
    }

    companion object CREATOR : Parcelable.Creator<ScreenMultiStack> {
        override fun createFromParcel(parcel: Parcel): ScreenMultiStack {
            return ScreenMultiStack(parcel)
        }

        override fun newArray(size: Int): Array<ScreenMultiStack?> {
            return arrayOfNulls(size)
        }
    }
}