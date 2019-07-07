package com.univation.tdsapplication.objects

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class WarmupExerciseObject(val position: Int, val exerciseName: String, val sets: String, val reps: String, val url: String) : Parcelable {
    constructor() : this (-1, "", "", "", "")
}