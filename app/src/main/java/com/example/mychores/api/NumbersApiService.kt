package com.example.mychores.api

import retrofit2.http.GET
import retrofit2.http.Path

interface NumbersApiService {

    @GET("{finished}/trivia?json")
    suspend fun getNumberOfFinishedChoresTrivia(@Path("finished")finished: Int) : Trivia
}