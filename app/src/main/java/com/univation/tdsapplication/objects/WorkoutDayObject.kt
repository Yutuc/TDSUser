package com.univation.tdsapplication.objects

class WorkoutDayObject (val position: Int, val key: String, var mainArrayList: ArrayList<MainExerciseObject>?, var warmupArrayList: ArrayList<WarmupExerciseObject>?, var accessoryArrayList: ArrayList<AccessoryExerciseObject>?, var coreArrayList: ArrayList<CoreExerciseObject>?, var conditioningArrayList: ArrayList<ConditioningExerciseObject>?, var dailyMacronutrientsObject: DailyMacronutrientsObject?) {
    constructor() : this(-1, "",null, null, null, null, null, null)
}