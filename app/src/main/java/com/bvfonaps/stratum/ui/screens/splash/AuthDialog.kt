package com.bvfonaps.stratum.ui.screens.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.platform.testTag
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bvfonaps.stratum.ui.theme.StratumTheme
import com.bvfonaps.stratum.R
import com.bvfonaps.stratum.ui.viewmodels.factory.AppViewModelProvider


@Composable
fun AuthDialog(
    modifier: Modifier = Modifier,
    viewModel: AuthViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val authTypeState by viewModel.authTypeState.collectAsState()
    val authResultState by viewModel.authResultState.collectAsState()
    AuthDialogContent(
        onDismiss = viewModel::closeAuthDialog,
        onSwitchToRegister = viewModel::switchToRegisterAuthDialog,
        onSwitchToLogin = viewModel::switchToLoginAuthDialog,
        authTypeState = authTypeState,
        authResultState = authResultState,
        onLogin = viewModel::login,
        onRegister = viewModel::register
    )
}


@Composable
fun AuthDialogContent(
    onDismiss: () -> Unit,
    onSwitchToRegister: () -> Unit,
    onSwitchToLogin: () -> Unit,
    authTypeState: AuthTypeState,
    authResultState: AuthResultState,
    onLogin: (username: String, password: String) -> Unit,
    onRegister: (username: String, password: String, confirmPassword: String) -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss
    ) {
        Card(
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .testTag("auth_dialog")
        ) {
            if (authTypeState == AuthTypeState.Login) {
                LoginDialog(
                    onSwitchToRegister = onSwitchToRegister,
                    authResultState = authResultState,
                    onLogin = onLogin
                )
            } else {
                RegisterDialog(
                    onSwitchToLogin = onSwitchToLogin,
                    authResultState = authResultState,
                    onRegister = onRegister
                )
            }
        }
    }
}


@Composable
fun LoginDialog(
    onSwitchToRegister: () -> Unit,
    onLogin: (username: String, password: String) -> Unit,
    authResultState: AuthResultState
) {
    Column(
        modifier = Modifier.padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = stringResource(R.string.login_to_continue),
            style = MaterialTheme.typography.displaySmall
        )
        AuthFeedback(authResultState)

        var username by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }

        Spacer(
            modifier = Modifier.height(8.dp)
        )
        AuthTextField(
            value = username,
            onValueChange = { username = it },
            placeholder = stringResource(R.string.username),
            modifier = Modifier.testTag("username_field")
        )
        AuthTextField(
            value = password,
            onValueChange = { password = it },
            placeholder = stringResource(R.string.password),
            isPassword = true,
            modifier = Modifier.testTag("password_field")
        )
        Spacer(
            modifier = Modifier.height(8.dp)
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            TextButton(
                onClick = onSwitchToRegister,
                colors = ButtonDefaults.textButtonColors(
                    contentColor = MaterialTheme.colorScheme.onSurface
                )
            ) {
                Text(stringResource(R.string.create_account_instead))
            }
            TextButton(
                onClick = { onLogin(username, password) }
            ) {
                Text(stringResource(R.string.login))
            }
        }
    }
}


@Composable
fun RegisterDialog(
    onSwitchToLogin: () -> Unit,
    onRegister: (username: String, password: String, confirmPassword: String) -> Unit,
    authResultState: AuthResultState
) {
    Column(
        modifier = Modifier.padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = stringResource(R.string.create_a_new_account),
            style = MaterialTheme.typography.displaySmall
        )
        AuthFeedback(authResultState)

        var username by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var confirmPassword by remember { mutableStateOf("") }

        Spacer(
            modifier = Modifier.height(8.dp)
        )
        AuthTextField(
            value = username,
            onValueChange = { username = it },
            placeholder = stringResource(R.string.username),
            modifier = Modifier.testTag("username_field")
        )
        AuthTextField(
            value = password,
            onValueChange = { password = it },
            placeholder = stringResource(R.string.password),
            isPassword = true,
            modifier = Modifier.testTag("password_field")
        )
        AuthTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            placeholder = stringResource(R.string.confirm_password),
            isPassword = true,
            modifier = Modifier.testTag("confirm_password_field")
        )
        Spacer(
            modifier = Modifier.height(8.dp)
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            TextButton(
                onClick = onSwitchToLogin,
                colors = ButtonDefaults.textButtonColors(
                    contentColor = MaterialTheme.colorScheme.onSurface
                )
            ) {
                Text(stringResource(R.string.have_an_account))
            }
            TextButton(
                onClick = { onRegister(username, password, confirmPassword) }
            ) {
                Text(stringResource(R.string.confirm))
            }
        }
    }
}


@Composable
fun AuthTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String = "",
    isPassword: Boolean = false,
    modifier: Modifier = Modifier
) {
    var passwordVisible by remember { mutableStateOf(false) }

    Surface(
        shape = RoundedCornerShape(12.dp),
        tonalElevation = 0.dp,
        shadowElevation = 8.dp,
        color = MaterialTheme.colorScheme.surface,
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            BasicTextField(
                value = value,
                onValueChange = onValueChange,
                singleLine = true,
                textStyle = MaterialTheme.typography.bodySmall.copy(
                    color = MaterialTheme.colorScheme.onSurface
                ),
                visualTransformation = when {
                    isPassword && !passwordVisible -> PasswordVisualTransformation()
                    else -> VisualTransformation.None
                },
                keyboardOptions = if (isPassword) {
                    KeyboardOptions(keyboardType = KeyboardType.Password)
                } else {
                    KeyboardOptions.Default
                },
                cursorBrush = SolidColor(MaterialTheme.colorScheme.onSurface),
                modifier = Modifier
                    .weight(1f)
            ) { innerTextField ->
                Box {
                    if (value.isEmpty()) {
                        Text(
                            text = placeholder,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                        )
                    }
                    innerTextField()
                }
            }
            if (isPassword) {
                IconButton(
                    onClick = { passwordVisible = !passwordVisible },
                    modifier = Modifier.size(20.dp)
                ) {
                    Icon(
                        imageVector = if (passwordVisible)
                            Icons.Default.Visibility
                        else
                            Icons.Default.VisibilityOff,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }
    }
}


@Composable
fun AuthFeedback(authResultState: AuthResultState) {
    when (authResultState) {
        is AuthResultState.Success -> {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .fillMaxWidth()
                    .padding(
                        vertical = 4.dp,
                        horizontal = 12.dp
                    )
                    .testTag("auth_success_feedback")
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painterResource(R.drawable.check_icon),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = stringResource(R.string.login_successful),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
        is AuthResultState.Error -> {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(MaterialTheme.colorScheme.errorContainer)
                    .fillMaxWidth()
                    .padding(
                        vertical = 4.dp,
                        horizontal = 12.dp
                    )
                    .testTag("auth_error_feedback")
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painterResource(R.drawable.error_icon),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.error,
                        modifier = Modifier
                            .size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = authResultState.message,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.testTag("auth_error_message")
                    )
                }
            }
        }
        is AuthResultState.Authenticating -> {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(MaterialTheme.colorScheme.tertiaryContainer)
                    .fillMaxWidth()
                    .padding(
                        vertical = 4.dp,
                        horizontal = 12.dp
                    )
                    .testTag("authenticating_feedback")
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(modifier = Modifier.width(2.dp))
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.tertiary,
                        strokeWidth = 2.dp,
                        modifier = Modifier
                            .size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = stringResource(R.string.authenticating),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.tertiary
                    )
                }
            }
        }
        else -> {

        }
    }
}


@Preview(showBackground = true)
@Composable
fun LoginDialogPreview() {
    StratumTheme {
        AuthDialogContent(
            onDismiss = { },
            onSwitchToLogin = { },
            onSwitchToRegister = { },
            authTypeState = AuthTypeState.Login,
            authResultState = AuthResultState.Authenticating,
            onLogin = { _, _ -> },
            onRegister = { _, _, _ -> }
        )
    }
}


@Preview(showBackground = true)
@Composable
fun RegisterDialogPreview() {
    StratumTheme {
        AuthDialogContent(
            onDismiss = { },
            onSwitchToRegister = { },
            onSwitchToLogin = { },
            authTypeState = AuthTypeState.Register,
            authResultState = AuthResultState.Error("Passwords don't match."),
            onLogin = { _, _ -> },
            onRegister = { _, _, _ -> }
        )
    }
}