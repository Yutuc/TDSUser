package com.univation.tdsapplication.user_profile_adapters

import com.univation.tdsapplication.R
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.vegetable_choices_row.view.*

class VegetableChoicesColumn(val foodChoices: String) : Item<ViewHolder>(){
    override fun getLayout(): Int {
        return R.layout.vegetable_choices_row
    }

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.food_choices_textview_vegetable_column.text = foodChoices
    }

}