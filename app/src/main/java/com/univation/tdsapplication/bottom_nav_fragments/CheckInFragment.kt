package com.univation.tdsapplication.bottom_nav_fragments

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

import com.univation.tdsapplication.R
import com.univation.tdsapplication.objects.CheckInObject
import com.univation.tdsapplication.register_login.LoginActivity
import kotlinx.android.synthetic.main.fragment_check_in.*
import java.text.DateFormat
import java.util.*

class CheckInFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_check_in, container, false)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.check_in_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.save_check_in -> {
               saveToFirebase()
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

    private fun saveToFirebase(){
        if(prompt_one_input.text.isEmpty() || prompt_two_input.text.isEmpty() || prompt_three_input.text.isEmpty() || prompt_four_input.text.isEmpty() ||
            prompt_five_input.text.isEmpty() || prompt_six_input.text.isEmpty() || prompt_seven_input.text.isEmpty()){
            Toast.makeText(context, "Empty field detected", Toast.LENGTH_SHORT).show()
            return
        }

        val uid = FirebaseAuth.getInstance().uid.toString()
        val calendar = Calendar.getInstance()
        val date = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.time)
        val questionOne = prompt_one_input.text.toString().trim()
        val questionTwo = prompt_two_input.text.toString().trim()
        val questionThree = prompt_three_input.text.toString().trim()
        val questionFour = prompt_four_input.text.toString().trim()
        val questionFive = prompt_five_input.text.toString().trim()
        val questionSix = prompt_six_input.text.toString().trim()
        val questionSeven = prompt_seven_input.text.toString().trim()

        val updatedCheckIn = CheckInObject(uid, date, questionOne, questionTwo, questionThree, questionFour, questionFive, questionSix, questionSeven)

        val ref = FirebaseDatabase.getInstance().getReference("/check-ins")
        ref.child("$uid").setValue(updatedCheckIn)
            .addOnSuccessListener {
                Toast.makeText(context, "Successfully updated check-in", Toast.LENGTH_SHORT).show()
                prompt_one_input.setText("")
                prompt_two_input.setText("")
                prompt_three_input.setText("")
                prompt_four_input.setText("")
                prompt_five_input.setText("")
                prompt_six_input.setText("")
                prompt_seven_input.setText("")
            }
    }//saveToFirebase function
}
