package com.example.myapplication.activities.mainActivity.history

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.models.Cocktail
import com.example.myapplication.utils.OnItemActionClickListener

class CocktailViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val imageView: ImageView = view.findViewById(R.id.deleteCocktail)
    private val nameView: TextView = view.findViewById(R.id.cocktailName)
    fun bind(listener: OnItemActionClickListener<Cocktail>, cocktail: Cocktail) {
        imageView.setOnClickListener { listener.onItemActionClick(cocktail) }
        nameView.text = cocktail.strDrink
    }
}