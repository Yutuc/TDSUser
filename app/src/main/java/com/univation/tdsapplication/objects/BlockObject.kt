package com.univation.tdsapplication.objects

class BlockObject (val key: String, val blockName: String, var size: Int){
    constructor() : this("", "", -1)
}