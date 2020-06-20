package com.example.mychores.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "user_table")
class User(
    @PrimaryKey()
    var userId: String,
    var userName: String,
    var email: String,
    var household: String,
    var finishedChores: List<Chore>
): Parcelable