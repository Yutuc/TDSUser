package com.univation.tdsapplication.user_profile_adapters

import com.univation.tdsapplication.R
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.food_choices_card.view.*

class FoodChoicesColumn (val type: String) : Item<ViewHolder>(){

    override fun getLayout(): Int {
        return R.layout.food_choices_card
    }

    override fun bind(viewHolder: ViewHolder, position: Int) {
        when(type){
            "PROTEIN_TITLE" -> {
                viewHolder.itemView.food_type_textview_food_choices_card.text = "Protein"
            }
            "CARBOHYDRATES_TITLE" -> {
                viewHolder.itemView.food_type_textview_food_choices_card.text = "Carbohydrates"
            }
            "FAT_TITLE" -> {
                viewHolder.itemView.food_type_textview_food_choices_card.text = "Fat"
            }
            "VEGETABLES_TITLE" -> {
                viewHolder.itemView.food_type_textview_food_choices_card.text = "Vegetables"
            }
        }
    }

}