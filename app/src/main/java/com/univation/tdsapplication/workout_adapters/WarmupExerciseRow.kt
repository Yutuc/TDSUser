package com.univation.tdsapplication.workout_adapters

import com.univation.tdsapplication.R
import com.univation.tdsapplication.objects.WarmupExerciseObject
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.warmup_exercise_row.view.*

class WarmupExerciseRow(val warmupExerciseObject: WarmupExerciseObject): Item<ViewHolder>(){
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.exercise_name_textview_warmup.text = warmupExerciseObject.exerciseName
        viewHolder.itemView.sets_textview_warmup.text = warmupExerciseObject.sets
        viewHolder.itemView.reps_textview_warmup.text = warmupExerciseObject.reps
    }

    override fun getLayout(): Int {
        return R.layout.warmup_exercise_row
    }
}