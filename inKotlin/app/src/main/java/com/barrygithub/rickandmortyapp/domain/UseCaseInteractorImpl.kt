package com.barrygithub.rickandmortyapp.domain

import com.barrygithub.rickandmortyapp.data.localDatasource.EntityDb
import com.barrygithub.rickandmortyapp.data.remoteDatasource.Repository
import com.barrygithub.rickandmortyapp.data.remoteDatasource.entities.GraphqlEntity
import com.barrygithub.rickandmortyapp.data.remoteDatasource.entities.convertToEntityDb
import com.google.gson.Gson
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
    override fun getDataGraphql(page: Int) {
        repository.getDataFromGraphql(page)
            .subscribeOn(Schedulers.io())
            .subscribe( object: SingleObserver<String> {
                override fun onSubscribe(d: Disposable) {

                }
                override fun onSuccess(graphqlResponse: String) {
                    catchDataFromApi(graphqlResponse)
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
    private fun catchDataFromApi(response: String) {
        val gson=Gson()
        if(!response.isNullOrEmpty()) {
            //mapeamos el objeto obtenido de tipo GraphqlEntity a EntityDb con "toDatabase" que es una extensión
            //de la dataclass GraphqlEntity
            val model = gson.fromJson(response, GraphqlEntity::class.java).convertToEntityDb()

            model.results.forEach { c->
                c.idMetadata=1
            }
            repository.saveMetadataInDb(model.info)
            repository.saveCharactersInDb(model.results)
            observable.onNext(model)
        }
    }
    private fun catchDataFromDb(entity: EntityDb){
       observable.onNext(entity)
    }
    private fun handleErrorDb(throwable:Throwable){
        observable.onNext(EntityDb())
    }
}