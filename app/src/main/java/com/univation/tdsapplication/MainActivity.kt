package com.univation.tdsapplication

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.univation.tdsapplication.fragments.CheckInFragment
import com.univation.tdsapplication.fragments.UserProfileFragment
import com.univation.tdsapplication.fragments.WorkoutFragment
import com.univation.tdsapplication.registerlogin.LoginActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_user_profile.*

class MainActivity : AppCompatActivity() {

    companion object {
        var currentTab = 0
    }

    val workoutFragment = WorkoutFragment()
    val checkInFragment = CheckInFragment()
    val userProfileFragment = UserProfileFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        verifyUserIsLoggedIn()
        initializeFragments()
        setTitle("Choose block") //Initially sets ActionBar title to "Workout"
        bottom_navigation_view_main.setOnNavigationItemSelectedListener(mBottomNavigationItemSelectedListener)
        setSelectedTab()
    }//onCreate function

    //determines which item is selected in the bottom navigation view
    val mBottomNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when(item.itemId){
            R.id.user_profile_page -> {
                changeFragment(0)
                //replaceFragment(UserProfileFragment())
                setTitle("User profile")
                currentTab = 0
                return@OnNavigationItemSelectedListener true
            }
            R.id.workout_page -> {
                changeFragment(1)
                //replaceFragment(WorkoutFragment())
                setTitle("Choose block")
                currentTab = 1
                return@OnNavigationItemSelectedListener true
            }
            R.id.check_ins_page -> {
                changeFragment(2)
                //replaceFragment(CheckInFragment())
                setTitle("Check-in")
                currentTab = 2
                return@OnNavigationItemSelectedListener true
            }
            /*R.id.shop_page -> {
                replaceFragment(ShopFragment())
                setTitle("Shop")
                return@OnNavigationItemSelectedListener true
            }
            R.id.info_page -> {
                replaceFragment(InfoFragment())
                setTitle("Info")
                return@OnNavigationItemSelectedListener true
            }*/
        }
        false
    }

    private fun initializeFragments(){
        val fragmentTransaction = supportFragmentManager.beginTransaction()

        fragmentTransaction.add(R.id.fragmentContainer, workoutFragment)
        fragmentTransaction.add(R.id.fragmentContainer, checkInFragment)
        fragmentTransaction.add(R.id.fragmentContainer, userProfileFragment)

        fragmentTransaction.hide(workoutFragment)
        fragmentTransaction.hide(checkInFragment)
        //don't hide userProfileFragment because it's the initial fragment shown

        fragmentTransaction.commit()
    }//initializeFragments function

    private fun changeFragment(position: Int){
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        when(position) {
            0 -> {
                fragmentTransaction.show(userProfileFragment)
                fragmentTransaction.hide(workoutFragment)
                fragmentTransaction.hide(checkInFragment)
            }
            1 -> {
                val tab = tab_layout_user_profile.getTabAt(0)
                tab!!.select()
                fragmentTransaction.show(workoutFragment)
                fragmentTransaction.hide(checkInFragment)
                fragmentTransaction.hide(userProfileFragment)
            }
            else -> {
                val tab = tab_layout_user_profile.getTabAt(0)
                tab!!.select()
                fragmentTransaction.show(checkInFragment)
                fragmentTransaction.hide(workoutFragment)
                fragmentTransaction.hide(userProfileFragment)
            }
        }
        fragmentTransaction.commit()
    }//changeFragment function

    private fun setSelectedTab(){
        when(currentTab){
            0 -> {
                bottom_navigation_view_main.selectedItemId = R.id.user_profile_page
            }
            1-> {
                bottom_navigation_view_main.selectedItemId = R.id.workout_page
            }
            else -> {
                bottom_navigation_view_main.selectedItemId = R.id.check_ins_page
            }
        }
    }//setSelectedTab function

    //replaces fragment on screen depending on which bottom nav menu item is selected
    private fun replaceFragment(fragment: Fragment){
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainer, fragment)
        fragmentTransaction.commit()
    }//replaceFragment

    //verifies a user is logged in, otherwise go to LoginActivity
    private fun verifyUserIsLoggedIn(){
        if(FirebaseAuth.getInstance().uid == null){
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
    }//verifyUserIsLoggedIn function
}
