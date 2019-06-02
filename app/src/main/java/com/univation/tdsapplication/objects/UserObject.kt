package com.univation.tdsapplication.objects

class UserObject(val uid: String, val email: String, val firstName: String, val lastName: String, val scheduledCheckIn: ScheduledTimeObject?) {
    constructor() : this ("","", "", "", null) //empty constructor in Kotlin
}