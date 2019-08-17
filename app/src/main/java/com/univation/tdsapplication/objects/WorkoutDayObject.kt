package com.univation.tdsapplication.objects

class WorkoutDayObject (val position: Int, val key: String, val completed: Boolean, var mainArrayList: ArrayList<MainExerciseObject>?, var warmupArrayList: ArrayList<WarmupExerciseObject>?, var accessoryArrayList: ArrayList<AccessoryExerciseObject>?, var coreArrayList: ArrayList<CoreExerciseObject>?, var conditioningArrayList: ArrayList<ConditioningExerciseObject>?) {
    constructor() : this(-1, "", false,null, null, null, null, null)
}