package by.gsu.drugstore.activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import com.miguelcatalan.materialsearchview.MaterialSearchView
import kotlinx.android.synthetic.main.main_activity.*
import android.widget.Toast
import android.speech.RecognizerIntent
import android.text.TextUtils
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import by.gsu.drugstore.*
import by.gsu.drugstore.adapter.DrugsAdapter
import by.gsu.drugstore.model.Drug
import by.gsu.drugstore.model.DrugsResponse
import by.gsu.drugstore.rest.ApiClient
import by.gsu.drugstore.rest.API
import kotlinx.android.synthetic.main.progress_bar.*
import kotlinx.android.synthetic.main.tool_bar.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private var drugs: List<Drug> = emptyList()
    private var message: String = ""
    private var statusCode: Int = 0
    private var success: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        setSupportActionBar(tool_bar)
        searchViewCode()
        recycler_view.layoutManager = LinearLayoutManager(this@MainActivity)
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onResume() {
        fillingData("all")
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onBackPressed() {
        if (search_view.isSearchOpen) {
            search_view.closeSearch()
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main_activity, menu)
        val mMenuSearchItem = menu?.findItem(R.id.action_search)
        search_view.setMenuItem(mMenuSearchItem)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_add -> {
                val intent = Intent(this, SecondActivity::class.java)
                startActivity(intent)
            }
            R.id.action_all_list -> {
                fillingData("all")
            }
            R.id.action_belarus_list -> {
                fillingData("drugsbel")
            }
            R.id.action_turkey_list -> {
                fillingData("drugsturkey")
            }
            R.id.action_usa_list -> {
                fillingData("drugsusa")
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun fillingData(tableName: String) {
        recycler_view.adapter = null
        progress_bar.visibility = View.VISIBLE
        val apiService = ApiClient().getClient()?.create(API::class.java)
        val call = apiService?.getDrugs(tableName)
        call?.enqueue(object : Callback<DrugsResponse> {
            override fun onFailure(call: Call<DrugsResponse>, t: Throwable) {
                progress_bar.visibility = View.INVISIBLE
                Toast.makeText(
                    applicationContext, resources.getString(R.string.no_internet_connection), Toast.LENGTH_SHORT
                ).show()
            }

            override fun onResponse(call: Call<DrugsResponse>, response: Response<DrugsResponse>) {
                statusCode = response.code()
                success = response.body().getSuccess()
                message = response.body().getMessage()
                drugs = response.body().getDrugs()
                progress_bar.visibility = View.INVISIBLE
                recycler_view.adapter = DrugsAdapter(drugs, R.layout.list_item, applicationContext)
                Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun searchViewCode() {
        search_view.setSuggestions(resources.getStringArray(R.array.query_suggestions))
        search_view.setEllipsize(true)
        search_view.setVoiceSearch(true)

        search_view.setOnQueryTextListener(object : MaterialSearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                recycler_view.adapter = null
                progress_bar.visibility = View.VISIBLE
                val apiService = ApiClient().getClient()?.create(API::class.java)
                val call = apiService?.searchDrugs(query)
                call?.enqueue(object : Callback<DrugsResponse> {
                    override fun onFailure(call: Call<DrugsResponse>, t: Throwable) {
                        progress_bar.visibility = View.INVISIBLE
                        Toast.makeText(
                            applicationContext, resources.getString(R.string.no_internet_connection), Toast.LENGTH_SHORT
                        ).show()
                    }

                    override fun onResponse(call: Call<DrugsResponse>, response: Response<DrugsResponse>) {
                        statusCode = response.code()
                        success = response.body().getSuccess()
                        message = response.body().getMessage()
                        drugs = response.body().getDrugs()
                        progress_bar.visibility = View.INVISIBLE
                        recycler_view.adapter = DrugsAdapter(drugs, R.layout.list_item, applicationContext)
                        Toast.makeText(applicationContext, "$query $message", Toast.LENGTH_SHORT).show()
                    }
                })
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return true
            }
        })

        search_view.setOnSearchViewListener(object : MaterialSearchView.SearchViewListener {
            override fun onSearchViewShown() {
            }

            override fun onSearchViewClosed() {
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == MaterialSearchView.REQUEST_VOICE && resultCode == Activity.RESULT_OK) {
            val matches = data!!.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            if (matches != null && matches.size > 0) {
                val searchWrd = matches[0]
                if (!TextUtils.isEmpty(searchWrd)) {
                    search_view.setQuery(searchWrd, false)
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}
