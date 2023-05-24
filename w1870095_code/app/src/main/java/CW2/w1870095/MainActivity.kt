//link for the demonstrating video is given below
//https://drive.google.com/file/d/1l6JHo846lmdvpvZxpaC6NQw4aG5DUO7m/view?usp=share_link
package CW2.w1870095

import android.content.Intent
import android.graphics.Movie
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.room.Room
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MainActivity : AppCompatActivity() {
    //creating variables
    private lateinit var saveButton: Button
    private lateinit var searchButton: Button
    private lateinit var searchMealButton: Button
    private lateinit var filterMealButton: Button
    private lateinit var imageLogo: ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inflate the views from XML
        saveButton = findViewById(R.id.button)
        searchButton = findViewById(R.id.button2)
        searchMealButton = findViewById(R.id.button3)
        filterMealButton = findViewById(R.id.button4)
        imageLogo = findViewById(R.id.imageView)

        // OnClick Listeners
        saveButton.setOnClickListener{
            saveMeals()//calling the method based on the user click
        }

        searchButton.setOnClickListener{
            displaySearchPage()
        }
//
        searchMealButton.setOnClickListener {
            displayMealSearchPage()
        }
//
        filterMealButton.setOnClickListener {
            displayFilterMealPage()
        }

        // Loading the stored states of the view widgets
        if (savedInstanceState != null) {
            val logo2 = savedInstanceState.getBoolean("ImageLogo")
            val button1 = savedInstanceState.getBoolean("Btn1IsEnabled")
            val button2 = savedInstanceState.getBoolean("Btn2IsEnabled")
            val button3 = savedInstanceState.getBoolean("Btn3IsEnabled")

            //Assigning values
            imageLogo.isEnabled = logo2
            saveButton.isEnabled = button1
            searchButton.isEnabled = button2
            searchMealButton.isEnabled = button3
        }
    }

    /**
     * Saving the state of the view widgets and
     * its corresponding values when orientation changes.
     * */
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (saveButton.isEnabled) {
            outState.putBoolean("Btn1IsEnabled", true)
        } else {
            outState.putBoolean("Btn1IsEnabled", false)
        }
        if (searchButton.isEnabled) {
            outState.putBoolean("Btn2IsEnabled", true)
        } else {
            outState.putBoolean("Btn2IsEnabled", false)
        }
        if (searchMealButton.isEnabled) {
            outState.putBoolean("Btn3IsEnabled", true)
        } else {
            outState.putBoolean("Btn3IsEnabled", false)
        }
        if (imageLogo.isEnabled) {
            outState.putBoolean("ImageLogo", true)
        } else {
            outState.putBoolean("NoImageLogo", false)
        }
    }


    /**
     * Setting up the meal objects
     * Reading all the meal and inserting them to create the database table
     * */

    private fun saveMeals(){
        // Creating an instance of the database
        val db = Room.databaseBuilder(this, AppDatabase::class.java, "MealDB").fallbackToDestructiveMigration().build()
        val mealDao = db.mealDao()

        val text = "Meals Added to Database"
        val duration = Toast.LENGTH_SHORT
        val toast = Toast.makeText(applicationContext, text, duration)

        runBlocking {
            launch {
                //adding the meal details to a variable
                val meal1 = Meal(
                    "ID:\n 1",
                    "Meal Name:\n Sweet and Sour Pork",
                    "Drink Alternate: \n " + null,
                    "Category: \n Pork",
                    "Area: \n Chinese",
                    "Instructions: \n Preparation\r\n1. Crack the egg into a bowl. Separate the egg white and yolk." +
                            "\r\n\r\nSweet and Sour Pork\r\n2. " +
                            "Slice the pork tenderloin into ips.\r\n\r\n3. " +
                            "Prepare the marinade using a pinch of salt, one teaspoon of starch, two teaspoons of light soy sauce, and an egg white.\r\n\r\n4." +
                            " Marinade the pork ips for about 20 minutes.\r\n\r\n5. " +
                            "Put the remaining starch in a bowl. Add some water and vinegar to make a starchy sauce.\r\n\r\n" +
                            "Sweet and Sour Pork\r\nCooking Inuctions\r\n1. Pour the cooking oil into a wok and heat to 190\u00b0C (375\u00b0F)." +
                            " Add the marinated pork ips and fry them until they turn brown. Remove the cooked pork from the wok and place on a plate.\r\n\r\n2. " +
                            "Leave some oil in the wok. Put the tomato sauce and white sugar into the wok, and heat until the oil and sauce are fully combined.\r\n\r\n3. " +
                            "Add some water to the wok and thoroughly heat the sweet and sour sauce before adding the pork ips to it.\r\n\r\n4. " +
                            "Pour in the starchy sauce. Stir-fry all the ingredients until the pork and sauce are thoroughly mixed together.\r\n\r\n5. " +
                            "Serve on a plate and add some coriander for decoration.",
                    "Tags: \n Sweet",
                    "Youtube: \n https:www.youtube.comwatch?v=mdaBIhgEAMo",
                    "Ingredients: \n Pork  \t , Egg \t , Water \t , Salt \t , Sugar \t , Soy Sauce \t ,  Starch \t ,  Tomato Puree \t ,  Vinegar \t ,  Coriander  \t ",
                    "Measures: \n 200g  \t ,  1  \t ,  Dash  \t ,  1/2 tsp  \t ,  1 tsp  \t ,  10g  \t ,  10g  \t ,  30g  \t ,  10g  \t ,  Dash  \t "
                )

                //adding the meal details to a variable
                val meal2 = Meal(
                    "ID:\n 2",
                    "Meal Name:\n Chicken Marengo",
                    "Drink Alternate: \n " + null,
                    "Category: \n Chicken",
                    "Area: \n French",
                    "Instructions: \n Heat the oil in a large flameproof casserole dish and stir-fry the mushrooms until they start to soften. Add the chicken legs and cook briefly on each side to colour them a little.\r\n" +
                            "Pour in the passata, crumble in the stock cube and stir in the olives. Season with black pepper \u2013 you shouldn\u2019t need salt." +
                            "Cover and simmer for 40 mins until the chicken is tender. Sprinkle with parsley and serve with pasta and a salad, or mash and green veg, if you like.",
                    "Tags: \n " + null,
                    "Youtube: \n https:www.youtube.comwatch?v=U33HYUr-0Fw",
                    "Ingredients: \n Olive Oil \t ,  Mushrooms \t , Passata  \t ,Chicken Stock Cube \t , Black Olives \t , Parsley",
                    "Measures: \n 1 tbs \t , 300g \t , 4 \t , 500g \t , 1 \t ,  100g \t , Chopped"
                )

                //adding the meal details to a variable
                val meal3 = Meal(
                    "ID: \n 3",
                    "Name: \n Beef Banh Mi Bowls with Sriracha Mayo, Carrot & Pickled Cucumber",
                    "Drink alternate: \n " + null,
                    "Category: \n Beef",
                    "Area: \n Vietnamese",
                    "Instructions: \n Add'l ingredients: mayonnaise, siracha\r\n\r\n1\r\n\r\n" +
                            "Place rice in a fine-mesh sieve and rinse until water runs clear. " +
                            "Add to a small pot with 1 cup water (2 cups for 4 servings) and a pinch of salt. Bring to a boil, " +
                            "then cover and reduce heat to low. Cook until rice is tender, 15 minutes. Keep covered off heat for at least 10 minutes or " +
                            "until ready to serve.\r\n\r\n2\r\n\r\n" +
                            "Meanwhile, wash and dry all produce. Peel and finely chop garlic. Zest and quarter lime (for 4 servings, zest 1 lime and quarter both). " +
                            "Trim and halve cucumber lengthwise; thinly slice crosswise into half-moons. Halve, peel, and medium dice onion. " +
                            "Trim, peel, and grate carrot.\r\n\r\n3\r\n\r\nIn a medium bowl, combine cucumber, juice from half the lime, \u00bc tsp sugar (\u00bd tsp for 4 servings), and a pinch of salt. In a small bowl, combine mayonnaise, a pinch of garlic, a squeeze of lime juice, and as much sriracha as you\u2019d like. Season with salt and pepper.\r\n\r\n4\r\n\r\n" +
                            "Heat a drizzle of oil in a large pan over medium-high heat. Add onion and cook, stirring, until softened, 4-5 minutes. Add beef, remaining garlic, and 2 tsp sugar (4 tsp for 4 servings). Cook, breaking up meat into pieces, until beef is browned and cooked through, 4-5 minutes. Stir in soy sauce. Turn off heat; taste and season with salt and pepper.\r\n\r\n5\r\n\r\n" +
                            "Fluff rice with a fork; stir in lime zest and 1 TBSP butter. Divide rice between bowls. Arrange beef, grated carrot, and pickled cucumber on top. Top with a squeeze of lime juice. Drizzle with sriracha mayo.",
                    "Tags: \n " + null,
                    "Youtube: \n ",
                    "Ingredients: \n Rice \t , Onion \t , Lime \t , Garlic Clove \t , Cucumber \t , Carrots \t ,Ground Beef \t , Soy Sauce",
                    "Measures: \n White \t , 1 \t , 1 \t , 3 \t , 1 \t , 3oz  \t , 1lb \t , 2oz"

                )

                //adding the meal details to a variable
                val meal4 = Meal(
                    "ID: \n 4",
                    "Name: \n Leblebi Soup",
                    "Drink Alternate: \n " + null,
                    "Category: \n Vegetarian",
                    "Area: \n Tunisian",
                    "Instructions: \n Heat the oil in a large pot. Add the onion and cook until translucent.\r\n" +
                            "Drain the soaked chickpeas and add them to the pot together with the vegetable stock. Bring to the boil, " +
                            "then reduce the heat and cover. Simmer for 30 minutes.\r\n" +
                            "In the meantime toast the cumin in a small ungreased frying pan, then grind them in a mortar. " +
                            "Add the garlic and salt and pound to a fine paste.\r\n" +
                            "Add the paste and the harissa to the soup and simmer until the chickpeas are tender, about 30 minutes.\r\n" +
                            "Season to taste with salt, pepper and lemon juice and serve hot.",
                    "Tags: \n Soup",
                    "Youtube: \n https:www.youtube.comwatch?v=BgRifcCwinY",
                    "Ingredients: \n Olive Oil \t , Onion \t , Chickpeas \t , Vegetable Stock \t , Cumin \t , Garlic \t ,  Salt\t ,  Harissa Spice \t ,  Pepper \t , Lime",
                    "Measures: \n 2 tbs \t ,  1 medium finely diced \t , 250g \t , 1.5L \t ,  1 tsp \t , 5 cloves \t , 1/2 tsp \t ,  1 tsp \t , Pinch \t , 1/2 "
                )


                // Populating Database by adding the variable that contains the details of the meal
                mealDao.insertMeal(meal1, meal2, meal3, meal4)
                toast.show()

                // Displaying DB table data
                displayDbTablePage()
            }
        }
    }

    // Displays the meal data that saved in the Database
    fun displayDbTablePage() {
        val i = Intent(this, DisplayDBTableActivity::class.java)
        startActivity(i)
    }

    //Displaying meal Search Page
    fun displaySearchPage() {
        val i = Intent(this, SearchActivity::class.java)
        startActivity(i)
    }
    //
    // Displaying meal Search Page
    fun displayMealSearchPage(){
        val i = Intent(this, MealSearchActivity::class.java)
        startActivity(i)
    }
    //
    // Displaying Filter meal Search Page
    fun displayFilterMealPage(){
        val i = Intent(this, FilterMealActivity::class.java)
        startActivity(i)
    }




}