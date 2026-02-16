package com.bvfonaps.stratum.ui.screens.splash

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bvfonaps.stratum.ui.theme.StratumTheme
import com.bvfonaps.stratum.R
import com.bvfonaps.stratum.ui.components.animations.SearchingAnimation
import com.bvfonaps.stratum.ui.viewmodels.factory.AppViewModelProvider
import com.bvfonaps.stratum.ui.navigation.NavigationDestination


object SplashDestination : NavigationDestination {
    override val route = "home"
}


@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    viewModel: DiscoveryViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val discoveryState by viewModel.discoveryState.collectAsState()
    val showAuthState by viewModel.showAuthState.collectAsState()
    SplashContent(
        onOpenAuthDialog = viewModel::openAuthDialog,
        onCloseAuthDialog = viewModel::closeAuthDialog,
        discoveryState = discoveryState,
        showAuthState = showAuthState,
        onClickSearch = viewModel::discover,
        modifier = modifier,
    )
}


@Composable
fun SplashContent(
    onOpenAuthDialog: () -> Unit,
    onCloseAuthDialog: () -> Unit,
    discoveryState: DiscoveryState,
    showAuthState: ShowAuthState,
    onClickSearch: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.app_name),
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(128.dp))
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                when (discoveryState) {
                    is DiscoveryState.Found -> {
                        FoundServerButton(
                            onClickButton = onOpenAuthDialog,
                            modifier = modifier
                        )
                    }
                    DiscoveryState.NotFound -> {
                        NotFoundServerButton(
                            modifier = modifier,
                            onClickSearch = onClickSearch
                        )
                    }
                    else -> {
                        SearchServerButton(
                            uiState = discoveryState,
                            onClickSearch = onClickSearch,
                            modifier = modifier
                        )
                    }
                }
                if (discoveryState == DiscoveryState.Idle) {
                    OutlinedButton(
                        onClick = { },
                        modifier = Modifier.fillMaxWidth(0.8f),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = MaterialTheme.colorScheme.tertiary,
                        )
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.question_mark_icon),
                            contentDescription = stringResource(R.string.how_stratum_works)
                        )
                        Spacer(modifier = modifier.width(16.dp))
                        Text(
                            text = stringResource(R.string.how_stratum_works),
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }
        }
        if (showAuthState == ShowAuthState.Open) {
            AuthDialog(onDismiss = onCloseAuthDialog)
        }
    }
}


@Composable
fun SearchServerButton(
    uiState: DiscoveryState,
    onClickSearch: () -> Unit,
    modifier: Modifier
) {

    @StringRes val searchResource: Int = when (uiState) {
        DiscoveryState.Searching -> R.string.searching
        else -> R.string.search_for_server
    }

    Button(
        onClick = onClickSearch,
        modifier = Modifier.fillMaxWidth(0.8f),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.tertiary
        )
    ) {
        if (uiState === DiscoveryState.Searching) {
            SearchingAnimation(
                size = 24.dp
            )
        } else {
            Icon(
                painterResource(R.drawable.search_icon),
                contentDescription = null
            )
        }
        Spacer(modifier = modifier.width(16.dp))
        Text(
            text = stringResource(searchResource),
            style = MaterialTheme.typography.bodySmall
        )
    }
}


@Composable
fun FoundServerButton(
    onClickButton: () -> Unit,
    modifier: Modifier
) {
    Button(
        onClick = onClickButton,
        modifier = Modifier.fillMaxWidth(0.8f),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary
        )
    ) {
        Icon(
            painterResource(R.drawable.check_icon),
            contentDescription = null
        )
        Spacer(modifier = modifier.width(16.dp))
        Text(
            text = stringResource(R.string.found_server),
            style = MaterialTheme.typography.bodySmall
        )
    }
}


@Composable
fun NotFoundServerButton(
    onClickSearch: () -> Unit,
    modifier: Modifier
) {
    Button(
        onClick = onClickSearch,
        modifier = Modifier.fillMaxWidth(0.8f),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.error
        )
    ) {
        Icon(
            painterResource(R.drawable.error_icon),
            contentDescription = null
        )
        Spacer(modifier = modifier.width(16.dp))
        Text(
            text = stringResource(R.string.not_found_server),
            style = MaterialTheme.typography.bodySmall
        )
    }
}


@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    StratumTheme(darkTheme = true) {
        SplashContent(
            discoveryState = DiscoveryState.NotFound,
            showAuthState = ShowAuthState.Open,
            onClickSearch = { },
            onCloseAuthDialog = { },
            onOpenAuthDialog = { }
        )
    }
}