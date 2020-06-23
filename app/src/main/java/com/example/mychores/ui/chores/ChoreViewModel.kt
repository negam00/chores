package com.example.mychores.ui.chores

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mychores.api.NumbersRepository
import com.example.mychores.database.ChoreRepository
import com.example.mychores.model.Chore
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ChoreViewModel(application: Application): AndroidViewModel(application) {
    private val choreRepository = ChoreRepository(application.applicationContext)
    private val ioScope = CoroutineScope((Dispatchers.IO))

    val getCurrentUser by lazy { FirebaseAuth.getInstance().currentUser }

    val getUserReference: DatabaseReference by lazy {
        FirebaseDatabase.getInstance().getReference("/Users")
    }

    val getHouseholdReference: DatabaseReference by lazy {
        FirebaseDatabase.getInstance().getReference("/Household")
    }
    val getChores: LiveData<List<Chore>> = choreRepository.getChores()

    fun insertChore(chore: Chore) {
        ioScope.launch {
            withContext(Dispatchers.IO) {
                choreRepository.insertChore((chore))
            }
        }
    }

    fun updateChore(choreId: Long?, completedAt: String?, completedBy: String?) {
        ioScope.launch {
            withContext(Dispatchers.IO) {
                choreRepository.updateChore(choreId, completedAt, completedBy)
            }
        }
    }

    fun deleteChore(chore:Chore) {
        ioScope.launch {
            withContext(Dispatchers.IO) {
                choreRepository.deleteChore((chore))
            }
        }
    }

    private val numbersRepository = NumbersRepository()
    val trivia = numbersRepository.trivia
    private val error: MutableLiveData<String> = MutableLiveData()

    fun getNumberOfFinishedChoresTrivia(finished: Int){
        viewModelScope.launch {
            try {
                numbersRepository.getNumberOfFinishedChoresTrivia(finished)
            } catch (e: NumbersRepository.TriviaRefreshError) {error.value = e.message}
        }
    }
}
