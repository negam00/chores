package com.example.mychores.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.mychores.model.Chore


@Dao
interface ChoreDao {

    @Query("SELECT * FROM chore_table")
    fun getAllChores(): LiveData<List<Chore>>

    @Query("UPDATE chore_table SET completedAt = :completedAt, completedBy = :completedBy WHERE choreId = :choreId")
    fun updateChore(choreId: Long?, completedAt: String?, completedBy: String?)

    @Insert
    suspend fun insertChore(chore: Chore)

    @Delete
    suspend fun deleteChore(chore: Chore)

    @Query("SELECT * FROM user_table WHERE userId= :userId")
    fun getUser(userId: String)
}