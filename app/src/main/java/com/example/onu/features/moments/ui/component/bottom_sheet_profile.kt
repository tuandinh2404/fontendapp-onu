package com.example.onu.features.moments.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import com.example.onu.features.profile.ui.profile_screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun bottom_sheet_profile(
    sheetState: SheetState,
    builderController: NavHostController,
    mainController: NavHostController,
    showBottomSheet: Boolean,
    onUpClickBottomSheet: () -> Unit
) {
    val state = rememberLazyListState()
    val sheetSetting = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var showSetting by remember { mutableStateOf(false) }

    if(showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = {
                onUpClickBottomSheet()
            },
            sheetState = sheetState,
            dragHandle = {

            },
            contentWindowInsets = { WindowInsets(0, 0, 0, 0) }

        ) {
            profile_screen(
                mainController,
                state = state,
                onClickShowSetting = {
                    showSetting = true
                }
            )
            if(showSetting) {
                ModalBottomSheet(
                    onDismissRequest = {
                        showSetting = false
                    },
                    sheetState = sheetSetting,
                    dragHandle = {

                    }
                ) {
                    Box(
                        Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(0.89f)
                            .background(Color.Red)
                    )

                }
            }

        }
    }
}