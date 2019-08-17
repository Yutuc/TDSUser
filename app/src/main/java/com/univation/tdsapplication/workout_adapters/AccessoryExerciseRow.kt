package com.univation.tdsapplication.workout_adapters

import android.app.AlertDialog
import android.graphics.Paint
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.univation.tdsapplication.R
import com.univation.tdsapplication.bottom_nav_fragments.WorkoutFragment
import com.univation.tdsapplication.objects.AccessoryExerciseObject
import com.univation.tdsapplication.workout_activities.ChooseWeekActivity
import com.univation.tdsapplication.workout_activities.ViewWorkoutWeekActivity
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.accessory_exercise_row.view.*
import kotlinx.android.synthetic.main.workout_input_value_alert_dialog.view.*

class AccessoryExerciseRow (val key: String, val completed: Boolean, val accessoryExerciseObject: AccessoryExerciseObject) : Item<ViewHolder>(){
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.exercise_name_textview_accessory.text = accessoryExerciseObject.exerciseName
        viewHolder.itemView.sets_textview_accessory.text = accessoryExerciseObject.sets
        viewHolder.itemView.reps_textview_accessory.text = accessoryExerciseObject.reps
        if(accessoryExerciseObject.weight.isEmpty()){
            viewHolder.itemView.weight_input_accessory.text = "lbs"
        }
        else{
            viewHolder.itemView.weight_input_accessory.text = accessoryExerciseObject.weight
        }
        viewHolder.itemView.weight_input_accessory.paintFlags = Paint.UNDERLINE_TEXT_FLAG

        viewHolder.itemView.weight_input_accessory.setOnClickListener {
            if(!completed){
                val dialogBuilder = AlertDialog.Builder(ViewWorkoutWeekActivity.mContext)
                val dialogView = ViewWorkoutWeekActivity.mInflater?.inflate(R.layout.workout_input_value_alert_dialog, null)!!

                dialogBuilder.setView(dialogView)
                dialogView.input_type_title_input_value_alert_dialog.text = "Weight"
                dialogView.input_value_edittext_input_value_alert_dialog.setHint("lbs")

                val alertDialog = dialogBuilder.create()
                alertDialog.show()

                dialogView.save_button_input_value_alert_dialog.setOnClickListener {
                    val weightInput = dialogView.input_value_edittext_input_value_alert_dialog.text.toString()
                    if(weightInput.isEmpty()) {
                        Toast.makeText(ViewWorkoutWeekActivity.mContext, "No value detected", Toast.LENGTH_SHORT).show()
                    }
                    else{
                        val currentUser = FirebaseAuth.getInstance().uid
                        val ref = FirebaseDatabase.getInstance().getReference("/workouts/${currentUser}/${WorkoutFragment.blockClicked?.blockObject?.blockName}/${ChooseWeekActivity.weekClicked?.weekNumber}/$key/accessoryArrayList/${accessoryExerciseObject.position}")
                        ref.child("weight").setValue(weightInput)
                        viewHolder.itemView.weight_input_accessory.text = weightInput
                        alertDialog.dismiss()
                    }
                }
            }
        }
    }

    override fun getLayout(): Int {
        return R.layout.accessory_exercise_row
    }
}
