package by.gsu.drugstore.model

import com.google.gson.annotations.SerializedName

class Drug(
    @SerializedName("name")
    private var name: String = "",
    @SerializedName("composition")
    private var composition: String = "",
    @SerializedName("country")
    private var country: String = ""
) {

    @SerializedName("id")
    private var id: Int = 0

    /*@SerializedName("title")
    private var title: String = ""*/

    fun setId(id: Int) {
        this@Drug.id = id
    }

    fun getId(): Int {
        return id
    }

    /*fun setTitle(title: String) {
        this@Drug.title = title
    }

    fun getTitle(): String {
        return title
    }*/

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