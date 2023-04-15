package com.barrygithub.rickandmortyapp.ui.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.barrygithub.rickandmortyapp.data.localDatasource.EntityDb
import com.barrygithub.rickandmortyapp.data.remoteDatasource.entities.Episode
import com.barrygithub.rickandmortyapp.domain.UseCaseInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.core.SingleObserver
import io.reactivex.rxjava3.disposables.Disposable
import javax.inject.Inject

/**
 * Project RickAndMortyApp
 * Created by Barry Zea H. on 14/04/23.
 * Copyright (c) Barry Zea H. All rights reserved.
 *
 **/
@HiltViewModel
class ViewModelMain @Inject constructor(private val interactor: UseCaseInteractor):ViewModel() {

    lateinit var disposable:Disposable
    private var _responseApi:MutableLiveData<EntityDb> = MutableLiveData()
    val responseApi:LiveData<EntityDb> get() = _responseApi

    private var _isLoading:MutableLiveData<Boolean> = MutableLiveData(true)
    val isLoading:LiveData<Boolean> get() = _isLoading

    private var _loadingEpisode:MutableLiveData<Boolean> = MutableLiveData(true)
    val loadingEpisode:LiveData<Boolean> get() = _loadingEpisode

    private var _episode:MutableLiveData<Episode> = MutableLiveData()
    val episode:LiveData<Episode> get() = _episode

    private var _error:MutableLiveData<String> = MutableLiveData()
    val error:LiveData<String> get() = _error

    fun getData(page:Int) = interactor.getData(page)

    init {
        setUpObserverData()
    }

    private fun setUpObserverData(){
        var response = interactor.getObservableData()
        response.observeOn(AndroidSchedulers.mainThread())
            .subscribe(getResponse())
    }

    private fun getResponse(): Observer<EntityDb> {
        return object : Observer<EntityDb>{
            override fun onSubscribe(d: Disposable){disposable=d}
            override fun onNext(response: EntityDb) {
                _responseApi.postValue(response)
                _isLoading.postValue(false)
            }

            override fun onError(e: Throwable) {
               _isLoading.postValue(false)
                _responseApi.postValue(EntityDb())
                _error.postValue(e.message)
            }

            override fun onComplete() {
                _isLoading.postValue(false)
            }
        }
    }
    fun callEpisode(idEpisode:Int){
        interactor.getEpisodeFromApi(idEpisode)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object: SingleObserver<Episode>{
                override fun onSubscribe(d: Disposable) {
                    disposable=d
                }

                override fun onSuccess(episode: Episode) {
                    _episode.postValue(episode)
                    _loadingEpisode.postValue(false)
                }

                override fun onError(e: Throwable) {
                    Log.e("TAG", e.message!!)
                }
            })
    }
}