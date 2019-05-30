package by.gsu.drugstore.model

import com.google.gson.annotations.SerializedName

class Drug(
    @SerializedName("name")
    private var name: String,
    @SerializedName("composition")
    private var composition: String,
    @SerializedName("country")
    private var country: String
) {

    /*@SerializedName("country")
    private lateinit var country: String
    @SerializedName("name")
    private lateinit var name: String
    @SerializedName("composition")
    private lateinit var composition: String*/

    fun setName(name: String) {
        this@Drug.name = name
    }

    fun getName(): String? {
        return name
    }

    fun setComposition(composition: String) {
        this@Drug.composition = composition
    }

    fun getComposition(): String? {
        return composition
    }

    fun setCountry(country: String) {
        this@Drug.country = country
    }

    fun getCountry(): String? {
        return country
    }

}