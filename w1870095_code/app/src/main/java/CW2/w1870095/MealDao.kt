package CW2.w1870095


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

//creating an interface for all the functions that will take place in the database
@Dao
interface MealDao {
    @Insert
    suspend fun insertAll(vararg meal: Meal)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMeal(vararg meal: Meal)

    @Query("SELECT * FROM meal")
    suspend fun getAll(): List<Meal>

    @Query("SELECT * FROM meal WHERE mealName LIKE '%' || :name || '%' ")
    suspend fun getSpecificMealTitle(name: String): List<Meal>

    @Query("SELECT count(*) FROM meal")

    suspend fun countAll(): Int
}