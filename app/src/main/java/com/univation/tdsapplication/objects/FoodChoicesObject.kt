package com.univation.tdsapplication.objects

class FoodChoicesObject (val protein: String, val carbohydrates: String, val fat: String, val vegetables: String){
    constructor() : this("", "", "", "")
}