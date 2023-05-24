package CW2.w1870095

import android.graphics.Movie
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

//finding meals by ingredients

class SearchActivity : AppCompatActivity() {
    //creating variables
    private var searchBox: EditText? = null
    private var searchBtn: Button? = null
    private var resultsBox: TextView? = null
    private var saveResultBtn: Button? = null
    private var copyRightText: TextView? = null
    private var urlString: String = ""
    private var dbData: String = ""

    // Search values for each meal
    private var id: String = ""
    private var name: String = ""
    private var drinkAlternate: String = ""
    private var category: String = ""
    private var area: String = ""
    private var instructions: String = ""
    private var tags: String = ""
    private var youtube: String = ""
    private var ingredients: String = ""
    private var mealIngredientOne: String = ""
    private var mealIngredientTwo: String = ""
    private var mealIngredientThree: String = ""
    private var mealIngredientFour: String = ""
    private var mealIngredientFive: String = ""
    private var mealIngredientSix: String = ""
    private var measures: String = ""
    private var mealMeasureOne: String = ""
    private var mealMeasureTwo: String = ""
    private var mealMeasureThree: String = ""
    private var mealMeasureFour: String = ""
    private var mealMeasureFive: String = ""
    private var mealMeasureSix: String = ""
    private var writtenData: String = ""
    private var finalResult: String = ""

    //creating an array list
    private var mealList: ArrayList<String> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        // Inflate the views from XML
        searchBox = findViewById(R.id.searchBox)
        searchBtn = findViewById(R.id.searchBtn)
        resultsBox = findViewById(R.id.results)
        saveResultBtn = findViewById(R.id.saveBtn)
        copyRightText = findViewById(R.id.copyright)


        // OnClick Listeners
        searchBtn?.setOnClickListener {
            assignMeal()
            getMeal()
        }
//
        saveResultBtn?.setOnClickListener {
            saveMeal()
        }

        // Loading saved preferences values
        if (savedInstanceState != null) {
            val retrieveDetails = savedInstanceState.getBoolean("RetrieveData")
            val saveDetails = savedInstanceState.getBoolean("SaveData")
            val textDetails = savedInstanceState.getString("MealDetails")
            val landSearch = savedInstanceState.getCharSequence("MySavedData")
            val copyRight = savedInstanceState.getString("copyright")

            searchBtn?.isEnabled = retrieveDetails
            saveResultBtn?.isEnabled = saveDetails
            resultsBox?.text = textDetails
            searchBox?.setText(landSearch)
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
            outState.putBoolean("RetrieveData", true)
        } else {
            outState.putBoolean("RetrieveData", false)
        }
        if (saveResultBtn?.isEnabled == true) {
            outState.putBoolean("SaveData", true)
        } else {
            outState.putBoolean("SaveData", false)
        }
        outState.putString("MealDetails", resultsBox?.text.toString())

        writtenData = searchBox!!.text.toString()
        outState.putCharSequence("MySavedData", writtenData)

        outState.putString("copyright", copyRightText?.text.toString())
    }

    /**
     * Fetching the meal details using the web service
     * storing the data into a String buffer object
     * Printing the values after returning the proper data from parseJson function
     * */


    private fun assignMeal(){
        // !! - converts any value to a non-null type and throws an exception if the value is null
        // trim() - removes whitespace from both ends of a string
        //stores the user entered ingredient in the variable
        val meal = searchBox!!.text.toString().lowercase().trim()
        if (meal  == "")
            return
        //creates the URL with the ingredient name
        urlString = "https://www.themealdb.com/api/json/v1/1/filter.php?i=$meal"

        runBlocking {
            withContext(Dispatchers.IO){
                // whole JSON data
                val stb = StringBuilder("")
                val url = URL(urlString)
                val con = url.openConnection() as HttpURLConnection
                val bf: BufferedReader
//
                try {
                    bf = BufferedReader(InputStreamReader(con.inputStream))
                }catch (e: IOException){
                    e.printStackTrace()
                    return@withContext
                }

                var line = bf.readLine()
                while (line != null){
                    stb.append(line)
                    line = bf.readLine()
                }

                // Testing Purpose
                println("******************************")
                println(stb)
                println("******************************")

                // Picking up all the data
                dbData = parsEJSON(stb)
            }
            resultsBox?.setText(dbData)


        }
    }

    private fun parsEJSON(stb: StringBuilder): String {
        val json = JSONObject(stb.toString())
        //takes the whole JSON data of the ingredient related meals and stores it in variable
        val meals = json.getJSONArray("meals")

        for(i in 0 until meals.length()){
            //iterates through the json data
            var names = meals.getJSONObject(i)
            //stores the meal name in the variable
            var name: String = names.getString("strMeal")
            //adds the meal name to the array list
            mealList.add(name)
            println("All the meals with the ingredient chicken has been added to the list")
            println(mealList)
        }
        //testing purpose
        for(i in 0 until mealList.size){
            var idk = mealList.get(i)
            println(idk)
        }

        finalResult = " "
        return finalResult;

    }

    private fun getMeal() {
        //iterates through the array list
        for(i in 0 until mealList.size ){
            var meal = mealList.get(i)
            //creates an URL with the meal name in the end to search the meal name with that ingredient
            urlString = "https://www.themealdb.com/api/json/v1/1/search.php?s=$meal"

            // Start fetching data in the background - Not in main thread
            runBlocking {
                withContext(Dispatchers.IO){
                    // whole JSON data
                    val stb = StringBuilder("")
                    val url = URL(urlString)
                    val con = url.openConnection() as HttpURLConnection
                    val bf: BufferedReader
//
                    try {
                        bf = BufferedReader(InputStreamReader(con.inputStream))
                    }catch (e: IOException){
                        e.printStackTrace()
                        return@withContext
                    }

                    var line = bf.readLine()
                    while (line != null){
                        stb.append(line)
                        line = bf.readLine()
                    }

                    // Testing Purpose
                    println("******************************")
                    println(stb)
                    println("******************************")

                    // Picking up all the data
                    dbData = parseJSON(stb)
                }
                resultsBox?.setText(dbData)
            }
        }
    }

    /**
     * Reading the Json data from the string buffer object and
     * retrieving the necessary information
     * */
    private fun parseJSON(stb: StringBuilder): String {
        // Displaying proper message for empty results

        // Extracting the actual data from the JSON data
        val json = JSONObject(stb.toString())
        val meals = json.getJSONArray("meals")

        //storing the JSON data in variables
        for(i in 0 until meals.length()){
            var names = meals.getJSONObject(i)
            name = names.getString("strMeal")
            id = names.getString("idMeal")
            drinkAlternate = names.getString("strDrinkAlternate")
            category= names.getString("strCategory")
            area = names.getString("strArea")
            instructions = names.getString("strInstructions")
            tags = names.getString("strTags")
            youtube = names.getString("strYoutube")
            mealIngredientOne = names.getString("strIngredient1")
            mealIngredientTwo = names.getString("strIngredient2")
            mealIngredientThree = names.getString("strIngredient3")
            mealIngredientFour = names.getString("strIngredient4")
            mealIngredientFive = names.getString("strIngredient5")
            mealIngredientSix = names.getString("strIngredient6")
            ingredients = mealIngredientOne + " , " + mealIngredientTwo + " , " + mealIngredientThree + " , " + mealIngredientFour + " , " + mealIngredientFive + " , " + mealIngredientSix
            mealMeasureOne = names.getString("strMeasure1")
            mealMeasureTwo = names.getString("strMeasure2")
            mealMeasureThree = names.getString("strMeasure3")
            mealMeasureFour = names.getString("strMeasure4")
            mealMeasureFive = names.getString("strMeasure5")
            mealMeasureSix = names.getString("strMeasure6")
            measures = mealMeasureOne + " , " + mealMeasureTwo + " , " + mealMeasureThree + " , " + mealMeasureFour + " , " + mealMeasureFive + " , " + mealMeasureSix
            println("The meal with the ingredient chicken will be printed now")
            println("Name:" + name)
            println("ID:" + id)
            println("DrinkAlternate:" + drinkAlternate)
            println("Category:" + category)
            println("Area:" + area)
            println("Instructions:" + instructions)
            println("Tags:" + tags)
            println("Youtube:" + youtube)
            println("Ingredients: " + ingredients)
            println("Measures: " + measures)

            //viewing the results
            finalResult = "\nMeal ID: " + '"' + id + '"'+
                    "\nMeal Name: " + '"' + name + '"'+
                    "\nDrink Alternate: " +  drinkAlternate +
                    "\nMeal Category: " + category +
                    "\nMeal Area: " + area +
                    "\nMeal Instructions: " + instructions +
                    "\nMeal Tags: " + tags +
                    "\nYoutube: " + youtube +
                    "\nIngredients: " + ingredients +
                    "\nMeasures: " + measures
        }
        return finalResult
    }

    /**
     * saving the meal object into the database by inserting the values
     * */
    //creating a function to save the results to the database
    private fun saveMeal() {
        // Access the database
        val db = Room.databaseBuilder(this, AppDatabase::class.java, "MealDB").build()
        val mealDao = db.mealDao()

        // Toast message for confirmation
        val text = "Meal Added to Database"
        val duration = Toast.LENGTH_SHORT
        val toast = Toast.makeText(applicationContext, text, duration)

        runBlocking {
            launch{
                // Saving the searched meal results in the DataBase
                val meals: List<Meal> = mealDao.getAll()
                val index = meals.size + 1
                val mov = Meal(id,name,drinkAlternate,category,area,instructions,youtube,tags,ingredients,measures)
                mealDao.insertMeal(mov)
                toast.show()

                // For Testing Purpose
                println("\n|------------------------------------------------------------|\n")
                for(m in meals){
                    println(m)
                    println()
                }
                println("\n|------------------------------------------------------------|\n")
            }
        }
    }


}