package com.example.myapplication.activities.mainActivity.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.models.Cocktail
import com.example.myapplication.utils.CacheHelper
import com.example.myapplication.utils.OnItemActionClickListener

class HistoryFragment : Fragment(), OnItemActionClickListener<Cocktail> {
    private lateinit var root: View
    private var arrayOfCocktails = arrayOf<Cocktail>()
    private lateinit var recyclerView: RecyclerView
    private var adapter = CocktailAdapter(this)


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        root = inflater.inflate(R.layout.fragment_history, container, false)
        initUI(root)
        return root
    }

    private fun initUI(root: View) {
        arrayOfCocktails = CacheHelper.getHistoryCocktails(root.context)
        recyclerView = root.findViewById(R.id.cocktailRecycler)
        recyclerView.adapter = adapter
        refreshInfo()

    }

    fun refreshInfo() {
        adapter.items = CacheHelper.getHistoryCocktails(root.context).toList()
        adapter.notifyDataSetChanged()
    }


    override fun onItemActionClick(item: Cocktail) {
        AlertDialog.Builder(root.context).setMessage(R.string.you_sure).setPositiveButton(R.string.yes) { _, _ ->
            CacheHelper.removeFromHistory(root.context, item)
            refreshInfo()
        }.setNegativeButton(R.string.no) { _, _ -> }.show()
    }
}