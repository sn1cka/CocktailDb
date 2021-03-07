package com.example.myapplication.activities.mainActivity.home

import android.annotation.SuppressLint
import android.graphics.drawable.AnimatedVectorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat.getDrawable
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.utils.MyScrollView
import com.example.myapplication.utils.ScrollViewListener


class HomeFragment : Fragment(), ScrollViewListener {
    private var heartIsFull = false
    private lateinit var homeViewModel: HomeViewModel

    private lateinit var cocktailImage: ImageView
    private lateinit var cocktailName: TextView
    private lateinit var cocktailCategory: TextView
    private lateinit var cocktailType: TextView
    private lateinit var cocktailIngridients: TextView

    private lateinit var heart: ImageView
    private lateinit var emptyHeart: AnimatedVectorDrawable
    private lateinit var fillHeart: AnimatedVectorDrawable

    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var cocktailContainer: LinearLayout
    private lateinit var retryContainer: LinearLayout
    private lateinit var myScrollView: MyScrollView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        initUi(root)
        homeViewModel.refreshRandomCocktail()

        return root
    }

    @SuppressLint("SetTextI18n")
    private fun initUi(root: View) {
        swipeRefreshLayout = root.findViewById(R.id.swipeRefresh)
        cocktailImage = root.findViewById(R.id.deleteCocktail)
        cocktailName = root.findViewById(R.id.cocktailName)
        cocktailCategory = root.findViewById(R.id.cocktailCategory)
        cocktailContainer = root.findViewById(R.id.cocktailContainer)
        retryContainer = root.findViewById(R.id.retryContainer)
        cocktailType = root.findViewById(R.id.cocktailType)
        myScrollView = root.findViewById(R.id.scrollView)
        cocktailIngridients = root.findViewById(R.id.cocktailIngridients)
        heart = root.findViewById(R.id.heart)

        emptyHeart = getDrawable(root.context, R.drawable.avd_heart_empty) as AnimatedVectorDrawable
        fillHeart = getDrawable(root.context, R.drawable.av_heart_fill) as AnimatedVectorDrawable

        myScrollView.setScrollViewListener(this)

        swipeRefreshLayout.setOnRefreshListener {
            homeViewModel.refreshRandomCocktail()
            if (heartIsFull) {
                heartIsFull = false
                animateHeart()
            }
        }
        heart.setOnClickListener {
            heartIsFull = !heartIsFull
            animateHeart()
        }

        homeViewModel.cocktail.observe(viewLifecycleOwner, Observer {
            cocktailName.text = "${it.idDrink}. ${it.strDrink}"
            cocktailCategory.text = "${getString(R.string.cocktail_category)}: ${it.strCategory}"
            cocktailType.text = "${getString(R.string.cocktail_category)}: ${it.strAlcoholic}"
            loadImageInImageView(it.strDrinkThumb!!)
        })
        homeViewModel.isVMRefreshing.observe(viewLifecycleOwner, Observer {
            swipeRefreshLayout.isRefreshing = it
        })
        homeViewModel.successResponse.observe(viewLifecycleOwner, Observer {
            cocktailContainer.isVisible = it
            retryContainer.isVisible = !it
        })
        homeViewModel.ingridients.observe(viewLifecycleOwner, Observer { list ->

            cocktailIngridients.text = getString(R.string.ingridients) + list.joinToString { it }
        })

    }

    private fun animateHeart() {
        val drawable = if (!heartIsFull) emptyHeart else fillHeart
        heart.setImageDrawable(drawable)
        drawable.start()
    }

    private fun loadImageInImageView(url: String) {
        Glide.with(this).load(url).into(cocktailImage)
    }


    override fun onScrollChanged(scrollView: MyScrollView?, x: Int, y: Int, oldx: Int, oldy: Int) {
        val view = scrollView!!.getChildAt(scrollView.childCount - 1)
        val diff = view.bottom - (scrollView.height + scrollView.scrollY)

        if (diff == 0) {
            homeViewModel.getDetailedInfo()
        }
    }


}