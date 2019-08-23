package com.univation.tdsapplication.user_profile_fragments

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.univation.tdsapplication.R
import com.univation.tdsapplication.objects.DailyMacronutrientsAveragesObject
import com.univation.tdsapplication.objects.DailyMacronutrientsObject
import com.univation.tdsapplication.user_profile_adapters.DailyMacronutrientsAveragesCard
import com.univation.tdsapplication.user_profile_adapters.DailyMacronutrientsCard
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_view_daily_macro_block.*
import java.lang.Exception
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.ArrayList

class ViewDailyMacroBlockActivity : AppCompatActivity() {

    companion object {
        var mInflater: LayoutInflater? = null
        var mContext: Context? = null
    }

    val adapter = GroupAdapter<ViewHolder>()
    val blockClicked = DailyMacroFragment.blockClicked
    val dailyMacroNutrientsHistoryArrayList = ArrayList<DailyMacronutrientsObject>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_daily_macro_block)
        setTitle("${blockClicked!!.blockName}")

        recyclerview_daily_macros_and_average.adapter = adapter

        pullDailyMacros()

        mInflater = layoutInflater
        mContext = this
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.view_daily_macro_block_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item!!.itemId) {
            R.id.add_daily_macro -> {
                val timeStamp = LocalDateTime.now()
                val timeFormatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
                val dateAndTime = timeStamp.format(timeFormatter)

                val ref = FirebaseDatabase.getInstance().getReference("/daily-macros/${FirebaseAuth.getInstance().uid}/${blockClicked!!.blockName}").push()
                ref.setValue(DailyMacronutrientsObject(ref.key!!, dateAndTime, "", "", "", "", ""))
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun pullDailyMacros(){
        val ref = FirebaseDatabase.getInstance().getReference("/daily-macros/${FirebaseAuth.getInstance().uid}/${blockClicked!!.blockName}")
        ref.addChildEventListener(object: ChildEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {

            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {
                try {
                    val dailyMacronutrientsObject = p0.getValue(DailyMacronutrientsObject::class.java)!!
                    dailyMacroNutrientsHistoryArrayList[find(dailyMacronutrientsObject)] = dailyMacronutrientsObject
                    refreshRecyclerView()
                }catch (e: Exception){}
            }

            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                try {
                    val dailyMacronutrientsObject = p0.getValue(DailyMacronutrientsObject::class.java)!!
                    dailyMacroNutrientsHistoryArrayList.add(dailyMacronutrientsObject)
                    refreshRecyclerView()
                }catch (e: Exception){}
            }

            override fun onChildRemoved(p0: DataSnapshot) {

            }

        })
    }//pullDailyMacroHistory function

    private fun refreshRecyclerView(){
        adapter.clear()
        adapter.add(DailyMacronutrientsAveragesCard(macroAverages()))
        dailyMacroNutrientsHistoryArrayList.forEach {
            adapter.add(DailyMacronutrientsCard(it))
        }
    }//refreshRecyclerView function

    private fun macroAverages() : DailyMacronutrientsAveragesObject{
        var protein = 0f
        var carbohydrates = 0f
        var fat = 0f
        var calories = 0f
        var weight = 0f

        dailyMacroNutrientsHistoryArrayList.forEach {
            try{
                protein += it.protein.toFloat()
            }catch (e: Exception){}

            try{
                carbohydrates += it.carbohydrates.toFloat()
            }catch (e: Exception){}

            try{
                fat += it.fat.toFloat()
            }catch (e: Exception){}

            try{
                calories += it.calories.toFloat()
            }catch (e: Exception){}

            try{
                weight += it.weight.toFloat()
            }catch (e: Exception){}
        }

        protein = protein.div(dailyMacroNutrientsHistoryArrayList.size)
        carbohydrates = carbohydrates.div(dailyMacroNutrientsHistoryArrayList.size)
        fat = fat.div(dailyMacroNutrientsHistoryArrayList.size)
        calories = calories.div(dailyMacroNutrientsHistoryArrayList.size)
        weight = weight.div(dailyMacroNutrientsHistoryArrayList.size)

        val dailyMacronutrientsAveragesObject = DailyMacronutrientsAveragesObject("%.2f".format(protein), "%.2f".format(carbohydrates), "%.2f".format(fat), "%.2f".format(calories), "%.2f".format(weight))
        return dailyMacronutrientsAveragesObject
    }//macroAverages function

    private fun find(dailyMacronutrientsObject: DailyMacronutrientsObject) : Int{
        for (i in dailyMacroNutrientsHistoryArrayList.size-1 downTo 0 step 1) {
            if(dailyMacroNutrientsHistoryArrayList.get(i).key == dailyMacronutrientsObject.key){
                return i
            }
        }
        return -1
    }
}
