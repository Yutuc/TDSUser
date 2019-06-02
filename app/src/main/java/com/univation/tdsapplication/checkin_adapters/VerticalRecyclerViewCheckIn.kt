package com.univation.tdsapplication.checkin_adapters

import android.app.AlertDialog
import android.content.Context
import android.renderscript.Sampler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.univation.tdsapplication.R
import com.univation.tdsapplication.objects.*
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.check_in_announcement_alert_dialog.view.*
import kotlinx.android.synthetic.main.check_in_confirmation_alert_dialog.view.*
import kotlinx.android.synthetic.main.check_in_reschedule_alert_dialog.view.*
import kotlinx.android.synthetic.main.check_in_vertical_recyclerview.view.*

class VerticalRecyclerViewCheckIn(val context: Context, val inflater: LayoutInflater, val checkInPageObject: CheckInPageObject): Item<ViewHolder>(){

    override fun bind(viewHolder: ViewHolder, position: Int) {
        val adapter = GroupAdapter<ViewHolder>()
        viewHolder.itemView.date_textview_check_in.text = checkInPageObject.date

        checkInPageObject.timesArrayList.forEach {
            adapter.add(AvailableTimesRow(it))
        }
        viewHolder.itemView.vertical_recyclerview_check_in.adapter = adapter

        adapter.setOnItemLongClickListener { item, view ->

            val itemClicked = item as AvailableTimesRow
            val timeClicked = itemClicked.availableTimeObject.time
            val dateClicked = checkInPageObject.date

            val currentUser = FirebaseAuth.getInstance().uid
            val ref = FirebaseDatabase.getInstance().getReference("/users/$currentUser").child("scheduledCheckIn")
            ref.addListenerForSingleValueEvent(object: ValueEventListener{
                override fun onDataChange(p0: DataSnapshot) {
                    val scheduledTimeObject = p0.getValue(ScheduledTimeObject::class.java)
                    if(scheduledTimeObject?.uid?.isEmpty()!!){
                        showConfirmDialog(itemClicked, timeClicked, dateClicked)
                    }
                    else{
                        showRescheduleDialog(scheduledTimeObject, itemClicked, timeClicked, dateClicked)
                    }
                }

                override fun onCancelled(p0: DatabaseError) {

                }
            })
            true //vibrates on long press
        }
    }

    private fun showConfirmDialog(itemClicked: AvailableTimesRow, timeClicked: String, dateClicked: String){
        val currentUser = FirebaseAuth.getInstance().uid

        val dialogBuilder = AlertDialog.Builder(context)
        var dialogView = inflater.inflate(R.layout.check_in_confirmation_alert_dialog, null)

        dialogBuilder.setView(dialogView)
        dialogBuilder.setTitle("Confirm Check-In?")

        dialogView?.confirmation_message_textview_checkin_popup?.text =
            "Confirm appointment for \n" + dateClicked + " at " + timeClicked + "?"

        val alertDialog = dialogBuilder.create()
        alertDialog.show()

        dialogView?.confirm_button_checkin_popup?.setOnClickListener {
            val ref = FirebaseDatabase.getInstance().getReference("/check-in-page/${checkInPageObject.date}/${itemClicked.availableTimeObject.key}").child("uid")
            ref.addListenerForSingleValueEvent(object: ValueEventListener{
                override fun onDataChange(p0: DataSnapshot) {
                    val uid = p0.getValue(String::class.java)
                    if(uid?.isEmpty()!!) {
                        ref.parent?.setValue(AvailableTimeObject(itemClicked.availableTimeObject.position, itemClicked.availableTimeObject.key, itemClicked.availableTimeObject.time, currentUser!!))
                        val userCheckInsRef = FirebaseDatabase.getInstance().getReference("/users/$currentUser/scheduledCheckIn")
                        userCheckInsRef.setValue(ScheduledTimeObject(itemClicked.availableTimeObject.position, itemClicked.availableTimeObject.key, checkInPageObject.date, itemClicked.availableTimeObject.time, currentUser!!))

                        val adminCheckInsRef = FirebaseDatabase.getInstance().getReference("admin-check-ins/$currentUser")
                        adminCheckInsRef.setValue(AdminScheduledTimeObject(getUserName(currentUser!!), checkInPageObject.date, itemClicked.availableTimeObject.time))

                        Toast.makeText(context, "Scheduled for $dateClicked at $timeClicked", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onCancelled(p0: DatabaseError) {

                }
            })
            alertDialog.dismiss()
        }
    }//showConfirmDialog function

    private fun getUserName(currentUser: String) : String{
        val ref = FirebaseDatabase.getInstance().getReference("users/$currentUser")
        var userName = ""
        ref.addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onDataChange(p0: DataSnapshot) {
                val userObject = p0.getValue(UserObject::class.java)
                if(userObject != null){
                    userName = "${userObject.firstName} ${userObject.lastName}"
                }
            }

            override fun onCancelled(p0: DatabaseError) {

            }
        })
        return userName
    }//getUserName method

    private fun showRescheduleDialog(previousSchedObject: ScheduledTimeObject, itemClicked: AvailableTimesRow, timeClicked: String, dateClicked: String){
        val currentUser = FirebaseAuth.getInstance().uid

        val dialogBuilder = AlertDialog.Builder(context)
        var dialogView = inflater.inflate(R.layout.check_in_reschedule_alert_dialog, null)

        dialogBuilder.setView(dialogView)
        dialogBuilder.setTitle("Reschedule?")

        dialogView?.reschedule_message_textview_checkin_popup?.text =
            "Reschedule appointment from \n" + dateClicked + " at " + timeClicked + "\n to \n" +
                    previousSchedObject.date + " at " + previousSchedObject.time

        val alertDialog = dialogBuilder.create()
        alertDialog.show()

        dialogView.confirm_reschedule__button_checkin_popup.setOnClickListener {
            val ref = FirebaseDatabase.getInstance().getReference("/check-in-page/${checkInPageObject.date}/${itemClicked.availableTimeObject.key}").child("uid")
            ref.addListenerForSingleValueEvent(object: ValueEventListener{
                override fun onDataChange(p0: DataSnapshot) {
                    val uid = p0.getValue(String::class.java)
                    if(uid?.isEmpty()!!) {
                        ref.parent?.setValue(AvailableTimeObject(itemClicked.availableTimeObject.position, itemClicked.availableTimeObject.key, itemClicked.availableTimeObject.time, currentUser!!))
                        val userCheckInsRef = FirebaseDatabase.getInstance().getReference("/users/$currentUser/scheduledCheckIn")
                        val previousCheckInsRef = FirebaseDatabase.getInstance().getReference("/check-in-page/${previousSchedObject.date}/${previousSchedObject.key}")
                        val adminCheckInsRef = FirebaseDatabase.getInstance().getReference("/admin-check-ins/$currentUser")

                        adminCheckInsRef.setValue(AdminScheduledTimeObject(getUserName(currentUser!!), checkInPageObject.date, itemClicked.availableTimeObject.time))
                        userCheckInsRef.setValue(ScheduledTimeObject(itemClicked.availableTimeObject.position, itemClicked.availableTimeObject.key, checkInPageObject.date, itemClicked.availableTimeObject.time, currentUser!!))
                        previousCheckInsRef.setValue(AvailableTimeObject(previousSchedObject.position, previousSchedObject.key, previousSchedObject.time, ""))

                        Toast.makeText(context, "Scheduled for $dateClicked at $timeClicked", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onCancelled(p0: DatabaseError) {

                }
            })
            alertDialog.dismiss()
        }
    }//showRescheduleDialog function

    override fun getLayout(): Int {
        return R.layout.check_in_vertical_recyclerview
    }
}