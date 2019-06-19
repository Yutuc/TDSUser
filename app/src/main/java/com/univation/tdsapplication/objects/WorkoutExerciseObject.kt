package com.univation.tdsapplication.objects

class WorkoutExerciseObject(val position: String, val exerciseName: String, val sets: String, val reps: String, val rpe: String, val weight: String, val type: String, val url: String) {
    constructor() : this ("","", "", "", "", "", "", "")
}