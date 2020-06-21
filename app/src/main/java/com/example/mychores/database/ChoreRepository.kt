package com.example.mychores.database

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.mychores.model.Chore

class ChoreRepository(context: Context) {
    private var choreDao: ChoreDao

    init {
        val database = ChoreRoomDatabase.getDatabase(context)
        choreDao = database!!.choreDao()
    }

    fun getChores(): LiveData<List<Chore>> = choreDao.getAllChores()

    fun updateChore(choreId: Long?, completedAt: String?, completedBy: String?) = choreDao
        .updateChore(choreId, completedAt, completedBy)

    suspend fun insertChore(chore: Chore) = choreDao.insertChore(chore)

    suspend fun deleteChore(chore: Chore) = choreDao.deleteChore(chore)
}
