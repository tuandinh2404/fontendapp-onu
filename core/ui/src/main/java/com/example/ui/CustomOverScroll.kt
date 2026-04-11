package com.example.ui

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.SpringSpec
import androidx.compose.animation.core.calculateTargetValue
import androidx.compose.animation.core.exponentialDecay
import androidx.compose.animation.core.spring
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.Velocity
import kotlinx.coroutines.launch
import kotlin.getOrElse
import kotlin.isFinite
import kotlin.math.sign
import kotlin.ranges.coerceAtLeast
import kotlin.ranges.coerceAtMost
import kotlin.runCatching

@Composable
fun Modifier.customOverScroll(
    listState: LazyListState,
    onNewOverScrollAmount: (Float) -> Unit,
    animationSpec: SpringSpec<Float> = spring(stiffness = Spring.StiffnessLow)
) = customOverScroll(
    orientation = remember { listState.layoutInfo.orientation },
    onNewOverScrollAmount = onNewOverScrollAmount,
    animationSpec = animationSpec
)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Modifier.customOverScroll(
    pagerState: PagerState,
    onNewOverScrollAmount: (Float) -> Unit,
    animationSpec: SpringSpec<Float> = spring(stiffness = Spring.StiffnessLow)
) = customOverScroll(
    orientation = remember { pagerState.layoutInfo.orientation },
    onNewOverScrollAmount = onNewOverScrollAmount,
    animationSpec = animationSpec
)

@Composable
fun Modifier.customOverScroll(
    orientation: Orientation,
    onNewOverScrollAmount: (Float) -> Unit,
    animationSpec: SpringSpec<Float> = spring(stiffness = Spring.StiffnessLow)
): Modifier {
    val overScrollAmountAnimateble = remember { Animatable(0f) }

    var length by remember { mutableFloatStateOf(0f) }

    LaunchedEffect(Unit) {
        snapshotFlow { overScrollAmountAnimateble.value }.collect {
            val safeResult = runCatching {
                if(length <=0f) return@runCatching 0f

                val normalLized = it / ( length * 1.5f )
                if(!normalLized.isFinite()) return@runCatching 0f

                val tranformed = CustomEasing.transform(normalLized) * length * 0.4f
                if(!tranformed.isFinite()) return@runCatching 0f

                tranformed
            }.getOrElse { 0f }
            onNewOverScrollAmount(safeResult)
        }
    }

    val scope = rememberCoroutineScope()

    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            private fun calculateOverScroll(available: Offset): Float {
                val previous = overScrollAmountAnimateble.value
                val newValue = previous + when(orientation) {
                    Orientation.Vertical -> available.y
                    Orientation.Horizontal -> available.x
                }
                return when {
                    previous > 0 -> newValue.coerceAtLeast(0f)
                    previous < 0 -> newValue.coerceAtMost(0f)
                    else -> newValue
                }
            }

            override fun onPostScroll(
                consumed: Offset,
                available: Offset,
                source: NestedScrollSource
            ): Offset {
                scope.launch {
                    overScrollAmountAnimateble.snapTo(targetValue = calculateOverScroll(available))
                }
                return Offset.Zero
            }

            override suspend fun onPostFling(consumed: Velocity, available: Velocity): Velocity {
                val availableVelocity = when (orientation) {
                    Orientation.Vertical -> available.y
                    Orientation.Horizontal -> available.x
                }
                overScrollAmountAnimateble.animateTo(
                    targetValue = 0f,
                    initialVelocity = availableVelocity,
                    animationSpec = animationSpec
                )
                return available
            }

            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                if(overScrollAmountAnimateble.value != 0f && source != NestedScrollSource.SideEffect) {
                    scope.launch {
                        overScrollAmountAnimateble.snapTo(calculateOverScroll(available))
                    }
                    return available
                }
                return super.onPreScroll(available, source)
            }

            override suspend fun onPreFling(available: Velocity): Velocity {
                val availableVelocity = when (orientation) {
                    Orientation.Vertical -> available.y
                    Orientation.Horizontal -> available.x
                }

                if(overScrollAmountAnimateble.value != 0f && availableVelocity != 0f) {
                    var consumedVelocity = availableVelocity
                    val previousSign = overScrollAmountAnimateble.value.sign
                    val predictedEndValue = exponentialDecay<Float>().calculateTargetValue(
                        initialValue = overScrollAmountAnimateble.value,
                        initialVelocity = availableVelocity,
                    )
                    if(predictedEndValue.sign == previousSign) {
                        overScrollAmountAnimateble.animateTo(
                            targetValue = 0f,
                            initialVelocity = availableVelocity,
                            animationSpec = animationSpec
                        )
                    } else {
                        try {
                            overScrollAmountAnimateble.animateDecay(
                                initialVelocity = availableVelocity,
                                animationSpec = exponentialDecay()
                            ) {
                                if(value.sign != previousSign) {
                                    consumedVelocity -= velocity
                                    scope.launch {
                                        overScrollAmountAnimateble.snapTo(0f)
                                    }
                                }
                            }
                        } catch(e: Exception) {

                        }
                    }

                    return when (orientation) {
                        Orientation.Vertical -> Velocity(0f, consumedVelocity)
                        Orientation.Horizontal -> Velocity(consumedVelocity, 0f)
                    }
                }

                return super.onPreFling(available)
            }
        }
    }
    return this
        .onSizeChanged {
            length = when (orientation) {
                Orientation.Vertical -> it.height.toFloat()
                Orientation.Horizontal -> it.width.toFloat()
            }
        }
        .nestedScroll(nestedScrollConnection)
}
val CustomEasing: Easing = CubicBezierEasing(0.2f, 0.2f, 1.0f, 0.25f)