package by.gsu.drugstore.model

import com.google.gson.annotations.SerializedName

class Drug(
    @SerializedName("name")
    private var name: String = "Constructor value",
    @SerializedName("composition")
    private var composition: String = "Constructor value",
    @SerializedName("country")
    private var country: String = "Constructor value"
) {

    @SerializedName("id")
    private var id: Int = 0

    fun setId(id: Int) {
        this@Drug.id = id
    }

    fun getId(): Int {
        return id
    }

    fun setName(name: String) {
        this@Drug.name = name
    }

    fun getName(): String {
        return name
    }

    fun setComposition(composition: String) {
        this@Drug.composition = composition
    }

    fun getComposition(): String {
        return composition
    }

    fun setCountry(country: String) {
        this@Drug.country = country
    }

    fun getCountry(): String {
        return country
    }

}