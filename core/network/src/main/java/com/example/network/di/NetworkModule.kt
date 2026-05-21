package com.example.network.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton
import com.example.network.BuildConfig
import com.example.network.datasource.AuthNetworkDataSource
import com.example.network.datasource.RetrofitAuthNetwork
import com.example.network.interceptor.AuthInterceptor
import com.example.network.retrofit.AuthApi
import com.example.network.retrofit.WeatherApi
import dagger.Binds
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
abstract class NetworkModule {

    @Binds
    @Singleton
    abstract fun bindsAuthNetworkDataSource(
        retrofitAuthNetwork: RetrofitAuthNetwork
    ): AuthNetworkDataSource

    companion object {
        @Provides
        @Singleton
        @Named("auth_client")
        fun provideOkHttpClient(
            authInterceptor: AuthInterceptor
        ): OkHttpClient {
            return OkHttpClient.Builder()
                .addInterceptor(
                    HttpLoggingInterceptor().apply {
                        if (BuildConfig.DEBUG) setLevel(HttpLoggingInterceptor.Level.BODY)
                    }
                )
                .addInterceptor(authInterceptor)
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .build()

        }

        // =========================================================
        // OKHTTP — không có AuthInterceptor, dùng cho API bên thứ 3
        // =========================================================

        @Provides
        @Singleton
        @Named("public_client")
        fun providePublicOkHttpClient(): OkHttpClient {
            return OkHttpClient.Builder()
                .addInterceptor(
                    HttpLoggingInterceptor().apply {
                        if (BuildConfig.DEBUG) setLevel(HttpLoggingInterceptor.Level.BODY)
                    }
                )
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .build()
        }

        // =========================================================
        // MAIN RETROFIT
        // =========================================================
        @Provides
        @Singleton
        @Named("main_retrofit")
        fun provideMainRetrofit(
            @Named("auth_client")
            okHttpClient: OkHttpClient
        ): Retrofit {
            return Retrofit.Builder()
                .baseUrl(BuildConfig.BACKEND_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
        }

        // =========================================================
        // WEATHER RETROFIT
        // =========================================================

        @Provides
        @Singleton
        @Named("weather_retrofit")
        fun provideWeatherRetrofit(
            @Named("public_client")
            okHttpClient: OkHttpClient
        ): Retrofit {

            return Retrofit.Builder()
                .baseUrl(
                    "https://api.openweathermap.org/data/2.5/"
                )
                .addConverterFactory(
                    GsonConverterFactory.create()
                )
                .client(okHttpClient)
                .build()
        }

        // =========================================================
        // AUTH API
        // =========================================================
        @Provides
        @Singleton
        fun provideAuthApi(
            @Named("main_retrofit")
            retrofit: Retrofit
        ): AuthApi {
            return retrofit.create(AuthApi::class.java)
        }


        // =========================================================
        // WEATHER API
        // =========================================================

        @Provides
        @Singleton
        fun provideWeatherApi(
            @Named("weather_retrofit")
            retrofit: Retrofit
        ): WeatherApi {
            return retrofit.create(
                WeatherApi::class.java
            )
        }

        // =========================================================
        // WEATHER API KEY
        // =========================================================

        @Provides
        @Named("weather_api_key")
        fun provideWeatherApiKey(): String {
            return BuildConfig.WEATHER_API_KEY
        }

    }
}