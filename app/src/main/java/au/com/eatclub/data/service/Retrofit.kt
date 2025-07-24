package au.com.eatclub.data.service

import au.com.eatclub.BuildConfig
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory


fun getRetrofitInstance(): Retrofit = Retrofit.Builder()
  .baseUrl("https://eccdn.com.au")
  .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
  .addConverterFactory(getKotlinSerializationConverterFactory())
  .client(getClient())
  .build()

@OptIn(ExperimentalSerializationApi::class)
private fun getKotlinSerializationConverterFactory(): Converter.Factory {
  val json = Json { ignoreUnknownKeys = true }
  return json.asConverterFactory("application/json".toMediaType())
}

private fun getClient(): OkHttpClient {

  val interceptor = HttpLoggingInterceptor()
  if (BuildConfig.DEBUG)
    interceptor.level = HttpLoggingInterceptor.Level.BODY // Logs all - headers request and response
  else
    interceptor.level = HttpLoggingInterceptor.Level.NONE
  return OkHttpClient.Builder().addInterceptor(interceptor).build()
}