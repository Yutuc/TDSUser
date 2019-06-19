package com.univation.tdsapplication.objects

class WarmupExerciseObject(val exerciseName: String, val sets: String, val reps: String, val rpe: String, val type: String, val url: String) {
    constructor() : this ("", "", "", "", "", "")
}