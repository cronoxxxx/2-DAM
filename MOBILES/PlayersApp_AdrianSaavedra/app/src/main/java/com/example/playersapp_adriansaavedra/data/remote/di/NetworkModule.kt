package com.example.playersapp_adriansaavedra.data.remote.di

import com.example.playersapp_adriansaavedra.BuildConfig
import com.example.playersapp_adriansaavedra.data.remote.services.FavoritePlayerService
import com.example.playersapp_adriansaavedra.data.remote.services.LoginService
import com.example.playersapp_adriansaavedra.data.remote.services.PlayerService
import com.example.playersapp_adriansaavedra.data.remote.utils.*
import dagger.*
import dagger.hilt.*
import dagger.hilt.components.*
import okhttp3.*
import okhttp3.logging.*
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    fun provideHTTPLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return interceptor
    }



    @Provides
    fun provideBaseOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        authInterceptor: AuthInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(authInterceptor)
            .build()
    }



    @Provides
    @Singleton
    fun provideRetrofit( okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideAuthService(retrofit: Retrofit): LoginService =
        retrofit.create(LoginService::class.java)

    @Singleton
    @Provides
    fun providePlayerService(retrofit: Retrofit): PlayerService =
        retrofit.create(PlayerService::class.java)

    @Singleton
    @Provides
    fun provideFavoritePlayerService(retrofit: Retrofit): FavoritePlayerService =
        retrofit.create(FavoritePlayerService::class.java)


}