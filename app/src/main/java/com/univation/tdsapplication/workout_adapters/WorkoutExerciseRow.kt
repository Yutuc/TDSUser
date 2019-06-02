package com.univation.tdsapplication.workout_adapters

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.univation.tdsapplication.R
import com.univation.tdsapplication.objects.WorkoutExerciseObject
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.workout_exercise_row.view.*

class WorkoutExerciseRow(val key: String, val workoutExerciseObject: WorkoutExerciseObject): Item<ViewHolder>(){
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.exercise_name_textview_workout.text = workoutExerciseObject.exerciseName
        viewHolder.itemView.sets_textview_workout.text = workoutExerciseObject.sets
        viewHolder.itemView.reps_textview_workout.text = workoutExerciseObject.reps
        viewHolder.itemView.rpe_textview_workout.text = workoutExerciseObject.rpe
        viewHolder.itemView.weight_input_workout.setText(workoutExerciseObject.weight)

        viewHolder.itemView.weight_input_workout.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                val weightInput = viewHolder.itemView.weight_input_workout.text.toString()
                val currentUser = FirebaseAuth.getInstance().uid
                val ref = FirebaseDatabase.getInstance().getReference("/workout-page/$currentUser/$key/workoutExercises/${workoutExerciseObject.position}")
                ref.child("weight").setValue(weightInput)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })
    }

    override fun getLayout(): Int {
        return R.layout.workout_exercise_row
    }
}