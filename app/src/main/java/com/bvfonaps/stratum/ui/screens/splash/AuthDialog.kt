package com.bvfonaps.stratum.ui.screens.splash

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
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
    AuthDialogContent(
        onDismiss = viewModel::closeAuthDialog,
        onSwitchToRegister = viewModel::switchToRegisterAuthDialog,
        onSwitchToLogin = viewModel::switchToLoginAuthDialog,
        authTypeState = authTypeState
    )
}


@Composable
fun AuthDialogContent(
    onDismiss: () -> Unit,
    onSwitchToRegister: () -> Unit,
    onSwitchToLogin: () -> Unit,
    authTypeState: AuthTypeState
) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            if (authTypeState == AuthTypeState.Login) {
                LoginDialog(
                    onSwitchToRegister = onSwitchToRegister
                )
            } else {
                RegisterDialog(
                    onSwitchToLogin = onSwitchToLogin
                )
            }
        }
    }
}


@Composable
fun LoginDialog(
    onSwitchToRegister: () -> Unit
) {
    Column(
        modifier = Modifier.padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = stringResource(R.string.login_to_continue),
            style = MaterialTheme.typography.bodySmall
        )

        var username by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }

        Spacer(
            modifier = Modifier.height(8.dp)
        )
        AuthTextField(
            value = username,
            onValueChange = { username = it },
            placeholder = stringResource(R.string.username)
        )
        AuthTextField(
            value = password,
            onValueChange = { password = it },
            placeholder = stringResource(R.string.password),
            isPassword = true
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
                Text(stringResource(R.string.create_account))
            }
            TextButton(
                onClick = { }
            ) {
                Text(stringResource(R.string.login))
            }
        }
    }
}


@Composable
fun RegisterDialog(
    onSwitchToLogin: () -> Unit
) {
    Column(
        modifier = Modifier.padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = stringResource(R.string.create_a_new_account),
            style = MaterialTheme.typography.bodySmall
        )

        var username by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var confirmPassword by remember { mutableStateOf("") }

        Spacer(
            modifier = Modifier.height(8.dp)
        )
        AuthTextField(
            value = username,
            onValueChange = { username = it },
            placeholder = stringResource(R.string.username)
        )
        AuthTextField(
            value = password,
            onValueChange = { password = it },
            placeholder = stringResource(R.string.password),
            isPassword = true
        )
        AuthTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            placeholder = stringResource(R.string.confirm_password),
            isPassword = true
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
                onClick = { }
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
    isPassword: Boolean = false
) {
    var passwordVisible by remember { mutableStateOf(false) }

    Surface(
        shape = RoundedCornerShape(12.dp),
        tonalElevation = 0.dp,
        shadowElevation = 8.dp,
        color = MaterialTheme.colorScheme.surface
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


@Preview(showBackground = true)
@Composable
fun LoginDialogPreview() {
    StratumTheme {
        AuthDialogContent(
            onDismiss = { },
            onSwitchToLogin = { },
            onSwitchToRegister = { },
            authTypeState = AuthTypeState.Login
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
            authTypeState = AuthTypeState.Register
        )
    }
}