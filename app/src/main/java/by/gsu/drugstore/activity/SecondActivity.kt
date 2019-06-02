package by.gsu.drugstore.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import by.gsu.drugstore.R
import by.gsu.drugstore.model.Drug
import by.gsu.drugstore.model.DrugsResponse
import by.gsu.drugstore.rest.ApiClient
import by.gsu.drugstore.rest.API
import kotlinx.android.synthetic.main.activity_second.*
import kotlinx.android.synthetic.main.tool_bar.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SecondActivity : AppCompatActivity() {

    private var drugs: List<Drug> = emptyList()
    private var message: String = ""
    private var statusCode: Int = 0
    private var success: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        setSupportActionBar(tool_bar)
        supportActionBar?.title = resources.getString(R.string.add_drug)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        button.setOnClickListener {
            addData()
            editTextInput.setText("")
            editTextInput2.setText("")
            editTextInput3.setText("")

        }

        button2.setOnClickListener {
            editTextInput.setText("")
            editTextInput2.setText("")
            editTextInput3.setText("")
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun addData() {
        val apiService = ApiClient().getClient()?.create(API::class.java)
        val call = apiService?.addDrug(
            editTextInput.text.toString(),
            editTextInput2.text.toString(),
            editTextInput3.text.toString()
        )
        call?.enqueue(object : Callback<DrugsResponse> {
            override fun onFailure(call: Call<DrugsResponse>, t: Throwable) {
                Toast.makeText(applicationContext, "add onFailure", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<DrugsResponse>, response: Response<DrugsResponse>) {
                statusCode = response.code()
                drugs = response.body().getDrugs()
                success = response.body().getSuccess()
                message = response.body().getMessage()
                Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
            }
        })
    }

}
