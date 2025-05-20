package com.ahrorovk.labwork.di

import android.content.Context
import com.ahrorovk.labwork.core.HttpRoutes
import com.ahrorovk.labwork.data.local.dataStore.DataStoreManager
import com.ahrorovk.labwork.data.network.AuthRepositoryImpl
import com.ahrorovk.labwork.data.network.LabWorkRepositoryImpl
import com.ahrorovk.labwork.data.network.remote.AuthApi
import com.ahrorovk.labwork.data.network.remote.LabWorkApi
import com.ahrorovk.labwork.domain.AuthRepository
import com.ahrorovk.labwork.domain.LabWorkRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CoreModule {
    @Provides
    @Singleton
    fun provideLaBWorkApi(): LabWorkApi =
        Retrofit
            .Builder()
            .baseUrl(HttpRoutes.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(
                (OkHttpClient.Builder().addInterceptor(
                    HttpLoggingInterceptor().setLevel(
                        HttpLoggingInterceptor.Level.BODY
                    )
                ).build())
            )
            .build()
            .create(LabWorkApi::class.java)

    @Provides
    @Singleton
    fun provideAuthApi(): AuthApi =
        Retrofit
            .Builder()
            .baseUrl(HttpRoutes.AUTH_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(
                (OkHttpClient.Builder().addInterceptor(
                    HttpLoggingInterceptor().setLevel(
                        HttpLoggingInterceptor.Level.BODY
                    )
                ).build())
            )
            .build()
            .create(AuthApi::class.java)

    @Provides
    @Singleton
    fun provideLabWorkRepository(labWorkApi: LabWorkApi): LabWorkRepository =
        LabWorkRepositoryImpl(labWorkApi)

    @Provides
    @Singleton
    fun provideAuthRepository(authApi: AuthApi): AuthRepository =
        AuthRepositoryImpl(authApi)

    @Provides
    @Singleton
    fun provideDataStoreManager(@ApplicationContext context: Context): DataStoreManager =
        DataStoreManager(context)
}