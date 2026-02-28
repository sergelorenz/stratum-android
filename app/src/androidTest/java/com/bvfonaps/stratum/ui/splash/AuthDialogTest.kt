package com.bvfonaps.stratum.ui.splash

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.bvfonaps.stratum.ui.screens.splash.AuthDialogContent
import com.bvfonaps.stratum.ui.screens.splash.AuthResultState
import com.bvfonaps.stratum.ui.screens.splash.AuthTypeState
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AuthDialogTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var authResultState: MutableState<AuthResultState>
    private lateinit var authTypeState: MutableState<AuthTypeState>

    @Before
    fun setUp() {
        authResultState = mutableStateOf(AuthResultState.Idle)
        authTypeState = mutableStateOf(AuthTypeState.Login)

        composeTestRule.setContent {
            AuthDialogContent(
                onDismiss = { },
                onSwitchToRegister = { },
                onSwitchToLogin = { },
                authTypeState = authTypeState.value,
                authResultState = authResultState.value,
                onLogin = { _, _ -> },
                onRegister = { _, _, _ -> }
            )
        }
    }

    @Test
    fun authDialog_login_idle_usernameAndPasswordFieldsAreDisplayed() {
        composeTestRule
            .onNodeWithTag("username_field")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithTag("password_field")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithTag("confirm_password_field")
            .assertIsNotDisplayed()
    }

    @Test
    fun authDialog_register_idle_usernameAndPasswordAndConfirmPasswordFieldsAreDispalyed() {
        authTypeState.value = AuthTypeState.Register

        composeTestRule
            .onNodeWithTag("username_field")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithTag("password_field")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithTag("confirm_password_field")
            .assertIsDisplayed()
    }

    @Test
    fun authDialog_authenticating_displaysAuthenticatingFeedback() {
        authResultState.value = AuthResultState.Authenticating

        composeTestRule
            .onNodeWithTag("authenticating_feedback")
            .assertIsDisplayed()
    }

    @Test
    fun authDialog_success_displaysAuthSuccessFeedback() {
        authResultState.value = AuthResultState.Success

        composeTestRule
            .onNodeWithTag("auth_success_feedback")
            .assertIsDisplayed()
    }

    @Test
    fun authDialog_error_displaysAuthErrorFeedback() {
        val message = "Random Error"
        authResultState.value = AuthResultState.Error(message)

        composeTestRule
            .onNodeWithTag("auth_error_feedback")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithTag("auth_error_message")
            .assertTextEquals(message)
    }
}