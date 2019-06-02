package com.univation.tdsapplication.registerlogin

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.univation.tdsapplication.MainActivity
import com.univation.tdsapplication.R
import com.univation.tdsapplication.objects.AvailableTimeObject
import com.univation.tdsapplication.objects.ScheduledTimeObject
import com.univation.tdsapplication.objects.UserObject
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        register_button_register.setOnClickListener { registerUser() }
        already_have_an_account_textview_register.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }//onCreate function

    //registers UserObject into FirebaseAuthentication
    private fun registerUser(){
        val firstName = first_name_edittext_register.text.toString().trim()
        val lastName = last_name_edittext_register.text.toString().trim()
        val email = email_edittext_register.text.toString().trim()
        val password = password_edittext_register.text.toString().trim()
        val confirmPassword = confirm_password_edittext_register.text.toString().trim()

        if(firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()){
            Toast.makeText(this, "Please fill in all text fields", Toast.LENGTH_SHORT).show()
        } else{
            if(password == confirmPassword){
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                    .addOnSuccessListener {
                        saveUserToFirebaseDatabase()
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                    }
            } else{
                Toast.makeText(this, "Passwords don't match", Toast.LENGTH_SHORT).show()
            }
        }
    }//registerUser function

    //saves registered user to FirebaseDatabase as UserObject object
    private fun saveUserToFirebaseDatabase(){
        val firstName = first_name_edittext_register.text.toString().trim()
        val lastName = last_name_edittext_register.text.toString().trim()
        val email = email_edittext_register.text.toString().trim()

        val uid = FirebaseAuth.getInstance().uid
        val ref = FirebaseDatabase.getInstance().getReference("/users/$uid")
        val user = UserObject(uid!!, email, firstName, lastName, ScheduledTimeObject(-1, "", "", "", ""))

        ref.setValue(user)
            .addOnSuccessListener {
            Toast.makeText(this, "Successful registration", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK) //clears the stack of activities
            startActivity(intent)
            }
            .addOnFailureListener {
                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
            }
    }//saveUserToFirebaseDatabase

}
