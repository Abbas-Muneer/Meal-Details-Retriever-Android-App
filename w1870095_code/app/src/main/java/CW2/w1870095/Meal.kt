package CW2.w1870095

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

//creating an data class to define all the column names of the table in the database
@Entity
data class Meal(
    @PrimaryKey
    @ColumnInfo(name = "mealID")
    val id: String,

    @ColumnInfo(name = "mealName")
    val name: String,

    @ColumnInfo(name = "drinkAlternate")
    val drinkAlternate: String?,

    @ColumnInfo(name = "Category")
    val Category: String?,

    @ColumnInfo(name = "Area")
    val area: String?,

    @ColumnInfo(name = "Instructions")
    val instructions: String?,

    @ColumnInfo(name = "Tags")
    val tags: String?,

    @ColumnInfo(name = "Youtube")
    val youtube: String?,

    @ColumnInfo(name = "Ingredient1")
    val ingredient1: String?,

    @ColumnInfo(name = "Measure1")
    val measure1: String?,

)
