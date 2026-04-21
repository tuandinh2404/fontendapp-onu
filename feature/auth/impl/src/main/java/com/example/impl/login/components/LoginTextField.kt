package com.example.impl.login.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.designsystem.icon.OnuIcons
import com.example.designsystem.theme.LightGray

@Composable
fun LoginTextField(
    valueText: String,
    onChange: (String) -> Unit,
    placeholder: String = "",
    clearError: () -> Unit,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    focusRequester: FocusRequester,
    isError: Boolean,
    enabled: Boolean = true
) {
    TextField(
        value = valueText,
        onValueChange = onChange,
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .focusRequester(focusRequester),
        placeholder = {
            Text(
                text = "Tên đăng nhập",
                fontSize = 25.sp,
                color = Color.Gray
            )
        },
        textStyle = TextStyle(
            fontSize = 25.sp,
            color = LightGray,
            fontWeight = FontWeight.ExtraBold
        ),
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        singleLine = true,
        enabled = enabled,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            cursorColor = Color.Blue.copy(alpha = 0.8f),
            disabledContainerColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            disabledTextColor = Color.Transparent
        )
    )
}