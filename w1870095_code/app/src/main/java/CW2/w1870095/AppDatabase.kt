package CW2.w1870095


import androidx.room.Database
import androidx.room.RoomDatabase

//creating the database
@Database(entities = [Meal::class], version = 2, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun mealDao(): MealDao
}
