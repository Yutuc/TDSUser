package com.univation.tdsapplication.workout_adapters

import com.univation.tdsapplication.R
import com.univation.tdsapplication.objects.WorkoutPageObject
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.workout_vertical_recyclerview.view.*

class VerticalRecyclerViewObjectWorkout(val workoutPageObject: WorkoutPageObject): Item<ViewHolder>(){

    override fun bind(viewHolder: ViewHolder, position: Int) {
        val verticalAdapter = GroupAdapter<ViewHolder>()
        verticalAdapter.add(DateTitleWorkoutRow(workoutPageObject.date))
        verticalAdapter.add(WorkoutCard(workoutPageObject.key, workoutPageObject.workoutArrayList))
        verticalAdapter.add(WarmupCard(workoutPageObject.warmupArrayList))
        verticalAdapter.add(DailyMacronutrientsCard(workoutPageObject.key, workoutPageObject.dailyMacronutrientsObject))
        viewHolder.itemView.vertical_recyclerview_workout.adapter = verticalAdapter
        //can create functions inside here by using ex) fun hello() {}
    }

    override fun getLayout(): Int {
        return R.layout.workout_vertical_recyclerview
    }
}