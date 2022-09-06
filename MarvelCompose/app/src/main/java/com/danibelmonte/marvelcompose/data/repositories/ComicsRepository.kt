package com.danibelmonte.marvelcompose.data.repositories

import com.danibelmonte.marvelcompose.data.entities.Comic
import com.danibelmonte.marvelcompose.data.network.ApiClient
import com.danibelmonte.marvelcompose.data.entities.Result
import com.danibelmonte.marvelcompose.data.entities.tryCall

object ComicsRepository {

    suspend fun get(format: Comic.Format): Result<List<Comic>> = tryCall {
        ApiClient
            .comicsService
            .getComics(0, 20, format.toStringFormat())
            .data
            .results
            .map { it.asComic() }
    }


    suspend fun find(id: Int): Result<Comic> = tryCall {
        ApiClient
            .comicsService
            .findComic(id)
            .data
            .results
            .first()
            .asComic()
    }

}