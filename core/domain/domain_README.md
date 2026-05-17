# :brain: core/domain

## Mục đích
**Trái tim của app.** Chứa toàn bộ logic nghiệp vụ (business logic) — hoàn toàn độc lập với Android, Retrofit, Room hay bất kỳ framework nào.

> Rule: Domain **không được** import bất cứ thứ gì từ `:core:network`, `:core:data`, hay Android SDK.

## Dùng khi nào?
- Khi cần định nghĩa một entity mới → thêm vào `model/`
- Khi cần một hành động nghiệp vụ mới → thêm UseCase vào `usecase/`
- Khi cần khai báo contract với tầng Data → thêm interface vào `repository/`

## Cấu trúc

```
com.example.domain
├── model/
│   ├── User.kt               ← Entity người dùng
│   ├── AuthResult.kt         ← Kết quả đăng nhập (User + token + refreshToken)
│   ├── LoginParams.kt        ← Input cho login
│   ├── SignupParams.kt       ← Input cho signup
│   ├── CheckUsernameResult.kt ← Kết quả kiểm tra username
│   └── Weather.kt            ← Entity thời tiết (temp + type)
│
├── repository/               ← Interface — chỉ khai báo hợp đồng, KHÔNG implement
│   ├── AuthRepository.kt     ← signUp / login / logout / checkUsername
│   └── WeatherRepository.kt  ← getWeather(lat, lon)
│
└── usecase/
    ├── LoginUseCase.kt          ← Đăng nhập
    ├── SignUpUseCase.kt         ← Đăng ký
    ├── CheckUsernameUseCase.kt  ← Validate + kiểm tra username tồn tại chưa
    └── GetWeatherUseCase.kt     ← Lấy thời tiết theo tọa độ
```

## API chính

### UseCase pattern
```kotlin
// Inject vào ViewModel, gọi bằng invoke()
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {
    fun login(username: String, password: String) {
        viewModelScope.launch {
            val result = loginUseCase(LoginParams(username, password))
            // xử lý Result<AuthResult>
        }
    }
}
```

### Repository interface
```kotlin
// Data layer implement interface này
// Domain không biết gì về cách implement
interface AuthRepository {
    suspend fun login(params: LoginParams): Result<AuthResult>
}
```

## Lưu ý & Vấn đề cần fix

| Vấn đề | File | Đề xuất |
|--------|------|---------|
| Typo tên file | `AuthRespository.kt` | Đổi thành `AuthRepository.kt` |
| Tên file chữ thường | `checkUsernameUseCase.kt` | Đổi thành `CheckUsernameUseCase.kt` |
| Hardcode Dispatcher | `LoginUseCase.kt` | Dùng `@Dispatcher(IO)` thay vì `Dispatchers.IO` |
