package by.gsu.drugstore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.Toolbar
import by.gsu.drugstore.ui.main.ListFragment
import com.miguelcatalan.materialsearchview.MaterialSearchView



class MainActivity : AppCompatActivity() {

    private lateinit var mToolBar : Toolbar
    private lateinit var mSearchView: MaterialSearchView

    private var listFragmentManager = supportFragmentManager
    private var listFragmentTransaction = listFragmentManager.beginTransaction()
    private var listFragment = ListFragment.newInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        //mToolBar = findViewById(R.id.tool_bar)
        //mSearchView = findViewById(R.id.search_view)

        //setSupportActionBar(findViewById(R.id.tool_bar))

        /*mSearchView.setOnQueryTextListener(object : MaterialSearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                //Do some magic
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                //Do some magic
                return false
            }
        })

        mSearchView.setOnSearchViewListener(object : MaterialSearchView.SearchViewListener {
            override fun onSearchViewShown() {
                //Do some magic
            }

            override fun onSearchViewClosed() {
                //Do some magic
            }
        })*/

        /*mSearchView.setVoiceSearch(true)*/


        /*if (savedInstanceState == null) {
            listFragmentTransaction.replace(R.id.main_activity, listFragment).commitNow()
        }*/
    }

    /*override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_list, menu)

        val mMenuSearchItem = menu!!.findItem(R.id.action_search)
        val mMenuAddItem = menu.findItem(R.id.action_add)

        mSearchView.setMenuItem(mMenuSearchItem)

        return *//*true*//* super.onCreateOptionsMenu(menu)
    }*/

    /*override fun onBackPressed() {
        if (mSearchView.isSearchOpen) {
            mSearchView.closeSearch()
        } else {
            super.onBackPressed()
        }
    }*/

}
