package com.univation.tdsapplication

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import com.google.firebase.auth.FirebaseAuth
import com.univation.tdsapplication.bottom_nav_fragments.CheckInFragment
import com.univation.tdsapplication.bottom_nav_fragments.UserProfileFragment
import com.univation.tdsapplication.bottom_nav_fragments.WorkoutFragment
import com.univation.tdsapplication.register_login.LoginActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        var mInflater: LayoutInflater? = null
        var mContext: Context? = null
        var currentFragment: Fragment? = UserProfileFragment()
        var currentFragmentPosition: Int = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        verifyUserIsLoggedIn()
        bottom_navigation_view_main.setOnNavigationItemSelectedListener(mBottomNavigationItemSelectedListener)
        replaceFragment(currentFragment!!)
        mInflater = layoutInflater
        mContext = this
    }//onCreate function

    //determines which item is selected in the bottom navigation view
    val mBottomNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when(item.itemId){
            R.id.user_profile_page -> {
                replaceFragment(UserProfileFragment())
                setTitle("User profile")
                currentFragment = UserProfileFragment()
                currentFragmentPosition = R.id.user_profile_page
                return@OnNavigationItemSelectedListener true
            }
            R.id.workout_page -> {
                replaceFragment(WorkoutFragment())
                setTitle("Choose block")
                currentFragment = WorkoutFragment()
                currentFragmentPosition = R.id.workout_page
                return@OnNavigationItemSelectedListener true
            }
            R.id.check_ins_page -> {
                replaceFragment(CheckInFragment())
                setTitle("Check-in")
                currentFragment = CheckInFragment()
                currentFragmentPosition = R.id.check_ins_page
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

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

    override fun onResume() {
        replaceFragment(currentFragment!!)
        bottom_navigation_view_main.selectedItemId = currentFragmentPosition
        super.onResume()
    }

}
