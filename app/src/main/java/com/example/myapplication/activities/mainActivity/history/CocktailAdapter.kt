package com.example.myapplication.activities.mainActivity.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.models.Cocktail
import com.example.myapplication.utils.OnItemActionClickListener

class CocktailAdapter(var listener: OnItemActionClickListener<Cocktail>) : RecyclerView.Adapter<CocktailViewHolder>() {
    lateinit var items: List<Cocktail>
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CocktailViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cocktail_history_item, parent, false)
        return CocktailViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: CocktailViewHolder, position: Int) {
        holder.bind(listener, items[position])
    }

}

