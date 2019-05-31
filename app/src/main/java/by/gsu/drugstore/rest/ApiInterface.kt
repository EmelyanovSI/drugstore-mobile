package by.gsu.drugstore.rest

import by.gsu.drugstore.model.DrugsResponse
import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {

    @GET("get_all_drugs.php")
    fun getAllDrugs(): Call<DrugsResponse>

}