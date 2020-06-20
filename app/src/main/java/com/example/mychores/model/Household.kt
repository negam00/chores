package com.example.mychores.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "household_table")
class Household(
    @PrimaryKey(autoGenerate = true)
    var householdId: Long? = null,
    var householdName: String,
    var creator: String,
    var members: List<User>
) : Parcelable