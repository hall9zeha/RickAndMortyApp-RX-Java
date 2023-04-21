package com.barrygithub.rickandmortyapp.graphql

/**
 * Project RickAndMortyApp
 * Created by Barry Zea H. on 20/04/23.
 * Copyright (c) Barry Zea H. All rights reserved.
 *
 **/
fun getCharacterQuery(page:Int):String{
    return  """
     query {
        characters(page: $page){
            info{
                count
                pages
            }
            results{
               id
               name
               status
               species
               gender
               type
               image
               episode {
                id
                episode
                name
                air_date
              }
           }
        }
        
     }
    """
}