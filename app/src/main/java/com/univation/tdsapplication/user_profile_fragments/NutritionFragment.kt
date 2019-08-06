package com.univation.tdsapplication.user_profile_fragments


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import com.google.firebase.auth.FirebaseAuth

import com.univation.tdsapplication.R
import com.univation.tdsapplication.register_login.LoginActivity
import com.univation.tdsapplication.user_profile_adapters.FoodChoicesColumn
import com.univation.tdsapplication.user_profile_adapters.MacrosPerMealCard
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.fragment_nutrition.view.*

class NutritionFragment : Fragment() {


    val PROTEIN_TITLE = "PROTEIN_TITLE"
    val CARBOHYDRATES_TITLE = "CARBOHYDRATES_TITLE"
    val FAT_TITLE = "FAT_TITLE"
    val VEGETABLES_TITLE = "VEGETABLES_TITLE"

    val foodChoicesAdapter = GroupAdapter<ViewHolder>()
    val macrosPerMealAdapter = GroupAdapter<ViewHolder>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_nutrition, container, false)
        view.recyclerview_food_choices.adapter = foodChoicesAdapter
        foodChoicesAdapter.add(FoodChoicesColumn(PROTEIN_TITLE))
        foodChoicesAdapter.add(FoodChoicesColumn(CARBOHYDRATES_TITLE))
        foodChoicesAdapter.add(FoodChoicesColumn(FAT_TITLE))
        foodChoicesAdapter.add(FoodChoicesColumn(VEGETABLES_TITLE))

        view.recyclerview_macros_per_meal.adapter = macrosPerMealAdapter
        macrosPerMealAdapter.add(MacrosPerMealCard())
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.nutrition_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.sign_out -> {
                FirebaseAuth.getInstance().signOut()
                val intent = Intent(context, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

}
