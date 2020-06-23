package com.example.mychores.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Household(
    var householdId: String,
    var householdName: String,
    var creator: User?,
    var members: List<User?>
) : Parcelable {
    class Household()
}