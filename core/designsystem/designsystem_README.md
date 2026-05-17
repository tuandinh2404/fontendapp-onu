# :art: core/designsystem

## Mục đích
Hệ thống thiết kế tập trung — màu sắc, typography, icon, shape, component UI tái sử dụng. Mọi màn hình đều phải dùng từ đây, không tự định nghĩa màu hay font riêng.

## Dùng khi nào?
- Cần màu → import từ `Color.kt`
- Cần icon → dùng `OnuIcons.xxx`
- Cần ảnh trang trí → dùng `OnuImages.xxx`
- Cần áp theme → bọc trong `OnuTheme { }`
- Cần chip → dùng `FilterChip()` từ `components/`

## Cấu trúc

```
com.example.designsystem
├── components/
│   └── Chip.kt          ← FilterChip tùy chỉnh (CircleShape, custom colors)
│
├── icon/
│   ├── OnuIcons.kt      ← R.drawable reference: Home, Camera, Flash, Search...
│   └── OnuImages.kt     ← R.drawable reference: Tape, Flowers, BorderImage...
│
└── theme/
    ├── Color.kt         ← Toàn bộ màu (Material palette + custom app colors)
    ├── Type.kt          ← OnuTypography dùng Quicksand font
    ├── Theme.kt         ← OnuTheme { } — wrap MaterialTheme
    ├── Background.kt    ← BackgroundTheme + LocalBackgroundTheme
    ├── Gradient.kt      ← GradientColors + LocalGradientColors
    └── Tint.kt          ← TintTheme + LocalTintTheme
```

## Font đang dùng
| Font family | Trạng thái | Dùng ở đâu |
|---|---|---|
| **Quicksand** | ✅ Đang dùng | `OnuTypography` — toàn bộ text |
| Nunito | ⚠️ Định nghĩa nhưng chưa dùng | — |
| Oswald | ⚠️ Định nghĩa nhưng chưa dùng | — |
| Josefin Sans | ⚠️ Định nghĩa nhưng chưa dùng | — |

## Lưu ý & Vấn đề cần fix

| Vấn đề | Đề xuất |
|--------|---------|
| `Theme.kt` chưa có ColorScheme | Thêm `lightColorScheme` / `darkColorScheme` vào `OnuTheme` |
| 3 font family định nghĩa nhưng không dùng | Xóa hoặc document lại mục đích |
| `LocalBackgroundTheme`, `LocalGradientColors`, `LocalTintTheme` chưa được set | Cần set trong `OnuTheme` |
