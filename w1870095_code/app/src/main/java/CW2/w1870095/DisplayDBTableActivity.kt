package CW2.w1870095


import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class DisplayDBTableActivity : AppCompatActivity() {
    lateinit var dbTitle: TextView
    lateinit var tableData: TextView
    lateinit var copyRightText: TextView
    private val dbData: ArrayList<String> = ArrayList()
    private var result = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_dbtable)

        dbTitle = findViewById(R.id.tableTitle)
        tableData = findViewById(R.id.tableData)
        copyRightText = findViewById(R.id.copyright)

        // Loading saved preferences
        if (savedInstanceState != null) {
            val tableTitle = savedInstanceState.getString("dbTitle")
            val dbValues = savedInstanceState.getString("tableValues")
            val copyRight = savedInstanceState.getString("copyRight")

            dbTitle.text = tableTitle
            tableData.text = dbValues
            copyRightText.text = copyRight

        }

        // Displaying the movie list in the Database
        displayMealList()
    }

    /**
     * Saving the state of the view widgets and
     * its corresponding values when orientation changes.
     * */
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("dbTitle", dbTitle.text.toString())
        outState.putString("tableValues", tableData.text.toString())
        outState.putString("copyRight", copyRightText.text.toString())
    }

    private fun displayMealList(){
        val db = Room.databaseBuilder(this, AppDatabase::class.java, "MealDB").fallbackToDestructiveMigration().build()
        val mealDao = db.mealDao()

        runBlocking {
            launch {
                val meals: List<Meal> = mealDao.getAll()
                for (m in meals) {
                    dbData.add("\n${m.id}\n ${m.name}\n ${m.drinkAlternate}\n ${m.Category}\n ${m.area}\n ${m.instructions}\n ${m.tags}\n ${m.youtube}\n ${m.ingredient1}\n ${m.measure1}\n")
                }

                for (m in dbData) {
                    result += m + "\n"
                }
                tableData.setText(result)
            }
        }
    }

}