package com.univation.tdsapplication.workout_adapters

import com.univation.tdsapplication.R
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder

class WorkoutTitlesRow(): Item<ViewHolder>(){
    override fun bind(viewHolder: ViewHolder, position: Int) {

    }

    override fun getLayout(): Int {
        return R.layout.workout_titles_row
    }
}