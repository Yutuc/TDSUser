package com.univation.tdsapplication.workout_adapters

import com.univation.tdsapplication.R
import com.univation.tdsapplication.objects.WorkoutExerciseObject
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.workout_page_column.view.*

class WorkoutCard(val key: String, val workoutArrayList: ArrayList<WorkoutExerciseObject>): Item<ViewHolder>() {

    override fun bind(viewHolder: ViewHolder, position: Int) {
        val adapter = GroupAdapter<ViewHolder>()
        adapter.add(WorkoutTitlesRow())
        workoutArrayList.forEach {
            adapter.add(WorkoutExerciseRow(key, it))
        }
        viewHolder.itemView.workout_item_recyclerview.adapter = adapter
    }

    override fun getLayout(): Int {
        return R.layout.workout_page_column
    }
}

