package com.barrygithub.rickandmortyapp.domain;

import com.barrygithub.rickandmortyapp.data.LocalDatasource.entities.Character;
import com.barrygithub.rickandmortyapp.data.LocalDatasource.entities.EntityDb;
import com.barrygithub.rickandmortyapp.data.RemoteDatasource.entities.GraphqlEntity;
import com.barrygithub.rickandmortyapp.data.Repository;
import com.google.gson.Gson;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.SingleObserver;
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

            repository.getDataFromApiGraphql(page)
                    .subscribeOn(Schedulers.io())
                    //si hay un error al traer los datos de la API, controlamos la excepción con la función handleError
                    .subscribe(new SingleObserver<String>() {
                        @Override
                        public void onSubscribe(@NonNull Disposable d) {
                        }

                        @Override
                        public void onSuccess(@NonNull String graphqlResponse) {

                            catchDataFromApi(graphqlResponse);
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {
                            //Si no hay respuesta de la API por desconexión a internet, llamamos a la base de datos
                            repository.getDataFromDb()
                                    .subscribeOn(Schedulers.io())
                                    .subscribe(UseCaseInteractorImpl.this::catchDataFromDb, UseCaseInteractorImpl.this::handleErrorDb);

                        }
                    });

    }

    private  void catchDataFromApi(String response){
        Gson gson = new Gson();

        GraphqlEntity graphqlModel  = gson.fromJson(response, GraphqlEntity.class);
        EntityDb entityDbModel= GraphqlEntity.convertToEntityDb(graphqlModel);

        //le colocamos un solo id a cada elemento de la lista de personajed manualmente para relacionar ese campo que es una foreignKey relacionada con la tabla metadata
        //y cargar la lista de personajes en modo offline, esto solo es necesario si se quire guardar la misma estructura
        //que la devuelta por la API, no se requiere si solo se guardará una lista de personajes
        for(Character c: entityDbModel.getResults()){
            c.setIdMetadata(1);
        }
        repository.saveMetadataInDb(entityDbModel.getInfo());
        repository.saveCharactersInDb(entityDbModel.getResults());
        observable.onNext(entityDbModel);
    }

    protected void catchDataFromDb(EntityDb entity){
        observable.onNext(entity);
    }
    protected void handleErrorDb(Throwable t){
        observable.onNext(new EntityDb());
    }
}
