package com.bvfonaps.stratum.ui.splash

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.bvfonaps.stratum.data.remote.api.utils.ShowAuthState
import com.bvfonaps.stratum.ui.screens.splash.DiscoveryState
import com.bvfonaps.stratum.ui.screens.splash.DiscoveryViewModel
import com.bvfonaps.stratum.ui.screens.splash.SplashContent
import com.bvfonaps.stratum.ui.screens.splash.SplashScreen
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SplashScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var discoveryState: MutableState<DiscoveryState>
    private lateinit var showAuthState: MutableState<ShowAuthState>

    @Before
    fun setUp() {
        discoveryState = mutableStateOf(DiscoveryState.Idle)
        showAuthState = mutableStateOf(ShowAuthState.Closed)

        composeTestRule.setContent {
            SplashContent(
                onOpenAuthDialog = { },
                onCloseAuthDialog = { },
                discoveryState = discoveryState.value,
                showAuthState = showAuthState.value,
                onClickSearch = { }
            )
        }
    }

    @Test
    fun splashScreen_displaysStratum() {
        composeTestRule
            .onNodeWithTag("main_title")
            .assertIsDisplayed()
    }

    @Test
    fun splashScreen_idle_displaysFindNetwork() {
        discoveryState.value = DiscoveryState.TestingConnection

        composeTestRule
            .onNodeWithTag("search_server_button")
            .assertIsNotDisplayed()

        discoveryState.value = DiscoveryState.Idle

        composeTestRule
            .onNodeWithTag("search_server_button")
            .assertIsDisplayed()
    }

    @Test
    fun splashScreen_foundNetwork_displaysFoundNetwork() {
        discoveryState.value = DiscoveryState.Found("Sample")
        composeTestRule
            .onNodeWithTag("found_server_button")
            .assertIsDisplayed()
    }

    @Test
    fun splashScreen_notFoundNetwork_displaysNotFoundNetwork() {
        discoveryState.value = DiscoveryState.NotFound
        composeTestRule
            .onNodeWithTag("not_found_server_button")
            .assertIsDisplayed()
    }

    @Test
    fun splashScreen_showAuth_displaysAuthDialog() {
        showAuthState.value = ShowAuthState.Open
        composeTestRule
            .onNodeWithTag("auth_dialog")
            .assertIsDisplayed()
    }

    @Test
    fun splashScreen_noShowAuth_doesNotDisplayAuthDialog() {
        showAuthState.value = ShowAuthState.Closed
        composeTestRule
            .onNodeWithTag("auth_dialog")
            .assertIsNotDisplayed()
    }
}