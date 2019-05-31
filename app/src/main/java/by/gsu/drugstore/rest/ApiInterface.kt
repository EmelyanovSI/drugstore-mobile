package by.gsu.drugstore.rest

import by.gsu.drugstore.model.Drug
import by.gsu.drugstore.model.Drugs
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface ApiInterface {
    @GET("get_all_drugs.php")
    fun getAllDrugs(@Query("api_key") apiKey: String): Call<Drugs>

    @GET("movie/{id}")
    fun getDrugsDetails(@Path("id") id: Int, @Query("api_key") apiKey: String): Call<Drug>
}