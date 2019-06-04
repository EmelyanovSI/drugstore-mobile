package by.gsu.drugstore.rest

import by.gsu.drugstore.model.DrugsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface API {

    @GET("api/get_all_drugs.php")
    fun getAllDrugs(): Call<DrugsResponse>

    @GET("api/get_drugs.php")
    fun getDrugs(@Query("table_name") tableName: String): Call<DrugsResponse>

    @GET("api/search_drugs.php")
    fun searchDrugs(@Query("name") name: String): Call<DrugsResponse>

    @GET("api/add_drug.php")
    fun addDrug(@Query("name") name: String,
                @Query("composition") composition: String,
                @Query("country") country: String): Call<DrugsResponse>

    @GET("api/remove_drug.php")
    fun removeDrug(@Query("id") id: Int,
                   @Query("country") country: String): Call<DrugsResponse>

}
