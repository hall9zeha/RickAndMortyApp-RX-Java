package com.barrygithub.rickandmortyapp.domain

import com.barrygithub.rickandmortyapp.data.localDatasource.EntityDb
import com.barrygithub.rickandmortyapp.data.remoteDatasource.Repository
import io.reactivex.rxjava3.core.SingleObserver
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject

/**
 * Project RickAndMortyApp
 * Created by Barry Zea H. on 14/04/23.
 * Copyright (c) Barry Zea H. All rights reserved.
 *
 **/
class UseCaseInteractorImpl(private val repository: Repository) : UseCaseInteractor  {

    private var observable:BehaviorSubject<EntityDb> = BehaviorSubject.create()

    override fun getData(page: Int) {
        repository.getDataFromApi(page)
            .subscribeOn(Schedulers.io())
            .subscribe( object: SingleObserver<EntityDb>{
                override fun onSubscribe(d: Disposable) {

                }

                override fun onSuccess(entityResponse: EntityDb) {
                    catchDataFromApi(entityResponse)
                }

                override fun onError(e: Throwable) {
                    repository.getDataFromDb()
                        .subscribeOn(Schedulers.io())
                        .subscribe(
                            this@UseCaseInteractorImpl::catchDataFromDb,
                            this@UseCaseInteractorImpl::handleErrorDb)
                }
            })
    }

    override fun getObservableData()  = observable

    override fun getEpisodeFromApi(idEpisode: Int) = repository.getEpisode(idEpisode)

    private fun catchDataFromApi(entity: EntityDb) {

        //le colocamos un solo id a cada elemento de la lista de personajed manualmente para relacionar ese campo que es una foreignKey relacionada con la tabla metadata
        //y cargar la lista de personajes en modo offline, esto solo es necesario si se quire guardar la misma estructura
        //que la devuelta por la API, no se requiere si solo se guardará una lista de personajes
        entity.results.forEach { c->
            c.idMetadata=1
        }
        repository.saveMetadataInDb(entity.info)
        repository.saveCharactersInDb(entity.results)
        observable.onNext(entity)

    }
    private fun catchDataFromDb(entity: EntityDb){
       observable.onNext(entity)
    }
    private fun handleErrorDb(throwable:Throwable){
        observable.onNext(EntityDb())
    }
}