package com.example.impl

    import androidx.compose.foundation.background
    import androidx.compose.foundation.gestures.detectTapGestures
    import androidx.compose.foundation.gestures.snapping.SnapPosition
    import androidx.compose.foundation.layout.*
    import androidx.compose.foundation.layout.Arrangement
    import androidx.compose.foundation.pager.VerticalPager
    import androidx.compose.foundation.pager.rememberPagerState
    import androidx.compose.foundation.shape.CircleShape
    import androidx.compose.foundation.shape.RoundedCornerShape
    import androidx.compose.material3.Text
    import androidx.compose.runtime.Composable
    import androidx.compose.runtime.mutableStateOf
    import androidx.compose.runtime.remember
    import androidx.compose.runtime.setValue
    import androidx.compose.runtime.getValue
    import androidx.compose.runtime.mutableLongStateOf
    import androidx.compose.runtime.rememberUpdatedState
    import androidx.compose.ui.Alignment
    import androidx.compose.ui.Modifier
    import androidx.compose.ui.draw.dropShadow
    import androidx.compose.ui.graphics.Color
    import androidx.compose.ui.graphics.shadow.Shadow
    import androidx.compose.ui.input.pointer.pointerInput
    import androidx.compose.ui.unit.DpOffset
    import androidx.compose.ui.unit.dp
    import androidx.compose.ui.zIndex
    import com.example.designsystem.theme.DarkGray
    import com.example.designsystem.theme.LightGray
    import com.example.impl.components.messenger_topBar
    import com.example.moments.component.FloatingIcon
    import com.example.moments.component.FloatingIconOverlay
    import com.example.moments.component.moments_statusbar_effect
    import com.example.moments.component.spawnFloatingIcons

@Composable
fun messenger_screen(
    onBack: () -> Unit
) {
    var icons by remember { mutableStateOf<List<FloatingIcon>>(emptyList()) }
    var lastSpawnTime by remember { mutableLongStateOf(0L) }


    Box(
        Modifier
            .fillMaxSize()
            .background(DarkGray)
    ) {
        moments_statusbar_effect(darkIcons = false)
        FriendsLayout(
            friends = List(10) { "Friend $it" },
            onDoubleTap = { emoji ->
                val now = System.currentTimeMillis()
                if (now - lastSpawnTime > 500L) {
                    lastSpawnTime = now
                    icons = spawnFloatingIcons(
                        emoji = emoji,
                        currentIcons = icons,
                        count = 1,
                    )
                }
            }
        )
        FloatingIconOverlay(
            icons = icons,
            onRemove = { id -> icons = icons.filter { it.id != id } },
            modifier = Modifier
                .zIndex(1f)
        )
        messenger_topBar(
            onBack,
        )


    }
}

@Composable
fun FriendsLayout(
    friends: List<String>,
    onDoubleTap:(String) -> Unit
) {
    val pagerState = rememberPagerState(pageCount = { friends.size })


    VerticalPager(
        state = pagerState,
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(top = 140.dp),
        pageSpacing = 20.dp,
        beyondViewportPageCount = 0,
        snapPosition = SnapPosition.Start,
    ) { page ->
        FriendCard(friends[page], onDoubleTap)
    }
}

@Composable
fun FriendCard(
    friend: String,
    onDoubleTap: (String) -> Unit
) {
    var selectedEmoji by remember { mutableStateOf("🥲") }
    val currentEmoji by rememberUpdatedState(selectedEmoji)


    Column(
        Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(15.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
//                            .graphicsLayer {
//                                rotationZ = (-7..7).random().toFloat()
//                            }
            ,
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(4f / 5f)
                    .dropShadow(
                        shape = CircleShape,
                        shadow = Shadow(
                            radius = 2.dp,
                            offset = DpOffset(0.dp, 0.dp),
                            color = LightGray.copy(alpha = 0.2f)
                        )
                    )
                    .background(Color.DarkGray, RoundedCornerShape(15.dp))
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onDoubleTap = {
                                onDoubleTap(currentEmoji)
                            }
                        )
                    }
            )
        }
        Row(
            Modifier
                .fillMaxWidth()
                .padding(start = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Box(
                Modifier
                    .size(60.dp)
                    .background(Color.Green, CircleShape)
            )
            Text(
                text = friend,
                color = Color.White
            )
        }
    }
}