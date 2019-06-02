package com.univation.tdsapplication.objects

class WarmupExerciseObject(val exerciseName: String, val sets: String, val reps: String, val rpe: String) {
    constructor() : this ("", "", "", "")
}