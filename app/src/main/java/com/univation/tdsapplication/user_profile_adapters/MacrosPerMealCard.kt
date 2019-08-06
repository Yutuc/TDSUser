package com.univation.tdsapplication.user_profile_adapters

import com.univation.tdsapplication.R
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.macros_per_meal_card.view.*

class MacrosPerMealCard () : Item<ViewHolder>(){

    val adapter = GroupAdapter<ViewHolder>()

    override fun getLayout(): Int {
        return R.layout.macros_per_meal_card
    }

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.recyclerview_macros_per_meal.adapter = adapter
        adapter.add(MacrosPerMealTitlesRow())
        adapter.add(MacrosPerMealRow())
        adapter.add(MacrosPerMealRow())
        adapter.add(MacrosPerMealRow())
        adapter.add(MacrosPerMealRow())
        adapter.add(MacrosPerMealRow())
        adapter.add(MacrosPerMealRow())
        adapter.add(MacrosPerMealRow())
    }

}