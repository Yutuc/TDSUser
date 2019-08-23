package com.univation.tdsapplication.user_profile_adapters

import android.app.AlertDialog
import android.graphics.Paint
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.univation.tdsapplication.R
import com.univation.tdsapplication.objects.DailyMacronutrientsObject
import com.univation.tdsapplication.user_profile_fragments.DailyMacroFragment
import com.univation.tdsapplication.user_profile_fragments.ViewDailyMacroBlockActivity
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.daily_macro_row.view.*
import kotlinx.android.synthetic.main.workout_input_value_alert_dialog.view.*

class DailyMacronutrientsCard(val dailyMacronutrientsObject: DailyMacronutrientsObject): Item<ViewHolder>(){

    val blockClicked = DailyMacroFragment.blockClicked

    override fun bind(viewHolder: ViewHolder, position: Int) {

        viewHolder.itemView.date_textview_daily_macro_row.text = dailyMacronutrientsObject.date

        viewHolder.itemView.protein_input_text.paintFlags = Paint.UNDERLINE_TEXT_FLAG
        viewHolder.itemView.carbohydrates_input_text.paintFlags = Paint.UNDERLINE_TEXT_FLAG
        viewHolder.itemView.fat_input_text.paintFlags = Paint.UNDERLINE_TEXT_FLAG
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

        if(dailyMacronutrientsObject.fat.isEmpty()){
            viewHolder.itemView.fat_input_text.text = "grams"
        }
        else{
            viewHolder.itemView.fat_input_text.text = dailyMacronutrientsObject.fat
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
            val dialogBuilder = AlertDialog.Builder(ViewDailyMacroBlockActivity.mContext)
            val dialogView = ViewDailyMacroBlockActivity.mInflater!!.inflate(R.layout.workout_input_value_alert_dialog, null)

            dialogBuilder.setView(dialogView)
            dialogView.input_type_title_input_value_alert_dialog.text = "Protein"
            dialogView.input_value_edittext_input_value_alert_dialog.setHint("grams")

            val alertDialog = dialogBuilder.create()
            alertDialog.show()

            dialogView.save_button_input_value_alert_dialog.setOnClickListener {
                val proteinInput = dialogView.input_value_edittext_input_value_alert_dialog.text.toString()
                if(proteinInput.isEmpty()){
                    Toast.makeText(ViewDailyMacroBlockActivity.mContext, "No value detected", Toast.LENGTH_SHORT).show()
                }
                else{
                    val ref = FirebaseDatabase.getInstance().getReference("/daily-macros/${FirebaseAuth.getInstance().uid}/${blockClicked!!.blockName}/${dailyMacronutrientsObject.key}")
                    ref.child("protein").setValue(proteinInput)
                        .addOnSuccessListener {
                            viewHolder.itemView.protein_input_text.text = proteinInput
                        }
                        .addOnFailureListener {
                            Toast.makeText(ViewDailyMacroBlockActivity.mContext, "${it.message}", Toast.LENGTH_SHORT).show()
                        }
                    alertDialog.dismiss()
                }
            }
        }

        viewHolder.itemView.carbohydrates_input_text.setOnClickListener {
            val dialogBuilder = AlertDialog.Builder(ViewDailyMacroBlockActivity.mContext)
            val dialogView = ViewDailyMacroBlockActivity.mInflater!!.inflate(R.layout.workout_input_value_alert_dialog, null)

            dialogBuilder.setView(dialogView)
            dialogView.input_type_title_input_value_alert_dialog.text = "Carbohydrates"
            dialogView.input_value_edittext_input_value_alert_dialog.setHint("grams")

            val alertDialog = dialogBuilder.create()
            alertDialog.show()

            dialogView.save_button_input_value_alert_dialog.setOnClickListener {
                val carbohydratesInput = dialogView.input_value_edittext_input_value_alert_dialog.text.toString()
                if(carbohydratesInput.isEmpty()){
                    Toast.makeText(ViewDailyMacroBlockActivity.mContext, "No value detected", Toast.LENGTH_SHORT).show()
                }
                else{
                    val ref = FirebaseDatabase.getInstance().getReference("/daily-macros/${FirebaseAuth.getInstance().uid}/${blockClicked!!.blockName}/${dailyMacronutrientsObject.key}")
                    ref.child("carbohydrates").setValue(carbohydratesInput)
                        .addOnSuccessListener {
                            viewHolder.itemView.carbohydrates_input_text.text = carbohydratesInput
                        }
                        .addOnFailureListener {
                            Toast.makeText(ViewDailyMacroBlockActivity.mContext, "${it.message}", Toast.LENGTH_SHORT).show()
                        }
                    alertDialog.dismiss()
                }
            }
        }

        viewHolder.itemView.fat_input_text.setOnClickListener {
            val dialogBuilder = AlertDialog.Builder(ViewDailyMacroBlockActivity.mContext)
            val dialogView = ViewDailyMacroBlockActivity.mInflater!!.inflate(R.layout.workout_input_value_alert_dialog, null)

            dialogBuilder.setView(dialogView)
            dialogView.input_type_title_input_value_alert_dialog.text = "fat"
            dialogView.input_value_edittext_input_value_alert_dialog.setHint("grams")

            val alertDialog = dialogBuilder.create()
            alertDialog.show()

            dialogView.save_button_input_value_alert_dialog.setOnClickListener {
                val fatInput = dialogView.input_value_edittext_input_value_alert_dialog.text.toString()
                if(fatInput.isEmpty()){
                    Toast.makeText(ViewDailyMacroBlockActivity.mContext, "No value detected", Toast.LENGTH_SHORT).show()
                }
                else{
                    val ref = FirebaseDatabase.getInstance().getReference("/daily-macros/${FirebaseAuth.getInstance().uid}/${blockClicked!!.blockName}/${dailyMacronutrientsObject.key}")
                    ref.child("fat").setValue(fatInput)
                        .addOnSuccessListener {
                            viewHolder.itemView.fat_input_text.text = fatInput
                        }
                        .addOnFailureListener {
                            Toast.makeText(ViewDailyMacroBlockActivity.mContext, "${it.message}", Toast.LENGTH_SHORT).show()
                        }
                    alertDialog.dismiss()
                }
            }
        }

        viewHolder.itemView.calories_input_text.setOnClickListener {
            val dialogBuilder = AlertDialog.Builder(ViewDailyMacroBlockActivity.mContext)
            val dialogView = ViewDailyMacroBlockActivity.mInflater!!.inflate(R.layout.workout_input_value_alert_dialog, null)

            dialogBuilder.setView(dialogView)
            dialogView.input_type_title_input_value_alert_dialog.text = "Calories"
            dialogView.input_value_edittext_input_value_alert_dialog.setHint("#")

            val alertDialog = dialogBuilder.create()
            alertDialog.show()

            dialogView.save_button_input_value_alert_dialog.setOnClickListener {
                val caloriesInput = dialogView.input_value_edittext_input_value_alert_dialog.text.toString()
                if(caloriesInput.isEmpty()){
                    Toast.makeText(ViewDailyMacroBlockActivity.mContext, "No value detected", Toast.LENGTH_SHORT).show()
                }
                else{
                    val ref = FirebaseDatabase.getInstance().getReference("/daily-macros/${FirebaseAuth.getInstance().uid}/${blockClicked!!.blockName}/${dailyMacronutrientsObject.key}")
                    ref.child("calories").setValue(caloriesInput)
                        .addOnSuccessListener {
                            viewHolder.itemView.calories_input_text.text = caloriesInput
                        }
                        .addOnFailureListener {
                            Toast.makeText(ViewDailyMacroBlockActivity.mContext, "${it.message}", Toast.LENGTH_SHORT).show()
                        }
                    alertDialog.dismiss()
                }
            }
        }

        viewHolder.itemView.weight_input_text.setOnClickListener {
            val dialogBuilder = AlertDialog.Builder(ViewDailyMacroBlockActivity.mContext)
            val dialogView = ViewDailyMacroBlockActivity.mInflater!!.inflate(R.layout.workout_input_value_alert_dialog, null)

            dialogBuilder.setView(dialogView)
            dialogView.input_type_title_input_value_alert_dialog.text = "Weight"
            dialogView.input_value_edittext_input_value_alert_dialog.setHint("lbs")

            val alertDialog = dialogBuilder.create()
            alertDialog.show()

            dialogView.save_button_input_value_alert_dialog.setOnClickListener {
                val weightInput = dialogView.input_value_edittext_input_value_alert_dialog.text.toString()
                if(weightInput.isEmpty()){
                    Toast.makeText(ViewDailyMacroBlockActivity.mContext, "No value detected", Toast.LENGTH_SHORT).show()
                }
                else{
                    val ref = FirebaseDatabase.getInstance().getReference("/daily-macros/${FirebaseAuth.getInstance().uid}/${blockClicked!!.blockName}/${dailyMacronutrientsObject.key}")
                    ref.child("weight").setValue(weightInput)
                        .addOnSuccessListener {
                            viewHolder.itemView.weight_input_text.text = weightInput
                        }
                        .addOnFailureListener {
                            Toast.makeText(ViewDailyMacroBlockActivity.mContext, "${it.message}", Toast.LENGTH_SHORT).show()
                        }
                    alertDialog.dismiss()
                }
            }
        }
    }

    override fun getLayout(): Int {
        return R.layout.daily_macro_row
    }
}