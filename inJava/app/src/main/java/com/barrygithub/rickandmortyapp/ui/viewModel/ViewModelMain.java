package com.barrygithub.rickandmortyapp.ui.viewModel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.barrygithub.rickandmortyapp.data.LocalDatasource.entities.EntityDb;
import com.barrygithub.rickandmortyapp.data.RemoteDatasource.entities.Episode;
import com.barrygithub.rickandmortyapp.domain.UseCaseInteractor;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;

/**
 * Project RickAndMortyApp
 * Created by Barry Zea H. on 3/04/23.
 * Copyright (c) Barry Zea H. All rights reserved.
 **/
@HiltViewModel
public class ViewModelMain extends ViewModel {
    private UseCaseInteractor interactor;
    public Disposable disposable;

    private MutableLiveData<EntityDb> _responseApi = new MutableLiveData<>();
    public LiveData<EntityDb> getResponseApi() {return _responseApi;}
    private MutableLiveData<Boolean> _loading = new MutableLiveData<>(true);
    public LiveData<Boolean> isLoading(){return _loading;}
    private MutableLiveData<Boolean> _loadingEpisode = new MutableLiveData<>(true);
    public LiveData<Boolean> isLoadingEpisode(){return _loadingEpisode;}

    private MutableLiveData<Episode> _episode = new MutableLiveData<>();
    public LiveData<Episode> getEpisode(){return _episode;}
    private MutableLiveData<String> _error= new MutableLiveData<>();
    public LiveData<String> getError(){return _error;}

  public @Inject ViewModelMain(UseCaseInteractor interactor){
      this.interactor= interactor;
      observerData();
  }
  public void getData(int page){
        interactor.getData(page);
  }
    private void observerData(){
        Observable<EntityDb> response= interactor.observerData();
        response.observeOn(AndroidSchedulers.mainThread())
                .subscribe(getResponse());
    }

   private Observer<EntityDb> getResponse(){
        return new Observer<EntityDb>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                disposable = d;
            }

            @Override
            public void onNext(@NonNull EntityDb entitydb) {
                _responseApi.postValue(entitydb);
                _loading.postValue(false);

            }

            @Override
            public void onError(@NonNull Throwable e) {
                _loading.postValue(false);
                _responseApi.postValue(null);
                _error.postValue(e.getMessage());

                Log.e("TAG", "Error de conexión");

            }

            @Override
            public void onComplete() {
                _loading.postValue(false);
            }
        };
    }
    public void callEpisode(int isEpisode){
        interactor.getEpisodeFromApi(isEpisode)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Episode>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable=d;
                    }

                    @Override
                    public void onSuccess(@NonNull Episode episode) {
                        _episode.postValue(episode);
                        _loadingEpisode.postValue(false);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e("TAG", e.getMessage());

                    }
                });
    }

}
