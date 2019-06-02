package com.univation.tdsapplication.workout_adapters

import android.text.Editable
import android.text.TextWatcher
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.univation.tdsapplication.R
import com.univation.tdsapplication.objects.DailyMacronutrientsObject
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.workout_daily_macro_row.view.*

class DailyMacronutrientsCard(val key: String, val dailyMacronutrientsObject: DailyMacronutrientsObject): Item<ViewHolder>(){
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.protein_input_edittext.setText(dailyMacronutrientsObject.protein)
        viewHolder.itemView.carbohydrates_input_edittext.setText(dailyMacronutrientsObject.carbohydrates)
        viewHolder.itemView.fats_input_edittext.setText(dailyMacronutrientsObject.fats)
        viewHolder.itemView.calories_input_edittext.setText(dailyMacronutrientsObject.calories)

        viewHolder.itemView.protein_input_edittext.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val proteinInput = viewHolder.itemView.protein_input_edittext.text.toString()
                val currentUser = FirebaseAuth.getInstance().uid
                val ref = FirebaseDatabase.getInstance().getReference("/workout-page/$currentUser/$key/dailyMacronutrientsObject")
                ref.child("protein").setValue(proteinInput)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })

        viewHolder.itemView.carbohydrates_input_edittext.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val carbohydrateInput = viewHolder.itemView.carbohydrates_input_edittext.text.toString()
                val currentUser = FirebaseAuth.getInstance().uid
                val ref = FirebaseDatabase.getInstance().getReference("/workout-page/$currentUser/$key/dailyMacronutrientsObject")
                ref.child("carbohydrates").setValue(carbohydrateInput)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })

        viewHolder.itemView.fats_input_edittext.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val fatInput = viewHolder.itemView.fats_input_edittext.text.toString()
                val currentUser = FirebaseAuth.getInstance().uid
                val ref = FirebaseDatabase.getInstance().getReference("/workout-page/$currentUser/$key/dailyMacronutrientsObject")
                ref.child("fats").setValue(fatInput)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })

        viewHolder.itemView.calories_input_edittext.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val calorieInput = viewHolder.itemView.calories_input_edittext.text.toString()
                val currentUser = FirebaseAuth.getInstance().uid
                val ref = FirebaseDatabase.getInstance().getReference("/workout-page/$currentUser/$key/dailyMacronutrientsObject")
                ref.child("calories").setValue(calorieInput)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })
    }

    override fun getLayout(): Int {
        return R.layout.workout_daily_macro_row
    }
}