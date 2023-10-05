package com.inbedroom.edottest.feature.list

import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.inbedroom.edottest.R
import com.inbedroom.edottest.databinding.ActivityMainBinding
import com.inbedroom.edottest.shared.ui.LoadingHandler
import com.inbedroom.edottest.shared.ui.LoadingHandlerImpl
import com.inbedroom.edottest.shared.ui.carousel.CarouselAdapter
import com.inbedroom.edottest.shared.ui.carousel.getCarouselDefaultTransformers
import com.inbedroom.edottest.shared.utils.MarginItemDecoration
import eightbitlab.com.blurview.RenderScriptBlur
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), LoadingHandler by LoadingHandlerImpl() {
    private val TAG = MainActivity::class.java.simpleName
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MovieListViewModel by viewModels { MovieListViewModel.Factory }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
        initializeLoadingDialog(this)
        observeUiState()
        viewModel.getMovies()
        viewModel.getSeries()

    }

    private fun initView() {
        binding.viewPagerLatestMovies.apply {
            offscreenPageLimit = 3
            (getChildAt(0) as RecyclerView).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        }

        binding.viewPagerSeries.apply {
            offscreenPageLimit = 3
            (getChildAt(0) as RecyclerView).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        }

        binding.viewPagerTrending.apply {
            layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
            addItemDecoration(MarginItemDecoration(
                horizontalSpacing = (10 * Resources.getSystem().displayMetrics.density).toInt(),
                verticalSpacing = 0
            ))
        }

        binding.viewPagerLatestMovies.setPageTransformer(getCarouselDefaultTransformers())
        binding.viewPagerSeries.setPageTransformer(getCarouselDefaultTransformers())
//        binding.viewPagerTrending.setPageTransformer(getCarouselDefaultTransformers())

        binding.blurView.setupWith(binding.root, RenderScriptBlur(this))
            .setFrameClearDrawable(binding.root.background)
            .setBlurRadius(15f)
    }

    private fun observeUiState() {
        lifecycleScope.launch {
            viewModel.uiState.flowWithLifecycle(lifecycle)
                .collect { state ->
                    when (state) {
                        is ApiLoading -> {
                            stackProgress()
                        }
                        is ApiEnd -> {
                            stackProgress(isAdd = false)
                        }
                    }
                    when (state) {
                        is MovieListUiState.SuccessGetMovies -> {
                            binding.viewPagerLatestMovies.adapter = CarouselAdapter(state.data)
                            binding.viewPagerTrending.adapter = CarouselAdapter(
                                state.data,
                                maxItemWidth = resources.getDimensionPixelSize(
                                    R.dimen.item_width
                                )
                            )
                        }
                        is MovieListUiState.SuccessGetSeries -> {
                            binding.viewPagerSeries.adapter = CarouselAdapter(state.data)
                        }
                        is MovieListUiState.Error -> {
                            Toast.makeText(this@MainActivity, state.message, Toast.LENGTH_SHORT).show()
                        }
                        else -> {}
                    }
                }
        }
    }
}