package com.univation.tdsapplication.workout_adapters

import android.app.AlertDialog
import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.univation.tdsapplication.MainActivity
import com.univation.tdsapplication.R
import com.univation.tdsapplication.fragments.WorkoutFragment
import com.univation.tdsapplication.objects.DailyMacronutrientsObject
import com.univation.tdsapplication.workout_activities.ChooseWeekActivity
import com.univation.tdsapplication.workout_activities.ViewWorkoutWeekActivity
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.workout_daily_macro_row.view.*
import kotlinx.android.synthetic.main.workout_input_value_alert_dialog.view.*

class DailyMacronutrientsCard(val key: String, val dailyMacronutrientsObject: DailyMacronutrientsObject): Item<ViewHolder>(){
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.protein_input_text.paintFlags = Paint.UNDERLINE_TEXT_FLAG
        viewHolder.itemView.carbohydrates_input_text.paintFlags = Paint.UNDERLINE_TEXT_FLAG
        viewHolder.itemView.fats_input_text.paintFlags = Paint.UNDERLINE_TEXT_FLAG
        viewHolder.itemView.calories_input_text.paintFlags = Paint.UNDERLINE_TEXT_FLAG
        viewHolder.itemView.weight_input_text.paintFlags = Paint.UNDERLINE_TEXT_FLAG

        if(dailyMacronutrientsObject.protein.isEmpty()){
            viewHolder.itemView.protein_input_text.text = "grams"
        }
        else{
            viewHolder.itemView.protein_input_text.text = dailyMacronutrientsObject.protein
        }

        if(dailyMacronutrientsObject.carbohydrates.isEmpty()){
            viewHolder.itemView.carbohydrates_input_text.text = "grams"
        }
        else{
            viewHolder.itemView.carbohydrates_input_text.text = dailyMacronutrientsObject.carbohydrates
        }

        if(dailyMacronutrientsObject.fats.isEmpty()){
            viewHolder.itemView.fats_input_text.text = "grams"
        }
        else{
            viewHolder.itemView.fats_input_text.text = dailyMacronutrientsObject.fats
        }

        if(dailyMacronutrientsObject.calories.isEmpty()){
            viewHolder.itemView.calories_input_text.text = "#"
        }
        else{
            viewHolder.itemView.calories_input_text.text = dailyMacronutrientsObject.calories
        }

        if(dailyMacronutrientsObject.weight.isEmpty()){
            viewHolder.itemView.weight_input_text.text = "#"
        }
        else{
            viewHolder.itemView.weight_input_text.text = dailyMacronutrientsObject.weight
        }

        viewHolder.itemView.protein_input_text.setOnClickListener {
            val dialogBuilder = AlertDialog.Builder(ViewWorkoutWeekActivity.mContext)
            val dialogView = ViewWorkoutWeekActivity.mInflater!!.inflate(R.layout.workout_input_value_alert_dialog, null)

            dialogBuilder.setView(dialogView)
            dialogView.input_type_title_input_value_alert_dialog.text = "Protein"
            dialogView.input_value_edittext_input_value_alert_dialog.setHint("grams")

            val alertDialog = dialogBuilder.create()
            alertDialog.show()

            dialogView.save_button_input_value_alert_dialog.setOnClickListener {
                val proteinInput = dialogView.input_value_edittext_input_value_alert_dialog.text.toString()
                if(proteinInput.isEmpty()){
                    Toast.makeText(ViewWorkoutWeekActivity.mContext, "No value detected", Toast.LENGTH_SHORT).show()
                }
                else{
                    val currentUser = FirebaseAuth.getInstance().uid
                    val ref = FirebaseDatabase.getInstance().getReference("/workouts/$currentUser/${WorkoutFragment.blockClicked?.blockObject?.blockName}/${ChooseWeekActivity.weekClicked?.weekNumber}/$key/dailyMacronutrientsObject")
                    ref.child("protein").setValue(proteinInput)
                        .addOnSuccessListener {
                            viewHolder.itemView.protein_input_text.text = proteinInput
                        }
                        .addOnFailureListener {
                            Toast.makeText(ViewWorkoutWeekActivity.mContext, "${it.message}", Toast.LENGTH_SHORT).show()
                        }
                    alertDialog.dismiss()
                }
            }
        }

        viewHolder.itemView.carbohydrates_input_text.setOnClickListener {
            val dialogBuilder = AlertDialog.Builder(ViewWorkoutWeekActivity.mContext)
            val dialogView = ViewWorkoutWeekActivity.mInflater!!.inflate(R.layout.workout_input_value_alert_dialog, null)

            dialogBuilder.setView(dialogView)
            dialogView.input_type_title_input_value_alert_dialog.text = "Carbohydrates"
            dialogView.input_value_edittext_input_value_alert_dialog.setHint("grams")

            val alertDialog = dialogBuilder.create()
            alertDialog.show()

            dialogView.save_button_input_value_alert_dialog.setOnClickListener {
                val carbohydratesInput = dialogView.input_value_edittext_input_value_alert_dialog.text.toString()
                if(carbohydratesInput.isEmpty()){
                    Toast.makeText(ViewWorkoutWeekActivity.mContext, "No value detected", Toast.LENGTH_SHORT).show()
                }
                else{
                    val currentUser = FirebaseAuth.getInstance().uid
                    val ref = FirebaseDatabase.getInstance().getReference("/workouts/$currentUser/${WorkoutFragment.blockClicked?.blockObject?.blockName}/${ChooseWeekActivity.weekClicked?.weekNumber}/$key/dailyMacronutrientsObject")
                    ref.child("carbohydrates").setValue(carbohydratesInput)
                        .addOnSuccessListener {
                            viewHolder.itemView.carbohydrates_input_text.text = carbohydratesInput
                        }
                        .addOnFailureListener {
                            Toast.makeText(ViewWorkoutWeekActivity.mContext, "${it.message}", Toast.LENGTH_SHORT).show()
                        }
                    alertDialog.dismiss()
                }
            }
        }

        viewHolder.itemView.fats_input_text.setOnClickListener {
            val dialogBuilder = AlertDialog.Builder(ViewWorkoutWeekActivity.mContext)
            val dialogView = ViewWorkoutWeekActivity.mInflater!!.inflate(R.layout.workout_input_value_alert_dialog, null)

            dialogBuilder.setView(dialogView)
            dialogView.input_type_title_input_value_alert_dialog.text = "Fats"
            dialogView.input_value_edittext_input_value_alert_dialog.setHint("grams")

            val alertDialog = dialogBuilder.create()
            alertDialog.show()

            dialogView.save_button_input_value_alert_dialog.setOnClickListener {
                val fatsInput = dialogView.input_value_edittext_input_value_alert_dialog.text.toString()
                if(fatsInput.isEmpty()){
                    Toast.makeText(ViewWorkoutWeekActivity.mContext, "No value detected", Toast.LENGTH_SHORT).show()
                }
                else{
                    val currentUser = FirebaseAuth.getInstance().uid
                    val ref = FirebaseDatabase.getInstance().getReference("/workouts/$currentUser/${WorkoutFragment.blockClicked?.blockObject?.blockName}/${ChooseWeekActivity.weekClicked?.weekNumber}/$key/dailyMacronutrientsObject")
                    ref.child("fats").setValue(fatsInput)
                        .addOnSuccessListener {
                            viewHolder.itemView.fats_input_text.text = fatsInput
                        }
                        .addOnFailureListener {
                            Toast.makeText(ViewWorkoutWeekActivity.mContext, "${it.message}", Toast.LENGTH_SHORT).show()
                        }
                    alertDialog.dismiss()
                }
            }
        }

        viewHolder.itemView.calories_input_text.setOnClickListener {
            val dialogBuilder = AlertDialog.Builder(ViewWorkoutWeekActivity.mContext)
            val dialogView = ViewWorkoutWeekActivity.mInflater!!.inflate(R.layout.workout_input_value_alert_dialog, null)

            dialogBuilder.setView(dialogView)
            dialogView.input_type_title_input_value_alert_dialog.text = "Calories"
            dialogView.input_value_edittext_input_value_alert_dialog.setHint("#")

            val alertDialog = dialogBuilder.create()
            alertDialog.show()

            dialogView.save_button_input_value_alert_dialog.setOnClickListener {
                val caloriesInput = dialogView.input_value_edittext_input_value_alert_dialog.text.toString()
                if(caloriesInput.isEmpty()){
                    Toast.makeText(ViewWorkoutWeekActivity.mContext, "No value detected", Toast.LENGTH_SHORT).show()
                }
                else{
                    val currentUser = FirebaseAuth.getInstance().uid
                    val ref = FirebaseDatabase.getInstance().getReference("/workouts/$currentUser/${WorkoutFragment.blockClicked?.blockObject?.blockName}/${ChooseWeekActivity.weekClicked?.weekNumber}/$key/dailyMacronutrientsObject")
                    ref.child("calories").setValue(caloriesInput)
                        .addOnSuccessListener {
                            viewHolder.itemView.calories_input_text.text = caloriesInput
                        }
                        .addOnFailureListener {
                            Toast.makeText(ViewWorkoutWeekActivity.mContext, "${it.message}", Toast.LENGTH_SHORT).show()
                        }
                    alertDialog.dismiss()
                }
            }
        }

        viewHolder.itemView.weight_input_text.setOnClickListener {
            val dialogBuilder = AlertDialog.Builder(ViewWorkoutWeekActivity.mContext)
            val dialogView = ViewWorkoutWeekActivity.mInflater!!.inflate(R.layout.workout_input_value_alert_dialog, null)

            dialogBuilder.setView(dialogView)
            dialogView.input_type_title_input_value_alert_dialog.text = "Weight"
            dialogView.input_value_edittext_input_value_alert_dialog.setHint("lbs")

            val alertDialog = dialogBuilder.create()
            alertDialog.show()

            dialogView.save_button_input_value_alert_dialog.setOnClickListener {
                val weightInput = dialogView.input_value_edittext_input_value_alert_dialog.text.toString()
                if(weightInput.isEmpty()){
                    Toast.makeText(ViewWorkoutWeekActivity.mContext, "No value detected", Toast.LENGTH_SHORT).show()
                }
                else{
                    val currentUser = FirebaseAuth.getInstance().uid
                    val ref = FirebaseDatabase.getInstance().getReference("/workouts/$currentUser/${WorkoutFragment.blockClicked?.blockObject?.blockName}/${ChooseWeekActivity.weekClicked?.weekNumber}/$key/dailyMacronutrientsObject")
                    ref.child("weight").setValue(weightInput)
                        .addOnSuccessListener {
                            viewHolder.itemView.weight_input_text.text = weightInput
                        }
                        .addOnFailureListener {
                            Toast.makeText(ViewWorkoutWeekActivity.mContext, "${it.message}", Toast.LENGTH_SHORT).show()
                        }
                    alertDialog.dismiss()
                }
            }
        }
    }

    override fun getLayout(): Int {
        return R.layout.workout_daily_macro_row
    }
}