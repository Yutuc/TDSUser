package com.univation.tdsapplication.objects

class UserObject(val uid: String, val email: String, val firstName: String, val lastName: String) {
    constructor() : this ("","", "", "") //empty constructor in Kotlin
}