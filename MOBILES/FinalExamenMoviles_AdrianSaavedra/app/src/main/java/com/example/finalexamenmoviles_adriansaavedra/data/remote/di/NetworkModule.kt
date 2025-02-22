package com.example.playersapp_adriansaavedra.data.remote.di


import com.example.finalexamenmoviles_adriansaavedra.BuildConfig
import com.example.finalexamenmoviles_adriansaavedra.data.PreferencesRepository
import com.example.finalexamenmoviles_adriansaavedra.data.remote.services.AlumnoService
import com.example.finalexamenmoviles_adriansaavedra.data.remote.services.LoginService
import com.example.finalexamenmoviles_adriansaavedra.data.remote.services.RatonService
import com.example.finalexamenmoviles_adriansaavedra.data.remote.utils.AuthInterceptor
import com.example.playersapp_adriansaavedra.data.remote.utils.*
import dagger.Lazy
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
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
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        authInterceptor: AuthInterceptor,
        authAuthenticator: AuthAuthenticator
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(authInterceptor)
            .authenticator(authAuthenticator)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }


    @Singleton
    @Provides
    fun provideRatService(retrofit: Retrofit): RatonService =
        retrofit.create(RatonService::class.java)

    @Singleton
    @Provides
    fun provideAlumnoService(retrofit: Retrofit): AlumnoService =
        retrofit.create(AlumnoService::class.java)


    @Provides
    @Singleton
    fun provideLoginService(retrofit: Retrofit): LoginService =
        retrofit.create(LoginService::class.java)
    @Singleton
    @Provides
    fun provideAuthInterceptor(preferencesRepository: PreferencesRepository): AuthInterceptor =
        AuthInterceptor(preferencesRepository)

    @Singleton
    @Provides
    fun provideAuthAuthenticator(
        preferencesRepository: PreferencesRepository,
        loginService: Lazy<LoginService>
    ): AuthAuthenticator =
        AuthAuthenticator(preferencesRepository, loginService)
}