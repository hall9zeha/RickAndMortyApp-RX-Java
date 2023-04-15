package com.barrygithub.rickandmortyapp.ui.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.barrygithub.rickandmortyapp.common.Constants
import com.barrygithub.rickandmortyapp.data.localDatasource.Character
import com.barrygithub.rickandmortyapp.databinding.ActivityMainBinding
import com.barrygithub.rickandmortyapp.ui.adapters.ImagesAdapter
import com.barrygithub.rickandmortyapp.ui.viewModel.ViewModelMain
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), (Character) -> Unit {
    private lateinit var bind:ActivityMainBinding
    private val viewModel:ViewModelMain by viewModels()
    private lateinit var adapter: ImagesAdapter
    private var CURRENT_PAGE = 1
    private lateinit  var gridLayoutManager: StaggeredGridLayoutManager
    private var isLoading = false
    private var isLastPage = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bind.root)
        setUpAdapter()
        setUpViewModel()
        setUpObservers()

    }

    private fun setUpViewModel(){
        viewModel.getData(CURRENT_PAGE)
    }

    private fun setUpAdapter(){
        gridLayoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        adapter = ImagesAdapter(this)
        bind.rvMain.setHasFixedSize(true)
        bind.rvMain.layoutManager = gridLayoutManager
        bind.rvMain.adapter = adapter
        bind.rvMain.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                adapter.addLoadingFooter()
                val visibleItemCount = gridLayoutManager.childCount
                val totalItemCount = gridLayoutManager.itemCount
                val firstVisibleItemCount = gridLayoutManager.findFirstVisibleItemPositions(null)
                if (!isLoading && !isLastPage) {
                    if (visibleItemCount + firstVisibleItemCount[0] >= totalItemCount && firstVisibleItemCount[0] >= 0) {
                        CURRENT_PAGE += 1
                        bind.rvMain.post { viewModel.getData(CURRENT_PAGE) }
                        isLoading = true
                    }
                }
            }
        })
    }
    private fun setUpObservers(){
        viewModel.isLoading.observe(this){if(!it) bind.loadingMain.visibility= View.GONE}
        viewModel.responseApi.observe(this){response->
            adapter.removeLoadingFooter()
            response?.let{
                if(response.info.pages == CURRENT_PAGE) isLastPage=true
                adapter.addAll(response.results)
            }
            isLoading=false
        }
        viewModel.error.observe(this){message->
            Toast.makeText(this@MainActivity, message, Toast.LENGTH_SHORT).show()
        }
    }
    override fun invoke(character: Character) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(Constants.EXTRA_BUNDLE, character)
        startActivity(intent)

    }

    override fun onDestroy() {
        if(!viewModel.disposable.isDisposed){
            viewModel.disposable.dispose()
        }
        super.onDestroy()
    }
}