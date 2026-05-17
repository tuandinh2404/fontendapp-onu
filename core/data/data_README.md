# :floppy_disk: core/data

## Mục đích
Tầng **triển khai** (implementation layer). Kết nối domain với hạ tầng — lấy dữ liệu từ network/database rồi chuyển đổi sang dạng domain hiểu được.

> Rule: Data **chỉ được** implement interface từ `:core:domain`. Data **không được** expose model của network ra ngoài.

## Dùng khi nào?
- Khi implement một `Repository` interface từ domain → thêm vào `repository/`
- Khi cần chuyển đổi network response → domain model → thêm mapper vào `mapper/`
- Khi cần đọc dữ liệu từ cảm biến → thêm vào `sensor/`

## Cấu trúc

```
com.example.data
├── di/
│   └── DataModule.kt              ← Bind AuthRepositoryImpl → AuthRepository
│
├── mapper/
│   ├── AuthMapper.kt              ← LoginParams/SignupParams ↔ Request/Response ↔ Domain
│   ├── WeatherMapper.kt           ← WeatherResponse → Weather
│   └── WeatherIconMapper.kt       ← ⚠️ Chưa implement (đang comment toàn bộ)
│
├── repository/
│   ├── AuthRepositoryImpl.kt      ← Gọi AuthNetworkDataSource, wrap try/catch → Result
│   └── WeatherRepositoryImpl.kt   ← Gọi WeatherRemoteDataSource
│
└── sensor/
    └── AccelerometerManager.kt    ← ⚠️ Class rỗng, chưa implement
```

## Luồng dữ liệu

```
UseCase → Repository (interface) → RepositoryImpl → NetworkDataSource → API
                                                  ↓
                                              Mapper
                                                  ↓
                                           Domain Model
```

## Lưu ý & Vấn đề cần fix

| Vấn đề | File | Đề xuất |
|--------|------|---------|
| Chưa bind WeatherRepository | `DataModule.kt` | Thêm `@Binds` cho `WeatherRepositoryImpl` |
| Class rỗng | `AccelerometerManager.kt` | Implement hoặc xóa nếu chưa cần |
| File comment toàn bộ | `WeatherIconMapper.kt` | Implement hoặc xóa |
| Typo tên class | `AuthRespositoryImpl.kt` | Đổi thành `AuthRepositoryImpl.kt` |
