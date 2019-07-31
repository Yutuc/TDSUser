package com.univation.tdsapplication.fragments


import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.univation.tdsapplication.R
import com.univation.tdsapplication.user_profile_fragments.ViewPagerAdapter
import kotlinx.android.synthetic.main.fragment_user_profile.*
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

        return view
    }

}
