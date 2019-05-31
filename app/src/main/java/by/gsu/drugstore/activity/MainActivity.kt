@file:Suppress("DEPRECATION")

package by.gsu.drugstore.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.SimpleAdapter
import com.miguelcatalan.materialsearchview.MaterialSearchView
import kotlinx.android.synthetic.main.main_activity.*
import org.json.JSONException
import org.json.JSONObject
import android.widget.Toast
import android.os.AsyncTask
import android.speech.RecognizerIntent
import android.text.TextUtils
import android.view.MenuItem
import by.gsu.drugstore.*
import by.gsu.drugstore.model.Drug
import by.gsu.drugstore.model.Drugs
import by.gsu.drugstore.rest.ApiClient
import by.gsu.drugstore.rest.ApiInterface
import org.json.JSONArray
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception
import java.io.*

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

    private var drugList: ArrayList<HashMap<String, String>> = ArrayList()
    private var drugListSearch: ArrayList<HashMap<String, String>> = ArrayList()
    private val url = "http://192.168.100.2:8080/get_all_drugs.php"
    var sh = HttpHandler()
    var jsonStr: String? = null
    private var pDialog: ProgressDialog? = null
    private lateinit var json: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        setSupportActionBar(tool_bar)

        /*val retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.100.2:8080/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api = retrofit.create(RegisterAPI::class.java)
        val call = api.dafter("ggg", "ggg")

        call.enqueue(object : Callback<Value> {
            override fun onFailure(call: Call<Value>?, t: Throwable?) {
                t?.printStackTrace()
                Toast.makeText(applicationContext, "arrorF", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<Value>?, response: Response<Value>?) {
                val value = response?.body()?.getValue()
                val message = response?.body()?.getMessage()

                if (value.equals("1")) {
                    Toast.makeText(applicationContext, "arrorR", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(applicationContext, "arrorRR", Toast.LENGTH_LONG).show()
                }
            }

        })*/


        /*val retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.100.2:8080/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi::class.java)

        val call = jsonPlaceHolderApi.getPosts()

        call.enqueue(object : Callback<List<Drug>> {
            override fun onFailure(call: Call<List<Drug>>?, t: Throwable?) {

            }

            override fun onResponse(call: Call<List<Drug>>?, response: Response<List<Drug>>?) {
                if (!(response?.isSuccessful!!)) {
                    return
                }

                val posts = response.body()

                var content = ""
                for (post: Drug in posts!!) {
                    content += post.getName() + '\n'
                }

            }

        })*/


        /*val retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.100.2:8080/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi::class.java)

        val call = jsonPlaceHolderApi.getPosts()

        call.enqueue(object : Callback<List<Drug>> {
            override fun onFailure(call: Call<List<Drug>>?, t: Throwable?) {

            }

            override fun onResponse(call: Call<List<Drug>>?, response: Response<List<Drug>>?) {
                if (!(response?.isSuccessful!!)) {
                    return
                }

                val posts = response.body()

                var content = ""
                for (post: Drug in posts!!) {
                    content += post.getName() + '\n'
                }

            }

        })*/



        //GetContacts().execute()
        searchViewCode()


        val API_KEY = "http://192.168.100.2:8080/"
        if (API_KEY.isEmpty())
            Toast.makeText(applicationContext, "API_KEY is empty", Toast.LENGTH_LONG).show()

        val client = ApiClient()
        val apiService = client.getClient()?.create(ApiInterface::class.java)

        val call = apiService?.getAllDrugs(API_KEY)
        call?.enqueue(object : Callback<Drugs> {
            override fun onFailure(call: Call<Drugs>?, t: Throwable?) {
                Toast.makeText(applicationContext, "error", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<Drugs>?, response: Response<Drugs>?) {
                val statusCode = response?.code()
                val drugs = response?.body()?.getDrugs()
                Toast.makeText(applicationContext, "easy win", Toast.LENGTH_LONG).show()
            }

        })

    }

    private fun searchViewCode() {
        // TODO: this
        //search_view.setSuggestions(resources.getStringArray(R.array.query_suggestions))
        search_view.setEllipsize(true)
        search_view.setVoiceSearch(true)

        search_view.setOnQueryTextListener(object : MaterialSearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                fillingListView(drugListSearch)
                Toast.makeText(applicationContext, query, Toast.LENGTH_SHORT).show()
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                drugListSearch = ArrayList()
                val onePurpose = ""
                if (newText.isNotEmpty()) {
                    if (jsonStr != null) {
                        try {
                            val jsonObj = JSONObject(jsonStr)
                            search(jsonObj, newText, onePurpose)
                        } catch (e: JSONException) {
                            catchException(e)
                        }
                    } else {
                        try {
                            val jsonObj = JSONObject(loadJSONFromAsset())
                            search(jsonObj, newText, onePurpose)
                        } catch (e: JSONException) {
                            catchException(e)
                        }
                    }
                    fillingListView(drugListSearch)
                } else fillingListView(drugList)
                return true
            }
        })

        search_view.setOnSearchViewListener(object : MaterialSearchView.SearchViewListener {
            override fun onSearchViewShown() {

            }

            override fun onSearchViewClosed() {
                fillingListView(drugList)
            }
        })
    }

    // TODO: this
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == MaterialSearchView.REQUEST_VOICE && resultCode == Activity.RESULT_OK) {
            val matches = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            if (matches != null && matches.size > 0) {
                val searchWrd = matches[0]
                if (!TextUtils.isEmpty(searchWrd)) {
                    search_view.setQuery(searchWrd, false)
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    // TODO: this
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_list, menu)
        val mMenuSearchItem = menu?.findItem(R.id.action_search)
        search_view.setMenuItem(mMenuSearchItem)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item != null) {
            when (item.itemId) {
                R.id.action_add -> {
                    val intent = Intent(this, SecondActivity::class.java)
                    startActivity(intent)
                    return true
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    @Suppress("DEPRECATION")
    @SuppressLint("StaticFieldLeak")
    private inner class GetContacts : AsyncTask<Void, Void, String?>() {
        private var resp: String? = null

        override fun onPreExecute() {
            super.onPreExecute()
            pDialog = ProgressDialog(this@MainActivity)
            pDialog?.setMessage("Please wait...")
            pDialog?.setCancelable(false)
            pDialog?.show()
        }

        override fun doInBackground(vararg arg0: Void): String? {
            sh = HttpHandler()
            jsonStr = sh.makeServiceCall(url)
            Log.e("Drugstore", "Response from url: $jsonStr")
            if (jsonStr != null) {
                try {
                    val jsonObj = JSONObject(jsonStr)
                    parsing(jsonObj)
                    resp = "that JSON"
                } catch (e: JSONException) {
                    catchException(e)
                }
            } else {
                try {
                    val jsonObj = JSONObject(loadJSONFromAsset())
                    parsing(jsonObj)
                    resp = "this JSON"
                } catch (e: JSONException) {
                    catchException(e)
                }
            }
            return resp
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            if (pDialog!!.isShowing)
                pDialog!!.dismiss()
            fillingListView(drugList)
        }
    }

    override fun onBackPressed() {
        if (search_view.isSearchOpen) {
            search_view.closeSearch()
        } else {
            super.onBackPressed()
        }
    }

    fun loadJSONFromAsset(): String {
        try {
            val iz: InputStream = try {
                this@MainActivity.assets.open("drugstore.json")
            } catch (ex: IOException) {
                this@MainActivity.assets.open("oldDrugstore.json")
            }
            val size = iz.available()
            val buffer = ByteArray(size)
            iz.read(buffer)
            iz.close()
            json = String(buffer)
        } catch (ex: IOException) {
            ex.printStackTrace()
        }
        return json
    }

    fun fillingListView(arrLst: ArrayList<HashMap<String, String>>) {
        val adapter = SimpleAdapter(
            this@MainActivity,
            arrLst,
            R.layout.list_item,
            arrayOf("title", "purpose", "drugstore"),
            intArrayOf(R.id.title, R.id.purpose, R.id.drugstore)
        )
        list_view.adapter = adapter
    }

    private fun addToDrugListSearch(i: Int, name: String, composition: String) {
        val oneDrug = HashMap<String, String>()
        oneDrug["title"] = name
        oneDrug["purpose"] = composition
        when (i) {
            0 -> oneDrug["drugstore"] = "Belarus"
            1 -> oneDrug["drugstore"] = "Turkey"
            2 -> oneDrug["drugstore"] = "USA"
            else -> oneDrug["drugstore"] = "undefined"
        }
        drugListSearch.add(oneDrug)
    }

    @Throws(JSONException::class)
    fun search(jsonObj: JSONObject, newText: String, onePurpose: String) {
        // TODO: delete try catch + parsing
        // TODO: fix search
        try {
            val purposes = arrayListOf<String>()
            val drugsArray = arrayListOf<JSONArray>(
                jsonObj.getJSONArray("drugsbel"),
                jsonObj.getJSONArray("drugsturkey"),
                jsonObj.getJSONArray("drugsusa")
            )
            for (i in 0 until jsonObj.length()) {
                for (j in 0 until drugsArray[i].length()) {
                    val drug = drugsArray[i].getJSONObject(j)
                    val name = drug.getString("name")
                    val composition = drug.getString("composition")
                    if (name.contains(newText, ignoreCase = true)) {
                        var len = 0
                        for (k in 0 until purposes.size)
                            if (composition != purposes[k]) len++
                            else break
                        if (len == purposes.size) purposes.add(composition)
                        addToDrugListSearch(i, name, composition)
                    } else {
                        for (k in 0 until purposes.size) {
                            if (composition.contains(purposes[k], ignoreCase = true)) {
                                addToDrugListSearch(i, name, composition)
                                break
                            }
                        }
                    }
                }
            }
        } catch (e: Exception) {
            // TODO: fix search
            var onePurposeThis = onePurpose
            val drugstores = jsonObj.getJSONArray("drugstores")
            for (i in 0 until drugstores.length()) {
                val country = drugstores.getJSONObject(i)
                val drugstore = country.getString("drugstore")
                val drugs = country.getJSONArray("drugs")
                for (j in 0 until drugs.length()) {
                    val drug = drugs.getJSONObject(j)
                    val title = drug.getString("title")
                    val purpose = drug.getString("purpose")
                    if (title.contains(newText, ignoreCase = true))
                        onePurposeThis = purpose
                    if (onePurposeThis == purpose) {
                        val oneDrug = HashMap<String, String>()
                        oneDrug["title"] = title
                        oneDrug["purpose"] = purpose
                        oneDrug["drugstore"] = drugstore
                        drugListSearch.add(oneDrug)
                    }
                }
            }
        }
    }

    @Throws(JSONException::class)
    fun parsing(jsonObj: JSONObject) {
        try {
            val drugsArray = arrayListOf<JSONArray>(
                jsonObj.getJSONArray("drugsbel"),
                jsonObj.getJSONArray("drugsturkey"),
                jsonObj.getJSONArray("drugsusa")
            )
            for (i in 0 until jsonObj.length()) {
                for (j in 0 until drugsArray[i].length()) {
                    val drug = drugsArray[i].getJSONObject(j)
                    val name = drug.getString("name")
                    val composition = drug.getString("composition")
                    val oneDrug = HashMap<String, String>()
                    oneDrug["title"] = name
                    oneDrug["purpose"] = composition
                    when (i) {
                        0 -> oneDrug["drugstore"] = "Belarus"
                        1 -> oneDrug["drugstore"] = "Turkey"
                        2 -> oneDrug["drugstore"] = "USA"
                        else -> oneDrug["drugstore"] = "undefined"
                    }
                    drugList.add(oneDrug)
                }
            }
        } catch (e: Exception) {
            val drugstores = jsonObj.getJSONArray("drugstores")
            for (i in 0 until drugstores.length()) {
                val country = drugstores.getJSONObject(i)
                val drugstore = country.getString("drugstore")
                val drugs = country.getJSONArray("drugs")
                for (j in 0 until drugs.length()) {
                    val drug = drugs.getJSONObject(j)
                    val title = drug.getString("title")
                    val purpose = drug.getString("purpose")
                    val oneDrug = HashMap<String, String>()
                    oneDrug["title"] = title
                    oneDrug["purpose"] = purpose
                    oneDrug["drugstore"] = drugstore
                    drugList.add(oneDrug)
                }
            }
        }
    }

    fun catchException(e: JSONException) {
        Log.e("Drugstore", "Json parsing error: " + e.message)
        runOnUiThread {
            Toast.makeText(
                applicationContext,
                "Json parsing error: " + e.message,
                Toast.LENGTH_LONG
            )
                .show()
        }
    }

    /*
    Server: db.gomel.ximxim.com
    DB: medication
    user: user
    password: e4GeZVGpbNUKwU8v

    http://192.168.100.2:8080/
    192.168.100.2:8080
    */

    // TODO: разбить все на файлы
}
