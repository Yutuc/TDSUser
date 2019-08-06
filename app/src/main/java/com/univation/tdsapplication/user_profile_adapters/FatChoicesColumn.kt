package com.univation.tdsapplication.user_profile_adapters

import com.univation.tdsapplication.R
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.fat_choices_column.view.*

class FatChoicesColumn(val foodChoices: String) : Item<ViewHolder>(){
    override fun getLayout(): Int {
        return R.layout.fat_choices_column
    }

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.food_choices_textview_fat_column.text = foodChoices
    }

}