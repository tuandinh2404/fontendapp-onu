# :globe_with_meridians: core/network

## Mục đích
Xử lý toàn bộ giao tiếp HTTP. Cấu hình Retrofit, OkHttp, định nghĩa API endpoint, và model request/response.

> Rule: Network **không được** dùng domain model — chỉ dùng network model riêng. Việc chuyển đổi là trách nhiệm của `:core:data`.

## Dùng khi nào?
- Thêm API endpoint mới → thêm vào `retrofit/`
- Thêm request/response model → thêm vào `model/`
- Cấu hình network (timeout, interceptor...) → sửa `di/NetworkModule.kt`

## Cấu trúc

```
com.example.network
├── di/
│   └── NetworkModule.kt          ← OkHttpClient, Retrofit, AuthApi, WeatherApi
│
├── datasource/
│   ├── AuthNetworkDataSource.kt  ← Interface: signUp / login / checkUsername
│   ├── RetrofitAuthNetwork.kt    ← Implement AuthNetworkDataSource
│   └── WeatherRemoteDataSource.kt ← Gọi WeatherApi.getWeather()
│
├── interceptor/
│   └── AuthInterceptor.kt        ← Tự động gắn "Authorization: Bearer <token>"
│
├── model/
│   ├── AuthRequest.kt            ← SignupRequest, LoginRequest
│   ├── AuthResponse.kt           ← AuthResponse (user + token + refreshToken)
│   ├── CheckUsername.kt          ← CheckUsernameResponse
│   ├── User.kt                   ← UserResponse
│   └── WeatherResponse.kt        ← WeatherResponse, MainDTO, WeatherDTO
│
└── retrofit/
    ├── AuthApi.kt                ← POST auth/signup, auth/login, auth/logout
    │                               GET  auth/check-username
    └── WeatherApi.kt             ← GET weather?lat=&lon=&appid=&units=
```

## Cấu hình hiện tại
- Base URL: `BuildConfig.BACKEND_URL`
- Timeout: 5 giây (connect + read)
- Logging: BODY level khi DEBUG
- Auth: Bearer token từ `SessionManager.cachedToken`
- Converter: Gson

## Lưu ý & Vấn đề cần fix

| Vấn đề | File | Đề xuất |
|--------|------|---------|
| Thiếu `@Inject` | `WeatherRemoteDataSource.kt` | Thêm `@Inject constructor` và `@Singleton` |
| `AuthApi` được tạo 2 lần | `NetworkModule.kt` + `RetrofitAuthNetwork.kt` | Xóa `retrofit.create()` trong `RetrofitAuthNetwork`, chỉ dùng injected `AuthApi` |
| `refreshToken` thiếu `@SerializedName` | `AuthResponse.kt` | Thêm `@SerializedName("refresh_token")` nếu server dùng snake_case |
