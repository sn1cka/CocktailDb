package com.example.myapplication.utils

import android.content.Context
import com.example.myapplication.models.Cocktail
import com.google.gson.Gson

object CacheHelper {
    private var gson = Gson()
    fun getHistoryCocktails(context: Context): Array<Cocktail> {
        val preferences = context.getSharedPreferences("CocktailHistory", Context.MODE_PRIVATE)
        return gson.fromJson(preferences.getString("CocktailHistory", ""), Array<Cocktail>::class.java) ?: arrayOf()
    }

    fun addToHistory(context: Context, cocktail: Cocktail) {
        var cocktailArray = arrayOf(cocktail) + getHistoryCocktails(context)
        if (cocktailArray.size > 10) cocktailArray = cocktailArray.sliceArray(0..9)
        val editor = context.getSharedPreferences("CocktailHistory", Context.MODE_PRIVATE).edit()
        editor.putString("CocktailHistory", gson.toJson(cocktailArray))
        editor.apply()
    }

    fun removeFromHistory(context: Context, cocktail: Cocktail) {
        var cocktailArray = getHistoryCocktails(context).filter { it.idDrink != cocktail.idDrink }.toTypedArray()
        val editor = context.getSharedPreferences("CocktailHistory", Context.MODE_PRIVATE).edit()
        editor.putString("CocktailHistory", gson.toJson(cocktailArray))
        editor.apply()
    }
}