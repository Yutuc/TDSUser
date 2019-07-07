package com.univation.tdsapplication.workout_adapters

import com.univation.tdsapplication.R
import com.univation.tdsapplication.objects.WorkoutDayObject
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.vertical_recyclerview_workout.view.*

class WorkoutDayRow(val workoutDayObject : WorkoutDayObject): Item<ViewHolder>(){

    override fun bind(viewHolder: ViewHolder, position: Int) {
        val verticalAdapter = GroupAdapter<ViewHolder>()
        verticalAdapter.add(WarmupCard(workoutDayObject.warmupArrayList!!))
        verticalAdapter.add(MainCard(workoutDayObject.key, workoutDayObject.mainArrayList!!))
        verticalAdapter.add(AccessoryCard(workoutDayObject.key, workoutDayObject.accessoryArrayList!!))
        verticalAdapter.add(CoreCard(workoutDayObject.key, workoutDayObject.coreArrayList!!))
        verticalAdapter.add(ConditioningCard(workoutDayObject.key, workoutDayObject.conditioningArrayList!!))
        verticalAdapter.add(DailyMacronutrientsCard(workoutDayObject.key, workoutDayObject.dailyMacronutrientsObject!!))
        viewHolder.itemView.vertical_recyclerview_workout.adapter = verticalAdapter
        //can create functions inside here by using ex) fun hello() {}
    }

    override fun getLayout(): Int {
        return R.layout.vertical_recyclerview_workout
    }
}