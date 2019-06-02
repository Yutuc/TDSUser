package com.univation.tdsapplication.objects

class ScheduledTimeObject (val position: Int, var key: String, val date: String, val time: String, val uid: String){
    constructor() : this (-1, "", "", "", "")
}