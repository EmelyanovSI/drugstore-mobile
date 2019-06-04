package by.gsu.drugstore.model

import com.google.gson.annotations.SerializedName

class DrugsResponse(
    @SerializedName("success")
    private var success: Int = 0,
    @SerializedName("message")
    private var message: String = "",
    @SerializedName("drugs")
    private var drugs: List<Drug> = emptyList()
) {

    constructor(success: Int) : this() {
        this@DrugsResponse.success = success
    }

    constructor(message: String) : this() {
        this@DrugsResponse.message = message
    }

    constructor(drugs: List<Drug>) : this() {
        this@DrugsResponse.drugs = drugs
    }

    constructor(success: Int, message: String) : this() {
        this@DrugsResponse.success = success
        this@DrugsResponse.message = message
    }

    fun setSuccess(success: Int) {
        this@DrugsResponse.success = success
    }

    fun getSuccess(): Int {
        return success
    }

    fun setMessage(message: String) {
        this@DrugsResponse.message = message
    }

    fun getMessage(): String {
        return message
    }

    fun setDrugs(drugs: List<Drug>) {
        this@DrugsResponse.drugs = drugs
    }

    fun getDrugs(): List<Drug> {
        return drugs
    }

}