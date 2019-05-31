package by.gsu.drugstore

import com.google.gson.annotations.SerializedName

class Mahasiswa(
    @SerializedName("name")
    private var name: String,
    @SerializedName("composition")
    private var composition: String,
    @SerializedName("country")
    private var country: String
) {

    fun setName(name: String) {
        this@Mahasiswa.name = name
    }

    fun getName(): String? {
        return name
    }

    fun setComposition(composition: String) {
        this@Mahasiswa.composition = composition
    }

    fun getComposition(): String? {
        return composition
    }

    fun setCountry(country: String) {
        this@Mahasiswa.country = country
    }

    fun getCountry(): String? {
        return country
    }

}