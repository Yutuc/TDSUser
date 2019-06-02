package com.univation.tdsapplication.objects

class AdminScheduledTimeObject(val userName: String, val date: String, val time: String){
    constructor() : this("", "", "")
}