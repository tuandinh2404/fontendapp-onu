# :package: core/common

## Mục đích
Module **nền tảng** dùng chung cho toàn bộ project. Không phụ thuộc vào bất kỳ module nào khác trong `:core`.

## Dùng khi nào?
Mọi module đều có thể import `:core:common`. Đây là điểm xuất phát — nếu bạn cần `Result`, `Dispatcher`, hay `CoroutineScope` thì lấy từ đây.

## Cấu trúc

```
com.example.common
├── network/
│   ├── OnuDispatchers.kt        ← Enum: Default, IO
│   ├── Dispatcher.kt            ← @Qualifier annotation cho DI
│   └── di/
│       ├── DispatchersModule.kt ← Provide Dispatchers.IO / Dispatchers.Default
│       └── CoroutineScopesModule.kt ← Provide ApplicationScope (singleton)
└── result/
    └── Result.kt                ← sealed interface: Success / Error / Loading
                                    + extension fun Flow<T>.asResult()
```

## API chính

### `Result<T>`
```kotlin
// Wrap bất kỳ Flow nào thành Result
viewModel.data
    .asResult()
    .collect { result ->
        when (result) {
            is Result.Success -> // hiển thị data
            is Result.Loading -> // hiển thị skeleton
            is Result.Error   -> // hiển thị lỗi
        }
    }
```

### `@Dispatcher`
```kotlin
// Inject dispatcher đúng cách thay vì hardcode
class MyRepo @Inject constructor(
    @Dispatcher(OnuDispatchers.IO) private val ioDispatcher: CoroutineDispatcher
)
```

### `@ApplicationScope`
```kotlin
// Inject CoroutineScope sống cùng app
class MyService @Inject constructor(
    @ApplicationScope private val scope: CoroutineScope
)
```

## Lưu ý
- `Result` ở đây **khác** với `kotlin.Result` — đây là custom sealed interface có thêm `Loading`
- Tên package là `network` nhưng không liên quan đến HTTP — đây là nơi đặt DI cho coroutine. Có thể đổi thành `coroutine` cho rõ hơn (xem Refactor Guide)
