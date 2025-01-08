package com.example.examfirstmoviles_adriansaavedra.data.remote.di

import com.example.examfirstmoviles_adriansaavedra.BuildConfig
import com.example.examfirstmoviles_adriansaavedra.data.remote.services.EquiposService
import com.example.examfirstmoviles_adriansaavedra.data.remote.services.PlayersService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    fun provideHTTPLoggingInterceptor() = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    @Provides
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor) =
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(BuildConfig.API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    @Provides
    fun provideUserService(retrofit: Retrofit): EquiposService = retrofit.create(EquiposService::class.java)
    
    @Provides
    fun provideCommentService(retrofit: Retrofit): PlayersService = retrofit.create(PlayersService::class.java)
}