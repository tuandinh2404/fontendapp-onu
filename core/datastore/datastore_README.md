# :key: core/datastore

## Mục đích
Lưu trữ dữ liệu **nhỏ, nhạy cảm** trên thiết bị — cụ thể là token xác thực. Dùng Jetpack DataStore (Preferences).

## Dùng khi nào?
- Lưu/đọc/xóa token → dùng `SessionManager`
- Thêm preference key mới → thêm vào `SessionManager.kt`

## Cấu trúc

```
com.example.datastore
├── di/
│   └── DataStoreModule.kt    ← Provide DataStore<Preferences> (file: "app_prefs")
│
└── session/
    └── SessionManager.kt     ← Quản lý token
                                  cachedToken: String? (in-memory cache, nhanh)
                                  tokenFlow: Flow<String?> (reactive)
                                  getToken(): suspend (one-shot)
                                  saveToken(token)
                                  clear()
```

## Cơ chế token

```
Lần đầu app chạy:
  tokenFlow.collect → đọc từ DataStore → gán cachedToken

Khi gọi API (AuthInterceptor):
  đọc cachedToken (không block thread!)

Khi login thành công:
  saveToken(newToken) → lưu DataStore + cập nhật cachedToken

Khi logout:
  clear() → xóa DataStore + cachedToken = null
```

## Lưu ý
- `SessionManager` cần được collect sớm (Application hoặc khi app start) để `cachedToken` được khởi tạo trước khi có request API đầu tiên
- Hiện tại chỉ lưu `token`, chưa lưu `refreshToken` — cần bổ sung nếu muốn auto-refresh
