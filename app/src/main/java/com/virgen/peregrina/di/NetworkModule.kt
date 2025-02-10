package com.virgen.peregrina.di

import android.content.Context
import com.example.virgen_peregrina_app.R
import com.virgen.peregrina.data.api.service.VirgenPeregrinaApiClient
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
object NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofit(@ApplicationContext context: Context): Retrofit {
        val interceptor = HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        }
        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
        return Retrofit.Builder()
            .baseUrl(context.getString(R.string.api_url))
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    @Singleton
    @Provides
    fun provideApiClient(retrofit: Retrofit): VirgenPeregrinaApiClient {
        return retrofit.create(VirgenPeregrinaApiClient::class.java)
    }

}