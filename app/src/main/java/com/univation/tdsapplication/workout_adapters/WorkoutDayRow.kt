package com.univation.tdsapplication.workout_adapters

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.univation.tdsapplication.R
import com.univation.tdsapplication.bottom_nav_fragments.WorkoutFragment
import com.univation.tdsapplication.objects.WorkoutDayObject
import com.univation.tdsapplication.user_profile_adapters.DailyMacronutrientsCard
import com.univation.tdsapplication.workout_activities.ChooseWeekActivity
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.vertical_recyclerview_workout.*
import kotlinx.android.synthetic.main.vertical_recyclerview_workout.view.*
import kotlinx.android.synthetic.main.vertical_recyclerview_workout.view.completed_workout_switch
import java.lang.Exception

class WorkoutDayRow(val workoutDayObject : WorkoutDayObject): Item<ViewHolder>(){

    val currentUser = FirebaseAuth.getInstance().uid

    override fun bind(viewHolder: ViewHolder, position: Int) {
        val verticalAdapter = GroupAdapter<ViewHolder>()
        try{
            verticalAdapter.add(WarmupCard(workoutDayObject.warmupArrayList!!))
        }
        catch (e: Exception){}

        try{
            verticalAdapter.add(MainCard(workoutDayObject.key, workoutDayObject.completed, workoutDayObject.mainArrayList!!))
        }
        catch (e: Exception){}

        try{
            verticalAdapter.add(AccessoryCard(workoutDayObject.key, workoutDayObject.completed, workoutDayObject.accessoryArrayList!!))
        }
        catch (e: Exception){}

        try{
            verticalAdapter.add(CoreCard(workoutDayObject.key, workoutDayObject.completed, workoutDayObject.coreArrayList!!))
        }
        catch (e: Exception){}

        try{
            verticalAdapter.add(ConditioningCard(workoutDayObject.key, workoutDayObject.conditioningArrayList!!))
        }
        catch (e: Exception){}

        viewHolder.itemView.vertical_recyclerview_workout.adapter = verticalAdapter

        viewHolder.itemView.completed_workout_switch.setOnCheckedChangeListener { buttonView, isChecked ->
            val ref = FirebaseDatabase.getInstance().getReference("/workouts/$currentUser/${WorkoutFragment.blockClicked!!.blockObject.blockName}/${ChooseWeekActivity.weekClicked!!.weekNumber}/${workoutDayObject.key}").child("completed")
            if(isChecked){
                ref.setValue(true)
            }
            else {
                ref.setValue(false)
            }
        }

        if(workoutDayObject.completed){
            viewHolder.itemView.completed_workout_switch.isChecked = true
            viewHolder.itemView.vertical_recyclerview_workout.alpha = 0.5f
        }
        else{
            viewHolder.itemView.completed_workout_switch.isChecked = false
            viewHolder.itemView.vertical_recyclerview_workout.alpha = 1.0f
        }
        //can create functions inside here by using ex) fun hello() {}
    }

    override fun getLayout(): Int {
        return R.layout.vertical_recyclerview_workout
    }
}