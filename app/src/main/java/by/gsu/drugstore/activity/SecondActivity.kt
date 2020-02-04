package by.gsu.drugstore.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
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
            when (button.text) {
                resources.getString(R.string.add) -> {
                    if (editTextInput.length() > 2 && editTextInput2.length() > 2 && editTextInput3.length() > 2) {
                        addData()
                        editTextInput.setText("")
                        editTextInput2.setText("")
                        editTextInput3.setText("")
                    } else {
                        if (editTextInput.length() <= 2)
                            editTextInput.error = resources.getString(R.string.incorrect_value)
                        if (editTextInput2.length() <= 2)
                            editTextInput2.error = resources.getString(R.string.incorrect_value)
                        if (editTextInput3.length() <= 2)
                            editTextInput3.error = resources.getString(R.string.incorrect_value)
                    }
                }
                resources.getString(R.string.remove) -> {
                    if (editTextInput.text != null && editTextInput3.length() > 2) {
                        removeData()
                        editTextInput.setText("")
                        editTextInput3.setText("")
                    } else {
                        if (editTextInput.text == null)
                            editTextInput.error = resources.getString(R.string.incorrect_value)
                        if (editTextInput3.length() <= 2)
                            editTextInput3.error = resources.getString(R.string.incorrect_value)
                    }
                }
            }
        }

        button2.setOnClickListener {
            editTextInput.setText("")
            editTextInput2.setText("")
            editTextInput3.setText("")
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_activity_second, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_add -> {
                supportActionBar?.title = resources.getString(R.string.add_drug)
                editText.hint = resources.getString(R.string.title)
                button.text = resources.getString(R.string.add)

                editTextInput2.isEnabled = true
                editText2.hint = resources.getString(R.string.active_substance)
                editTextInput2.visibility = View.VISIBLE /*isVisible = true*/
            }
            R.id.action_remove -> {
                supportActionBar?.title = resources.getString(R.string.remove_drug)
                editText.hint = resources.getString(R.string.id)
                button.text = resources.getString(R.string.remove)

                editTextInput2.setText("")
                editTextInput2.isEnabled = false
                editText2.hint = null
                editTextInput2.visibility = View.INVISIBLE /*isVisible = false*/
            }
        }
        return super.onOptionsItemSelected(item)
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
                Toast.makeText(
                    applicationContext,
                    resources.getString(R.string.no_internet_connection),
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onResponse(call: Call<DrugsResponse>, response: Response<DrugsResponse>) {
                statusCode = response.code()
                success = response.body().getSuccess()
                message = response.body().getMessage()
                drugs = response.body().getDrugs()
                Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun removeData() {
        val apiService = ApiClient().getClient()?.create(API::class.java)
        val call = apiService?.removeDrug(
            editTextInput.text.toString().toInt(),
            editTextInput2.text.toString()
        )
        call?.enqueue(object : Callback<DrugsResponse> {
            override fun onFailure(call: Call<DrugsResponse>, t: Throwable) {
                Toast.makeText(
                    applicationContext,
                    resources.getString(R.string.no_internet_connection),
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onResponse(call: Call<DrugsResponse>, response: Response<DrugsResponse>) {
                statusCode = response.code()
                success = response.body().getSuccess()
                message = response.body().getMessage()
                drugs = response.body().getDrugs()
                Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
            }
        })
    }
}
