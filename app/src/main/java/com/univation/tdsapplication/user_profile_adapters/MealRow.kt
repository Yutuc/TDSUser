package com.univation.tdsapplication.user_profile_adapters

import com.univation.tdsapplication.R
import com.univation.tdsapplication.objects.MealObject
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.meal_row.view.*

class MealRow (val mealObject: MealObject) : Item<ViewHolder>(){
    override fun getLayout(): Int {
        return R.layout.meal_row
    }

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.meal_number_textview_meal_row.text = mealObject.mealName
        viewHolder.itemView.protein_textview_meal_row.text = "${mealObject.protein}g"
        viewHolder.itemView.carbohydrates_textview_meal_row.text = "${mealObject.carbohydrates}g"
        viewHolder.itemView.fat_textview_meal_row.text = "${mealObject.fat}g"
        viewHolder.itemView.vegetables_textview_meal_row.text = "${mealObject.vegetables}g"
    }

}