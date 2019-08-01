package com.univation.tdsapplication.user_profile_fragments


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import android.widget.Toast
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
import kotlinx.android.synthetic.main.fragment_daily_macro.*
import kotlinx.android.synthetic.main.fragment_daily_macro.view.*
import java.text.DateFormat
import java.util.*

class DailyMacroFragment : Fragment() {

    val dailyMacroNutrientsHistoryArrayList = ArrayList<DailyMacronutrientsObject>()
    val adapter = GroupAdapter<ViewHolder>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_daily_macro, container, false)
        view.recyclerview_daily_macro.adapter = adapter
        pullDailyMacroHistory()
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.daily_macro_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.add_daily_macro -> {
                val calendar = Calendar.getInstance()
                val date = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.time)

                val ref = FirebaseDatabase.getInstance().getReference("/user-daily-macro-history/${FirebaseAuth.getInstance().uid}").push()
                ref.setValue(DailyMacronutrientsObject(ref.key!!, date, "", "", "", "", ""))
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

    private fun pullDailyMacroHistory(){
        val ref = FirebaseDatabase.getInstance().getReference("/user-daily-macro-history/${FirebaseAuth.getInstance().uid}")
        ref.addChildEventListener(object: ChildEventListener{
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {

            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {

            }

            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                val dailyMacronutrientsObject = p0.getValue(DailyMacronutrientsObject::class.java)!!
                dailyMacroNutrientsHistoryArrayList.add(dailyMacronutrientsObject)
                refreshRecyclerView()
            }

            override fun onChildRemoved(p0: DataSnapshot) {

            }

        })
    }//pullDailyMacroHistory function

    private fun refreshRecyclerView(){
        adapter.clear()
        dailyMacroNutrientsHistoryArrayList.forEach {
            adapter.add(DailyMacronutrientsCard(it))
        }
    }//refreshRecyclerView function
}
