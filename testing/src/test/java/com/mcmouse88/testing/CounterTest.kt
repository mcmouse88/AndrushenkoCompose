package com.mcmouse88.testing

import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class CounterTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    fun setUp() {
        composeTestRule.setContent { Counter() }
    }

    @Test
    fun clickIncrementButton_incrementsCounter(): Unit = with(composeTestRule) {
        // act
        onNodeWithTag(INCREMENT_BUTTON).performClick()

        // assert
        onNodeWithTag(COUNTER_TEXT).assertTextEquals("1")
    }

    @Test
    fun initialCounter_containsZeroValue(): Unit = with(composeTestRule) {
        // assert
        onNodeWithTag(COUNTER_TEXT).assertTextEquals("0")
    }
}