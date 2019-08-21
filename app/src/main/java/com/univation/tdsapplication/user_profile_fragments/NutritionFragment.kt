package com.univation.tdsapplication.user_profile_fragments


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase

import com.univation.tdsapplication.R
import com.univation.tdsapplication.objects.MealObject
import com.univation.tdsapplication.register_login.LoginActivity
import com.univation.tdsapplication.user_profile_adapters.*
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.fragment_nutrition.view.*

class NutritionFragment : Fragment() {

    val foodChoicesAdapter = GroupAdapter<ViewHolder>()
    val mealsAdapter = GroupAdapter<ViewHolder>()
    val mealArrayList = ArrayList<MealObject>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_nutrition, container, false)
        view.recyclerview_food_choices.adapter = foodChoicesAdapter
        foodChoicesAdapter.add(ProteinChoicesColumn())
        foodChoicesAdapter.add(CarbohydrateChoicesColumn())
        foodChoicesAdapter.add(FatChoicesColumn())

        view.recyclerview_macros_per_meal.adapter = mealsAdapter
        mealsAdapter.add(MacrosPerMealTitlesRow())
        pullMeals()

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

    private fun pullMeals(){
        val ref = FirebaseDatabase.getInstance().getReference("/user-meals/${FirebaseAuth.getInstance().uid}")
        ref.addChildEventListener(object: ChildEventListener{
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {

            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {

            }

            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                val mealObject = p0.getValue(MealObject::class.java)!!
                mealArrayList.add(mealObject)
                refreshMealsRecyclerView()
            }

            override fun onChildRemoved(p0: DataSnapshot) {

            }

        })
    }//pullMeals function

    private fun refreshMealsRecyclerView(){
        mealsAdapter.clear()
        mealsAdapter.add(MacrosPerMealTitlesRow())
        mealArrayList.forEach {
            mealsAdapter.add(MealRow(it))
        }
    }//refreshMealsRecyclerView function

}
