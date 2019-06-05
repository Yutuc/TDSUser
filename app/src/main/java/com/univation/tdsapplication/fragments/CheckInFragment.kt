package com.univation.tdsapplication.fragments

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase

import com.univation.tdsapplication.R
import com.univation.tdsapplication.checkin_adapters.VerticalRecyclerViewCheckIn
import com.univation.tdsapplication.objects.CheckInPageObject
import com.univation.tdsapplication.objects.ScheduledTimeObject
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.fragment_check_in.view.*

class CheckInFragment : Fragment() {

    val checkinPagesArrayList = ArrayList<CheckInPageObject>()
    val adapter = GroupAdapter<ViewHolder>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_check_in, container, false)
        pullDates(context!!, inflater)
        view.horizontal_recyclerview_check_in.adapter = adapter
        return view
    }

    private fun pullDates(context: Context, inflater: LayoutInflater){
        val ref = FirebaseDatabase.getInstance().getReference("/check-in-page")
        ref.addChildEventListener(object: ChildEventListener{
            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                pullTimes(context, inflater, p0.key!!)
            }

            override fun onChildRemoved(p0: DataSnapshot) {

            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {

            }

            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {

            }
        })
    }//pullDates function

    private fun pullTimes(context: Context, inflater: LayoutInflater, date: String){
        val ref = FirebaseDatabase.getInstance().getReference("check-in-page/$date")
        val timesArrayList = ArrayList<ScheduledTimeObject>()
        ref.addChildEventListener(object: ChildEventListener{
            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                val time = p0.getValue(ScheduledTimeObject::class.java)
                timesArrayList.add(time!!)
                refreshRecyclerView(context, inflater)
            }

            override fun onChildRemoved(p0: DataSnapshot) {

            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {
                val time = p0.getValue(ScheduledTimeObject::class.java)
                timesArrayList.set(time?.position!!, time!!)
                refreshRecyclerView(context, inflater)
            }

            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {

            }
        })
        checkinPagesArrayList.add(CheckInPageObject(date, timesArrayList))
    }//pullTimes function

    private fun refreshRecyclerView(context: Context, inflater: LayoutInflater){
        adapter.clear()
        checkinPagesArrayList.forEach {
            adapter.add(VerticalRecyclerViewCheckIn(context, inflater, it))
        }
    }//refreshRecyclerView function
}
