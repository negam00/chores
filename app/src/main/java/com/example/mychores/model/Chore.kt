package com.example.mychores.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
@Entity(tableName = "chore_table")
class Chore (
    @PrimaryKey(autoGenerate = true)
    var choreId: Long? = null,
    var choreName: String,
    var choreDescription: String,
    var recurrence: Int = 0,
    var completedAt: Date? = Date(),
    var completedBy: String? = ""
): Parcelable