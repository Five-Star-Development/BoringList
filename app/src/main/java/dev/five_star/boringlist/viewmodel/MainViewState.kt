package dev.five_star.boringlist.viewmodel

import dev.five_star.boringlist.model.BoringItem

/**
 * This data class reporesentes the view state for the main screen. All of this
 * data should be formatted in a way that the home screen can just take the
 * information and display it.
 */
data class MainViewState(
    var boringList: List<BoringItem> = emptyList()
)
