package com.univation.tdsapplication.objects

class DailyMacronutrientsObject(val protein: String, val carbohydrates: String, val fats: String, val calories: String){
    constructor() :this("", "", "", "")
}