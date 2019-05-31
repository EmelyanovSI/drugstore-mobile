package by.gsu.drugstore

import by.gsu.drugstore.model.Drug
import retrofit2.Call
import retrofit2.http.GET

interface JsonPlaceHolderApi {

    @GET("get_all_drugs.php")
    fun getPosts(): Call<List<Drug>>


}