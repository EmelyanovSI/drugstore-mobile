package by.gsu.drugstore

import com.google.gson.annotations.SerializedName

class Value(
    @SerializedName("result")
    private var result: List<Mahasiswa>,
    @SerializedName("value")
    private var value: String,
    @SerializedName("message")
    private var message: String
) {

    fun setDrugs(drugs: List<Mahasiswa>) {
        this@Value.result = drugs
    }

    fun getDrugs(): List<Mahasiswa> {
        return result
    }

    fun getValue(): String {
        return value
    }

    fun getMessage(): String {
        return message
    }

}