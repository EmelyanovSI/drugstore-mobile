package by.gsu.drugstore.activity

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.app.ProgressDialog
import android.os.AsyncTask
import by.gsu.drugstore.R
import kotlinx.android.synthetic.main.activity_second.*
import kotlinx.android.synthetic.main.main_activity.tool_bar

class SecondActivity : AppCompatActivity() {

    private lateinit var pDialog: ProgressDialog
    private var name: String? = null
    private var composition: String? = null
    private var country: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        setSupportActionBar(tool_bar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        button.setOnClickListener {
            //AddNewDrug().execute()
            textView.text = editText.text
            textView2.text = editText2.text
            textView3.text = editText3.text
        }

        button2.setOnClickListener {
            editText.setText("")
            editText2.setText("")
            editText3.setText("")
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    @Suppress("DEPRECATION")
    @SuppressLint("StaticFieldLeak")
    private inner class AddNewDrug : AsyncTask<Void, Void, String?>() {

        private var resp: String? = null

        override fun onPreExecute() {
            super.onPreExecute()
            pDialog = ProgressDialog(this@SecondActivity)
            pDialog.setMessage("Please wait...")
            pDialog.setCancelable(false)
            pDialog.show()
        }

        override fun doInBackground(vararg arg0: Void): String? {

            resp = "this DRUG"

            textView.text = editText.text
            textView2.text = editText2.text
            textView3.text = editText3.text

            return resp
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            if (pDialog.isShowing)
                pDialog.dismiss()
        }
    }

}
