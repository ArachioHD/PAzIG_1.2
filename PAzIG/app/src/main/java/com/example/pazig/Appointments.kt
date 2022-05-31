package com.example.pazig

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.firebase.auth.FirebaseAuth

class Appointments : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_appointments)

        val i = intent
        val email = i.getStringExtra("aone").toString()

        //go to newapp
        val newapp: Button =findViewById(R.id.newappointment)
        newapp.setOnClickListener {
            val gotonewapp= Intent(this, NewAppointment::class.java)
            gotonewapp.putExtra("atwo", email)
            startActivity(gotonewapp)
        }

        //go to app
        val apps: Button = findViewById(R.id.app)
        apps.setOnClickListener {
            val gotoapps= Intent(this, Calendar::class.java)
            gotoapps.putExtra("aapp", email)
            startActivity(gotoapps)
        }
        //log out
        firebaseAuth= FirebaseAuth.getInstance()
        val logout: Button=findViewById(R.id.logoutApp)
       logout.setOnClickListener{
            firebaseAuth.signOut()
            startActivity(Intent(this, MainActivity::class.java))
        }
        //goback
        val back = findViewById<Button>(R.id.backApp)
        back.setOnClickListener {
            val goback = Intent(applicationContext, Patient::class.java)
            startActivity(goback)
        }
    }
}