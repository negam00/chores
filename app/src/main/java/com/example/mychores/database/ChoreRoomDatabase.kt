package com.example.mychores.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.mychores.model.Chore

@Database(entities = [Chore::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class ChoreRoomDatabase : RoomDatabase() {
    abstract fun choreDao(): ChoreDao

    companion object {
        private const val DATABASE_NAME = "CHORE_DATABASE"

        @Volatile
        private var choreRoomDatabaseInstance: ChoreRoomDatabase? = null

        fun getDatabase(context: Context): ChoreRoomDatabase? {
            if (choreRoomDatabaseInstance == null) {
                synchronized(ChoreRoomDatabase::class.java) {
                    if (choreRoomDatabaseInstance == null) {
                        choreRoomDatabaseInstance = Room.databaseBuilder(
                            context.applicationContext,
                            ChoreRoomDatabase::class.java,
                            DATABASE_NAME
                        ).build()
                    }
                }
            }
            return choreRoomDatabaseInstance
        }
    }
}