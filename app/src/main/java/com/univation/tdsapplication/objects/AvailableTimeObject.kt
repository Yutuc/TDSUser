package com.univation.tdsapplication.objects

class AvailableTimeObject (val position: Int, val key: String, val time: String, val uid: String){
    constructor() : this(-1,"", "", "")
}