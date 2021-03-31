package com.example.testapp

import com.example.testapp.apidata.BusStation
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

//

interface BusAPI {
    @GET("facilities/station/getDataList")
    fun getStationInfo(): Call<BusStation>

    companion object {
        const val BASE_URL = "http://bus.andong.go.kr:8080/api/"



        fun create(): BusAPI {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

            val headerInterceptor = Interceptor { //Header 넣는 부분 인가
                val request = it.request()
                    .newBuilder()
                    .build()
                return@Interceptor it.proceed(request)
            }

            val client = OkHttpClient.Builder() // OKHTTP 클라이언트 넣는 부분
                .addInterceptor(headerInterceptor)
                .addInterceptor(httpLoggingInterceptor)
                .build()

            return Retrofit.Builder() // retrofit 구성부분
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(BusAPI::class.java)
        }

    }
}