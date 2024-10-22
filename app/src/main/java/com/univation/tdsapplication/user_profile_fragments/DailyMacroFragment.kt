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
import com.univation.tdsapplication.objects.DailyMacronutrientsObject
import com.univation.tdsapplication.register_login.LoginActivity
import com.univation.tdsapplication.user_profile_adapters.DailyMacronutrientsCard
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.fragment_daily_macro.view.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*

class DailyMacroFragment : Fragment() {

    val dailyMacronutrientsArrayList = ArrayList<DailyMacronutrientsObject>()
    val adapter = GroupAdapter<ViewHolder>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_daily_macro, container, false)
        view.recyclerview_daily_macro_blocks.adapter = adapter

        pullDailyMacros()
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.daily_macro_fragment_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.add_daily_macro -> {
                val timeStamp = LocalDateTime.now()
                val timeFormatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
                val dateAndTime = timeStamp.format(timeFormatter)

                val ref = FirebaseDatabase.getInstance().getReference("/daily-macros/${FirebaseAuth.getInstance().uid}").push()
                ref.setValue(DailyMacronutrientsObject(ref.key!!, dateAndTime, "", "", "", "", ""))
            }
            R.id.sign_out -> {
                FirebaseAuth.getInstance().signOut()
                val intent = Intent(context, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun pullDailyMacros(){
        val ref = FirebaseDatabase.getInstance().getReference("/daily-macros/${FirebaseAuth.getInstance().uid}")
        ref.addChildEventListener(object: ChildEventListener{
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {

            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {
                val dailyMacronutrientsObject = p0.getValue(DailyMacronutrientsObject::class.java)!!
                dailyMacronutrientsArrayList[find(dailyMacronutrientsObject)] = dailyMacronutrientsObject
                refreshRecyclerView()
            }

            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                val dailyMacronutrientsObject = p0.getValue(DailyMacronutrientsObject::class.java)!!
                dailyMacronutrientsArrayList.add(dailyMacronutrientsObject)
                refreshRecyclerView()
            }

            override fun onChildRemoved(p0: DataSnapshot) {

            }

        })
    }//pullDailyMacros function

    private fun refreshRecyclerView(){
        adapter.clear()
        for (i in dailyMacronutrientsArrayList.size-1 downTo 0 step 1) {
            adapter.add(DailyMacronutrientsCard(dailyMacronutrientsArrayList[i]))
        }
    }//refreshRecyclerView function

    private fun find(dailyMacronutrientsObject: DailyMacronutrientsObject) : Int {
        for (i in dailyMacronutrientsArrayList.size-1 downTo 0 step 1) {
            if(dailyMacronutrientsArrayList[i].key == dailyMacronutrientsObject.key){
                return i
            }
        }
        return -1
    }
}
