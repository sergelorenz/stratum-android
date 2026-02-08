package com.bvfonaps.stratum.ui.components.animations

import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlin.math.cos
import kotlin.math.sin
import com.bvfonaps.stratum.R
import kotlin.math.roundToInt


@Composable
fun SearchingAnimation (
    modifier: Modifier = Modifier,
    size: Dp = 20.dp,
    radius: Dp = 10.dp,
    durationMs: Int = 1800
) {
    val infiniteTransition = rememberInfiniteTransition(label = "search")

    val angle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMs, easing = LinearEasing)
        ),
        label = "angle"
    )

    val density = LocalDensity.current
    val radiusPx = with(density) { radius.toPx() }

    val x = cos(Math.toRadians(angle.toDouble())).toFloat() * radiusPx * 0.45f
    val y = sin(Math.toRadians(angle.toDouble())).toFloat() * radiusPx * 0.45f

    Box(
        modifier = modifier.size(size),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(R.drawable.search_icon),
            contentDescription = null,
            modifier = Modifier
                .offset { IntOffset(x.roundToInt(), y.roundToInt()) }
        )
    }
}
