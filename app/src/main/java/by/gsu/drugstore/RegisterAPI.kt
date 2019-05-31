package by.gsu.drugstore

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface RegisterAPI {
    @FormUrlEncoded
    @POST("api/insert.php")
    fun dafter(@Field("name") name: String,
               @Field("composition") composition: String
    ): Call<Value>

    @GET("api/view.php")
    fun view(): Call<Value>

}