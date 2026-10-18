package com.project.nit3213.di

import com.google.gson.GsonBuilder
import com.project.nit3213.data.model.EntityItem
import com.project.nit3213.data.model.EntityItemDeserializer
import com.project.nit3213.data.remote.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

// Hilt module providing network-related dependencies (OkHttpClient, Retrofit, ApiService)
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    // Base URL for the backend API
    private const val BASE_URL = "https://nit3213apinew.onrender.com/"

    // Provides OkHttpClient with logging interceptor and extended timeouts for backend cold starts
    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .connectTimeout(90, TimeUnit.SECONDS) // Wait up to 90s for connection
            .readTimeout(90, TimeUnit.SECONDS)    // Wait up to 90s for data
            .writeTimeout(90, TimeUnit.SECONDS)   // Wait up to 90s for sending data
            .build()
    }

    // Provides Retrofit instance registered with a custom Gson deserializer
    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        val gson = GsonBuilder()
            .registerTypeAdapter(EntityItem::class.java, EntityItemDeserializer()) // Custom parsing for dynamic fields
            .create()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    // Provides the ApiService interface for making network API calls
    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }
}

