package com.barrygithub.rickandmortyapp.domain;

import android.util.Log;

import com.barrygithub.rickandmortyapp.data.LocalDatasource.entities.Character;
import com.barrygithub.rickandmortyapp.data.LocalDatasource.entities.EntityDb;
import com.barrygithub.rickandmortyapp.data.RemoteDatasource.entities.Episode;
import com.barrygithub.rickandmortyapp.data.Repository;
import com.barrygithub.rickandmortyapp.data.RepositoryImpl;
import com.bumptech.glide.load.HttpException;
import com.google.gson.JsonSyntaxException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.core.SingleSource;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subjects.BehaviorSubject;

/**
 * Project RickAndMortyApp
 * Created by Barry Zea H. on 5/04/23.
 * Copyright (c) Barry Zea H. All rights reserved.
 **/
public class UseCaseInteractorImpl  implements UseCaseInteractor{
    private Repository repository;
    private BehaviorSubject<EntityDb> observable;
    private EntityDb entityDb;
    public UseCaseInteractorImpl(Repository repository){
        this.repository= repository;
        observable= BehaviorSubject.create();
    }

    @Override
    public Observable<EntityDb> observerData() {
        return observable;
    }

    @Override
    public void getData(int page) {

        repository.getDataFromApi(page)
                .subscribeOn(Schedulers.io())
                //si hay un error al traer los datos de la API, controlamos la excepción con la función handleError
                .subscribe(new SingleObserver<EntityDb>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }

                    @Override
                    public void onSuccess(@NonNull EntityDb entityDb) {
                        catchDataFromApi(entityDb);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        //Si no hay respuesta de la API por desconexión a internet, llamamos a la base de datos
                        repository.getDataFromDb()
                                .subscribeOn(Schedulers.io())
                                .subscribe(UseCaseInteractorImpl.this::catchDataFromDb,UseCaseInteractorImpl.this::handleErrorDb);

                    }
                });
    }

    @Override
    public Single<Episode> getEpisodeFromApi(int idEpisode) {
        return repository.getEpisode(idEpisode).subscribeOn(Schedulers.io());
    }

    private  void catchDataFromApi(EntityDb entity){
        entityDb=entity;
        //le colocamos un solo id a cada elemento de la lista de personajed manualmente para relacionar ese campo que es una foreignKey relacionada con la tabla metadata
        //y cargar la lista de personajes en modo offline, esto solo es necesario si se quire guardar la misma estructura
        //que la devuelta por la API, no se requiere si solo se guardará una lista de personajes
        for(Character c: entity.getResults()){
            c.setIdMetadata(1);
        }
        repository.saveMetadataInDb(entity.getInfo());
        repository.saveCharactersInDb(entity.getResults());

        observable.onNext(entityDb);
    }

    protected void catchDataFromDb(EntityDb entity){
        observable.onNext(entityDb);
    }
    protected void handleErrorDb(Throwable t){
        observable.onNext(new EntityDb());
    }
}
