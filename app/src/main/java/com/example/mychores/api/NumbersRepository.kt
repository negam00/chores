package com.example.mychores.api

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.withTimeout

class NumbersRepository {
    private val numberApi: NumbersApiService = NumbersApi.createApi()
    private val _trivia: MutableLiveData<Trivia> = MutableLiveData()

    // expose non-mutable livedata via getter encapsulation
    val trivia: LiveData<Trivia> get() = _trivia

    suspend fun getNumberOfFinishedChoresTrivia(finished: Int) {
        try {
            val result = withTimeout(5_000) {
                numberApi.getNumberOfFinishedChoresTrivia(finished)
            }
            _trivia.value = result
        } catch (error: Throwable) {
            throw TriviaRefreshError("Trivia could not be refreshed", error)
        }
    }

        class TriviaRefreshError(message: String, cause: Throwable) : Throwable(message, cause)
}