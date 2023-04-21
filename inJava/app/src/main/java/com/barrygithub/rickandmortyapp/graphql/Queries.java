package com.barrygithub.rickandmortyapp.graphql;

import java.util.Locale;

/**
 * Project RickAndMortyApp
 * Created by Barry Zea H. on 21/04/23.
 * Copyright (c) Barry Zea H. All rights reserved.
 **/
public class Queries {
    public static String getAllCharactersQuery(int page){
        return String.format(Locale.getDefault()," query {\n" +
                "        characters(page: %d ){\n" +
                "            info{\n" +
                "                count\n" +
                "                pages\n" +
                "            }\n" +
                "            results{\n" +
                "               id\n" +
                "               name\n" +
                "               status\n" +
                "               species\n" +
                "               gender\n" +
                "               type\n" +
                "               image\n" +
                "               episode {\n" +
                "                id\n" +
                "                episode\n" +
                "                name\n" +
                "                air_date\n" +
                "              }\n" +
                "           }\n" +
                "        }\n" +
                "        \n" +
                "     }",page);
    }
}
