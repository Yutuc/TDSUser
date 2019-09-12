package com.univation.tdsapplication.user_profile_fragments


import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.view.*
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.univation.tdsapplication.MainActivity

import com.univation.tdsapplication.R
import com.univation.tdsapplication.bottom_nav_fragments.UserProfileFragment
import com.univation.tdsapplication.bottom_nav_fragments.WorkoutFragment
import com.univation.tdsapplication.objects.BlockObject
import com.univation.tdsapplication.register_login.LoginActivity
import com.univation.tdsapplication.workout_adapters.BlockRow
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.add_block_alert_dialog.view.*
import kotlinx.android.synthetic.main.fragment_daily_macro.view.*
import java.util.*

class DailyMacroFragment : Fragment() {

    companion object {
        var blockClicked: BlockObject? = null
    }

    val dailyMacroNutrientsBlocksHistoryArrayList = ArrayList<BlockObject>()
    val adapter = GroupAdapter<ViewHolder>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_daily_macro, container, false)
        view.recyclerview_daily_macro_blocks.adapter = adapter
        view.recyclerview_daily_macro_blocks.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))

        adapter.setOnItemClickListener { item, _ ->
            MainActivity.currentFragment = UserProfileFragment()
            MainActivity.currentFragmentPosition = R.id.user_profile_page
            val blockRow = item as BlockRow
            blockClicked = blockRow.blockObject
            val intent = Intent(context, ViewDailyMacroBlockActivity::class.java)
            startActivity(intent)
        }

        pullDailyMacroBlocks()
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
            R.id.add_daily_macro_block -> {
                /*val timeStamp = LocalDateTime.now()
                val timeFormatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
                val dateAndTime = timeStamp.format(timeFormatter)

                val ref = FirebaseDatabase.getInstance().getReference("/user-daily-macro-history/${FirebaseAuth.getInstance().uid}").push()
                ref.setValue(DailyMacronutrientsObject(ref.key!!, dateAndTime, "", "", "", "", ""))*/

                val dialogBuilder = AlertDialog.Builder(context)
                val dialogView = layoutInflater.inflate(R.layout.add_block_alert_dialog, null)

                dialogBuilder.setView(dialogView)

                val alertDialog = dialogBuilder.create()
                alertDialog.show()

                dialogView.add_block_button_add_block_alert_dialog.setOnClickListener {
                    val blockName = dialogView.block_name_input_add_block_alert_dialog.text.toString().trim()
                    if(blockName.isEmpty()){
                        Toast.makeText(context, "Please enter a block name", Toast.LENGTH_SHORT).show()
                    }
                    else {
                        val ref = FirebaseDatabase.getInstance().getReference("/daily-macros/${FirebaseAuth.getInstance().uid}").push()
                        ref.setValue(BlockObject(ref.key!!, blockName, 0))
                        Toast.makeText(context, "Successfully created $blockName", Toast.LENGTH_SHORT).show()
                        alertDialog.dismiss()
                    }
                }
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

    private fun pullDailyMacroBlocks(){
        val ref = FirebaseDatabase.getInstance().getReference("/daily-macros/${FirebaseAuth.getInstance().uid}")
        ref.addChildEventListener(object: ChildEventListener{
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {

            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {

            }

            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                val blockObject = p0.getValue(BlockObject::class.java)!!
                dailyMacroNutrientsBlocksHistoryArrayList.add(blockObject)
                refreshRecyclerView()
            }

            override fun onChildRemoved(p0: DataSnapshot) {

            }

        })
    }//pullDailyMacroHistory function

    private fun refreshRecyclerView(){
        adapter.clear()
        dailyMacroNutrientsBlocksHistoryArrayList.forEach {
            adapter.add(BlockRow(it))
        }
    }//refreshRecyclerView function
}
