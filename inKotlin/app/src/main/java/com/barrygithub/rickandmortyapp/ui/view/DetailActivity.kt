package com.barrygithub.rickandmortyapp.ui.view
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.barrygithub.rickandmortyapp.common.Constants
import com.barrygithub.rickandmortyapp.common.loadUrl
import com.barrygithub.rickandmortyapp.data.localDatasource.Character
import com.barrygithub.rickandmortyapp.data.remoteDatasource.entities.Episode
import com.barrygithub.rickandmortyapp.databinding.ActivityDetailBinding
import com.barrygithub.rickandmortyapp.ui.viewModel.ViewModelMain
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {
    private lateinit var bind:ActivityDetailBinding
    private val viewModel:ViewModelMain by viewModels()
    private var character = Character()

    override fun onCreate(savedInstanceState: Bundle?) {
        title = "Detail"
        super.onCreate(savedInstanceState)
        bind = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(bind.root)
        setUpObservers()
        getBundle()
    }


    private fun getBundle() {
        intent?.let{
            character = it.extras!!.getParcelable(Constants.EXTRA_BUNDLE)!!
            setUpDetails(character)
        }
    }
    private fun setUpDetails(c: Character)= with(bind){
        ivDetail.loadUrl(c.image)
        tvName.text = c.name
        tvGender.text = format("Gender", c.gender)
        tvSpecie.text = format("Specie", c.species)
        tvStatus.text = format("Status", c.status)
        tvType.text = format("Type", c.type)
        toolbarMain.title = c.name

        c.episodes?.let { episode->
           episode.forEach {s->
                val idEpisode = s.substring(s.lastIndexOf('/') + 1).toInt()
                viewModel.callEpisode(idEpisode)
            }
        }
    }
    private fun setUpObservers(){
        viewModel.episode.observe(this,::addViewForEpisode)
        viewModel.loadingEpisode.observe(this){
            if(!it)bind.pbEpisodesLoading.visibility= View.GONE
        }
    }
    private fun format(title: String, value: String): String? {
        return String.format("%s: %s", title, value)
    }
    private fun addViewForEpisode(ep: Episode) {
        val tv = TextView(this)
        tv.text = format(ep.episode, ep.name)
        bind.lnEpisodes.addView(tv)

    }
}
