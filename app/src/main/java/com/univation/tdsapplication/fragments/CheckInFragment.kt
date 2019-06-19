package com.univation.tdsapplication.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

import com.univation.tdsapplication.R
import com.univation.tdsapplication.objects.CheckInObject
import kotlinx.android.synthetic.main.fragment_check_in.*
import kotlinx.android.synthetic.main.fragment_check_in.view.*
import java.text.DateFormat
import java.util.*

class CheckInFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_check_in, container, false)
        view.update_button_checkin.setOnClickListener {
            saveToFirebase()
        }
        return view
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
        val questionOne = prompt_one_input.text.toString()
        val questionTwo = prompt_two_input.text.toString()
        val questionThree = prompt_three_input.text.toString()
        val questionFour = prompt_four_input.text.toString()
        val questionFive = prompt_five_input.text.toString()
        val questionSix = prompt_six_input.text.toString()
        val questionSeven = prompt_seven_input.text.toString()

        val updatedCheckIn = CheckInObject(uid, date, questionOne, questionTwo, questionThree, questionFour, questionFive, questionSix, questionSeven)

        val ref = FirebaseDatabase.getInstance().getReference("/check-ins")
        ref.child("$uid").setValue(updatedCheckIn)
            .addOnSuccessListener {
                Toast.makeText(context, "Successfully updated check-in", Toast.LENGTH_SHORT).show()
            }
    }//saveToFirebase function
}
