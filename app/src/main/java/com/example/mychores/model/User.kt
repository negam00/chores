package com.example.mychores.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class User(
    var userId: String = "",
    var userName: String = "",
    var email: String = "",
    var household: String = "",
    var finishedChores: List<Chore> = emptyList()
): Parcelable{
    class User()
}
