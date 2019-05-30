package by.gsu.drugstore.model

import com.google.gson.annotations.SerializedName

class Drugs(
    @SerializedName("drugs")
    private var drugs: List<Drug>
) {

    fun setDrugs(drugs: List<Drug>) {
        this@Drugs.drugs = drugs
    }

    fun getDrugs(): List<Drug>? {
        return drugs
    }

    fun addDrug(drug: Drug) {
        drugs
    }

}