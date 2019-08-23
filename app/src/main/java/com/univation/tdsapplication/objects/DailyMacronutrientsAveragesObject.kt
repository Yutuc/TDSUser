package com.univation.tdsapplication.objects

class DailyMacronutrientsAveragesObject (val protein: String, val carbohydrates: String, val fat: String, val calories: String, val weight: String) {
    constructor() : this("", "", "", "", "")
}