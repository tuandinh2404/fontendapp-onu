package com.example.impl.register.components.steps

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.designsystem.theme.LightGray
import com.example.impl.register.components.StepTextField

@Composable
fun UidStep(
    textUid: String,
    onChangeUid: (String) -> Unit,

) {
    Column(
        Modifier
            .fillMaxWidth()
    ) {
        Box(
            Modifier
                .fillMaxWidth()
                .padding(
                    start = 20.dp
                )
        ) {
            Text(
                text = "Nghĩ về uid của bạn?",
                fontSize = 40.sp,
                fontWeight = FontWeight.ExtraBold,
                color = LightGray
            )

        }
        Box(
            Modifier
                .fillMaxWidth()
                .padding(top = 20.dp)
        ) {
            StepTextField(
                value = textUid,
                onChange = { input ->
                    if (input.length <= 20 && input.all { (it.isLetterOrDigit() || it == '_') && it.code < 128 }) {
                        onChangeUid(input)
                    }
                },
                placeholder = "UId",

                )
        }
    }
}