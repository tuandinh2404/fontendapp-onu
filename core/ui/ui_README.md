# :iphone: core/ui

## Mục đích
Chứa các **Composable và utility tái sử dụng** không thuộc về design system (không phải theme/color) — những thứ liên quan đến behavior và UX.

> Khác với `designsystem`: `ui` chứa **logic** (overscroll, permission), `designsystem` chứa **visual** (màu, font, icon).

## Dùng khi nào?
- Cần custom overscroll → dùng `Modifier.customOverScroll()`
- Cần iOS-style squircle → dùng `SuperEllipseShape()`
- Cần custom TextField → dùng `TextFieldCustom()`
- Cần check/xin permission → dùng `rememberPermissionState()` hoặc `hasPermission()`
- Cần mở cài đặt app → dùng `Context.openAppSettings()`

## Cấu trúc

```
com.example.ui
├── CustomOverScroll.kt          ← Modifier extension: hiệu ứng bounce khi kéo quá giới hạn
│                                   Hỗ trợ: LazyListState, PagerState, Orientation
├── SuperEllipseShape.kt         ← Shape "squircle" kiểu iOS/Snapchat (n=4.5)
├── TextFieldCustom.kt           ← TextField bọc lại, transparent background
│
└── permission/
    ├── PermissionManager.kt     ← Enum OnuPermission + hasPermission() / hasPermissions()
    ├── PermissionUtils.kt       ← Context.openAppSettings()
    └── rememberPermissionState.kt ← Composable: quản lý state xin quyền
```

## API chính

### Custom overscroll
```kotlin
LazyColumn(
    modifier = Modifier.customOverScroll(
        listState = listState,
        onNewOverScrollAmount = { offset ->
            // offset: số pixel đã kéo quá → dùng để animate UI
        }
    )
)
```

### Permission
```kotlin
val cameraState = rememberPermissionState(
    permission = OnuPermission.CAMERA,
    onGranted = { /* bắt đầu camera */ },
    onDenied  = { /* hiển thị rationale */ }
)

Button(onClick = { cameraState.request() }) {
    Text(if (cameraState.isGranted) "Chụp ảnh" else "Cấp quyền camera")
}
```

## Lưu ý & Vấn đề cần fix

| Vấn đề | File | Đề xuất |
|--------|------|---------|
| `onFocusChange` nhận nhưng không dùng | `TextFieldCustom.kt` | Implement hoặc xóa param |
| `cursorColor` bị overridden | `TextFieldCustom.kt` | `cursorColor` param không có tác dụng vì bị hardcode `focusedContainerColor` |
