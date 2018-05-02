package paging.example.cheese

import io.reactivex.Single
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.Result
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface CheeseApi {
    companion object {
        fun create() : CheeseApi {
            return Retrofit.Builder()
                .addConverterFactory(MoshiConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(OkHttpClient.Builder()
                    .addInterceptor(HttpLoggingInterceptor()
                        .setLevel(HttpLoggingInterceptor.Level.BASIC))
                    .build())
                .baseUrl("http://10.0.2.2:5000")
                .build()
                .create(CheeseApi::class.java)
        }
    }

    @GET("/cheeses.json")
    fun getCheeses(@Query("offset") offset: Int,
                   @Query("limit") limit: Int): Single<Result<Cheese.Array>>

    @GET("/count.json")
    fun getCount(): Single<Result<Int>>

    @PUT("/cheeses/{id}/like.json")
    fun like(@Path("id") id: Int): Single<Result<Cheese>>
}
