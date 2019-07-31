package com.univation.tdsapplication.user_profile_fragments

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class ViewPagerAdapter (fm: FragmentManager?) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return when(position){
            0 -> {
                NutritionFragment()
            }
            else -> {
                DailyMacroFragment()
            }
        }
    }

    override fun getCount(): Int {
        return 2 //number of fragments
    }

    /*override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> {
                "Nutrition"
            }
            else -> {
                "Daily Macronutrients"
            }
        }
    }*/
}