package com.univation.tdsapplication.bottom_nav_fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

import com.univation.tdsapplication.R
import com.univation.tdsapplication.objects.UserGoalsObject
import com.univation.tdsapplication.user_profile_fragments.ViewPagerAdapter
import kotlinx.android.synthetic.main.fragment_user_profile.view.*
import kotlinx.android.synthetic.main.fragment_user_profile.view.tab_layout_user_profile

class UserProfileFragment : Fragment() {

    val tabIconsArrayList = arrayOf(
        R.drawable.ic_nutrition,
        R.drawable.ic_daily_macros
    )

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_user_profile, container, false)

        val fragmentAdapter = ViewPagerAdapter(fragmentManager)
        view.view_pager_user_profile.adapter = fragmentAdapter
        view.view_pager_user_profile.offscreenPageLimit = fragmentAdapter.count
        view.tab_layout_user_profile.setupWithViewPager(view.view_pager_user_profile)
        for (x in 0 until tabIconsArrayList.size){
            view.tab_layout_user_profile.getTabAt(x)?.setIcon(tabIconsArrayList.get(x))
        }

        pullUserGoals()

        return view
    }

    private fun pullUserGoals(){
        val ref = FirebaseDatabase.getInstance().getReference("/user-goals/${FirebaseAuth.getInstance().uid}")
        ref.addValueEventListener(object: ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                val userGoalObject = p0.getValue(UserGoalsObject::class.java)!!
                view?.athlete_name_textview_user_profile?.text = userGoalObject.userName
                view?.start_weight_textview_user_profile?.text = "Start weight: ${userGoalObject.startWeight}Lbs"
                view?.goal_weight_textview_user_profile?.text = "Goal weight: ${userGoalObject.goalWeight}Lbs"
                view?.protein_textview_user_profile?.text = "Protein: ${userGoalObject.protein}g"
                view?.carbohydrates_textview_user_profile?.text = "Carbs: ${userGoalObject.carbohydrates}g"
                view?.fat_textview_user_profile?.text = "Fats: ${userGoalObject.fat}g"
                view?.calories_textview_user_profile?.text = "Calories: ${userGoalObject.calories}"
            }

        })
    }//pullUserGoals function
}
