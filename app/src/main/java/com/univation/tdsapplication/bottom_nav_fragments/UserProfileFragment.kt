package com.univation.tdsapplication.bottom_nav_fragments


import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

import com.univation.tdsapplication.R
import com.univation.tdsapplication.objects.DailyMacronutrientsAveragesObject
import com.univation.tdsapplication.objects.DailyMacronutrientsObject
import com.univation.tdsapplication.objects.UserGoalsObject
import com.univation.tdsapplication.user_profile_fragments.DailyMacroFragment
import com.univation.tdsapplication.user_profile_fragments.ViewPagerAdapter
import kotlinx.android.synthetic.main.fragment_user_profile.view.*
import kotlinx.android.synthetic.main.fragment_user_profile.view.tab_layout_user_profile
import java.util.ArrayList

class UserProfileFragment : Fragment() {

    companion object {
        var tabSelected = 0
    }

    val dailyMacroNutrientsHistoryArrayList = ArrayList<DailyMacronutrientsObject>()

    val tabIconsArrayList = arrayOf(
        R.drawable.ic_nutrition,
        R.drawable.ic_daily_macros
    )

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_user_profile, container, false)
        val fragmentAdapter = ViewPagerAdapter(childFragmentManager)
        view.view_pager_user_profile.adapter = fragmentAdapter
        view.view_pager_user_profile.offscreenPageLimit = fragmentAdapter.count
        view.tab_layout_user_profile.setupWithViewPager(view.view_pager_user_profile)
        view.tab_layout_user_profile.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener{
            override fun onTabReselected(p0: TabLayout.Tab?) {

            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {

            }

            override fun onTabSelected(p0: TabLayout.Tab?) {
                tabSelected = p0!!.position
                when(tabSelected) {
                    0 -> {
                        view.app_bar_user_profile.alpha = 1f
                        view.app_bar_daily_macro_averages.alpha = 0f
                    }
                    1->{
                        view.app_bar_user_profile.alpha = 0f
                        view.app_bar_daily_macro_averages.alpha = 1f
                    }
                }
            }

        })

        for (x in 0 until tabIconsArrayList.size){
            view.tab_layout_user_profile.getTabAt(x)?.setIcon(tabIconsArrayList.get(x))
        }

        pullUserGoals()
        pullDailyMacros()
        return view
    }

    private fun pullDailyMacros(){
        val ref = FirebaseDatabase.getInstance().getReference("/daily-macros/${FirebaseAuth.getInstance().uid}")
        ref.addChildEventListener(object: ChildEventListener{
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {

            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {
                try {
                    val dailyMacronutrientsObject = p0.getValue(DailyMacronutrientsObject::class.java)!!
                    dailyMacroNutrientsHistoryArrayList[find(dailyMacronutrientsObject)] = dailyMacronutrientsObject
                    displayMacroAverages()
                }catch (e: java.lang.Exception){}
            }

            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                try {
                    val dailyMacronutrientsObject = p0.getValue(DailyMacronutrientsObject::class.java)!!
                    dailyMacroNutrientsHistoryArrayList.add(dailyMacronutrientsObject)
                    displayMacroAverages()
                }catch (e: java.lang.Exception){}
            }

            override fun onChildRemoved(p0: DataSnapshot) {

            }

        })
    }//pullDailyMacros function

    private fun displayMacroAverages(){
        val macroAverages = macroAverages()
        view!!.protein_average_text.text = macroAverages!!.protein
        view!!.carbohydrates_average_text.text = macroAverages!!.carbohydrates
        view!!.fat_average_text.text = macroAverages!!.fat
        view!!.calories_average_text.text = macroAverages!!.calories
        view!!.weight_average_text.text = macroAverages!!.weight
    }//displayMacroAverages function

    private fun pullUserGoals(){
        val ref = FirebaseDatabase.getInstance().getReference("/user-goals/${FirebaseAuth.getInstance().uid}")
        ref.addValueEventListener(object: ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                try{
                    val userGoalObject = p0.getValue(UserGoalsObject::class.java)!!
                    view?.athlete_name_textview_user_profile?.text = userGoalObject.userName
                    view?.start_weight_textview_user_profile?.text = "Start weight: ${userGoalObject.startWeight}Lbs"
                    view?.goal_weight_textview_user_profile?.text = "Goal weight: ${userGoalObject.goalWeight}Lbs"
                    view?.protein_textview_user_profile?.text = "Protein: ${userGoalObject.protein}g"
                    view?.carbohydrates_textview_user_profile?.text = "Carbs: ${userGoalObject.carbohydrates}g"
                    view?.fat_textview_user_profile?.text = "Fats: ${userGoalObject.fat}g"
                    view?.calories_textview_user_profile?.text = "Calories: ${userGoalObject.calories}"
                }
                catch(e: Exception){}
            }

        })
    }//pullUserGoals function

    private fun macroAverages() : DailyMacronutrientsAveragesObject{
        var protein = 0f
        var carbohydrates = 0f
        var fat = 0f
        var calories = 0f
        var weight = 0f

        if(dailyMacroNutrientsHistoryArrayList.size <= 7) {
            dailyMacroNutrientsHistoryArrayList.forEach {
                try{
                    protein += it.protein.toFloat()
                }catch (e: java.lang.Exception){}

                try{
                    carbohydrates += it.carbohydrates.toFloat()
                }catch (e: java.lang.Exception){}

                try{
                    fat += it.fat.toFloat()
                }catch (e: java.lang.Exception){}

                try{
                    calories += it.calories.toFloat()
                }catch (e: java.lang.Exception){}

                try{
                    weight += it.weight.toFloat()
                }catch (e: java.lang.Exception){}
            }

            protein = protein.div(dailyMacroNutrientsHistoryArrayList.size)
            carbohydrates = carbohydrates.div(dailyMacroNutrientsHistoryArrayList.size)
            fat = fat.div(dailyMacroNutrientsHistoryArrayList.size)
            calories = calories.div(dailyMacroNutrientsHistoryArrayList.size)
            weight = weight.div(dailyMacroNutrientsHistoryArrayList.size)
        }
        else{
            for (i in dailyMacroNutrientsHistoryArrayList.size-1 downTo dailyMacroNutrientsHistoryArrayList.size-7 step 1) {
                try{
                    protein += dailyMacroNutrientsHistoryArrayList[i].protein.toFloat()
                }catch (e: java.lang.Exception){}

                try{
                    carbohydrates += dailyMacroNutrientsHistoryArrayList[i].carbohydrates.toFloat()
                }catch (e: java.lang.Exception){}

                try{
                    fat += dailyMacroNutrientsHistoryArrayList[i].fat.toFloat()
                }catch (e: java.lang.Exception){}

                try{
                    calories += dailyMacroNutrientsHistoryArrayList[i].calories.toFloat()
                }catch (e: java.lang.Exception){}

                try{
                    weight += dailyMacroNutrientsHistoryArrayList[i].weight.toFloat()
                }catch (e: java.lang.Exception){}
            }

            protein = protein.div(7)
            carbohydrates = carbohydrates.div(7)
            fat = fat.div(7)
            calories = calories.div(7)
            weight = weight.div(7)
        }

        val dailyMacronutrientsAveragesObject = DailyMacronutrientsAveragesObject("%.2f".format(protein), "%.2f".format(carbohydrates), "%.2f".format(fat), "%.2f".format(calories), "%.2f".format(weight))
        return dailyMacronutrientsAveragesObject
    }//macroAverages function

    private fun find(dailyMacronutrientsObject: DailyMacronutrientsObject) : Int {
        for (i in dailyMacroNutrientsHistoryArrayList.size-1 downTo 0 step 1) {
            if(dailyMacroNutrientsHistoryArrayList.get(i).key == dailyMacronutrientsObject.key){
                return i
            }
        }
        return -1
    }

    override fun onResume() {
        val tabSelected = view!!.tab_layout_user_profile.getTabAt(tabSelected)
        tabSelected!!.select()
        super.onResume()
    }
}
