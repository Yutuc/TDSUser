package com.univation.tdsapplication

import android.app.AlertDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.univation.tdsapplication.fragments.CheckInFragment
import com.univation.tdsapplication.fragments.WorkoutFragment
import com.univation.tdsapplication.objects.ScheduledTimeObject
import com.univation.tdsapplication.registerlogin.LoginActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.check_in_announcement_alert_dialog.view.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        verifyUserIsLoggedIn()
        replaceFragment(WorkoutFragment()) //Initially starts the app in the WorkoutFragment
        setTitle("Workout") //Initially sets ActionBar title to "Workout"
        bottom_navigation_view_main.setOnNavigationItemSelectedListener(mBottomNavigationItemSelectedListener)
    }//onCreate function

    //determines which item is selected in the bottom navigation view
    val mBottomNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when(item.itemId){
            R.id.workout_page -> {
                replaceFragment(WorkoutFragment())
                setTitle("Workout")
                return@OnNavigationItemSelectedListener true
            }
            R.id.check_ins_page -> {
                replaceFragment(CheckInFragment())
                setTitle("Check-in")
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

    //replaces fragment on screen depending on which bottom nav menu item is selected
    private fun replaceFragment(fragment: Fragment){
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainer, fragment)
        fragmentTransaction.commit()
    }//replaceFragment

    //displays menu items
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.top_nav_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }//onCreateOptionsMenu function

    //handles actions when a menu item is touched/clicked
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        //"when" statements in Kotlin are "switch cases"in Java
        when (item?.itemId) {
            R.id.sign_out_top_nav_menu -> {
                FirebaseAuth.getInstance().signOut()
                val intent = Intent(this, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
            R.id.announcement_top_nav_menu -> {
                val dialogBuilder = AlertDialog.Builder(this)
                var dialogView = layoutInflater.inflate(R.layout.check_in_announcement_alert_dialog, null)

                dialogBuilder.setView(dialogView)
                val currentUser = FirebaseAuth.getInstance().uid
                val ref = FirebaseDatabase.getInstance().getReference("/users/$currentUser").child("scheduledCheckIn")

                var date = ""
                var scheduledTimeObject: ScheduledTimeObject? = null

                ref.addListenerForSingleValueEvent(object: ValueEventListener{
                    override fun onDataChange(p0: DataSnapshot) {
                        scheduledTimeObject = p0.getValue(ScheduledTimeObject::class.java)!!
                        date = scheduledTimeObject?.date!!
                        if(!date.isEmpty()){
                            dialogView.scheduled_check_in_announcement_textview.text = "Check-In on " + date + " at " + scheduledTimeObject?.time
                        }
                    }

                    override fun onCancelled(p0: DatabaseError) {

                    }
                })

                dialogBuilder.setTitle("Reminder")

                val alertDialog = dialogBuilder.create()
                alertDialog.show()

                dialogView.cancel_button_announcement.setOnClickListener {
                    if(date.isEmpty()){
                        Toast.makeText(this, "No check-in to cancel", Toast.LENGTH_SHORT).show()
                    }
                    else{
                        val checkInPageRef = FirebaseDatabase.getInstance().getReference("/check-in-page/$date/${scheduledTimeObject?.key}")
                        checkInPageRef.setValue(ScheduledTimeObject(scheduledTimeObject?.position!!, scheduledTimeObject?.key!!, scheduledTimeObject?.date!!, scheduledTimeObject?.time!!, ""))
                        ref.child("date").setValue("")
                        ref.child("key").setValue("")
                        ref.child("position").setValue(-1)
                        ref.child("time").setValue("")
                        ref.child("uid").setValue("")

                        val adminRef = FirebaseDatabase.getInstance().getReference("/admin-check-ins/$currentUser")
                        adminRef.removeValue()

                        Toast.makeText(this, "Successfully cancelled check-in", Toast.LENGTH_SHORT).show()
                    }
                    alertDialog.dismiss()
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }//onOptionsItemSelected function

    //verifies a user is logged in, otherwise go to LoginActivity
    private fun verifyUserIsLoggedIn(){
        if(FirebaseAuth.getInstance().uid == null){
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
    }//verifyUserIsLoggedIn function
}
