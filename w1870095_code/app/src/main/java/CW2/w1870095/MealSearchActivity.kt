package CW2.w1870095

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

//searching for meal in database

class MealSearchActivity  : AppCompatActivity() {
    //creating the variables
    private var searchBox: EditText? = null
    private var searchBtn: Button? = null
    private var resultsBox: TextView? = null
    private var copyRightText: TextView? = null
    private var mealTitle: String = ""
    private var  writtenData: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meal_search)

        // Inflate the views from XML
        searchBox = findViewById(R.id.searchBox)
        searchBtn = findViewById(R.id.sBtn)
        resultsBox = findViewById(R.id.results)
        copyRightText = findViewById(R.id.copyright)

        // OnClick Listeners
        searchBtn?.setOnClickListener {
            getMeal()
        }

        // Loading saved instance values
        if (savedInstanceState != null) {
            val landBtn = savedInstanceState.getBoolean("SaveBtn")
            val landSearch = savedInstanceState.getCharSequence("MySavedData")
            val landView = savedInstanceState.getString("viewMeals")
            val copyRight = savedInstanceState.getString("copyright")

            searchBtn?.isEnabled = landBtn
            searchBox?.setText(landSearch)
            resultsBox?.text = landView
            copyRightText?.text = copyRight

        }
    }

    /**
     * Saving the state of the view widgets and
     * its corresponding values when orientation changes.
     * */
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (searchBtn?.isEnabled == true) {
            outState.putBoolean("SaveBtn", true)
        } else {
            outState.putBoolean("SaveBtn", false)
        }
        writtenData = searchBox?.text.toString()
        outState.putCharSequence("MySavedData", writtenData)

        outState.putString("viewMeals", resultsBox?.text.toString())
        outState.putString("copyright", copyRightText?.text.toString())
    }

    /**
     * Searching the meal name in the database table values
     * Retrieving the meal's name
     * */
    private fun getMeal() {
        // Access the database
        val db = Room.databaseBuilder(this, AppDatabase::class.java, "MealDB").fallbackToDestructiveMigration().build()
        val mealDao = db.mealDao()

        // Getting the meal name
        val meal = searchBox!!.text.toString().trim()
        if (meal  == "")
            return

        runBlocking {
            launch{
                // Emptying the results box for every search
                mealTitle = ""

                val meals =  mealDao.getSpecificMealTitle(meal)

                mealTitle = "|-------- Meals in Database- -------|\n\n"

                //appending the meal name with the meal title
                for (x in meals){
                    mealTitle += x.name
                    mealTitle += "\n"
                }

                resultsBox?.text = mealTitle

                // FOR TESTING PURPOSE
                println("|______________________________________________|")
                println(meals)
                println("|______________________________________________|")
            }
        }
    }
}