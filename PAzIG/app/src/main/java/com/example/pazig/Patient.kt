package com.example.pazig

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.firebase.auth.FirebaseAuth

class Patient : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_patient)

        //get email
        val i = intent
        val email = i.getStringExtra("one").toString()

        //go to mydata
        val patdata: Button =findViewById(R.id.mydata)
        patdata.setOnClickListener {
            val gotopatdata=Intent(this, PatientData::class.java)
            gotopatdata.putExtra("two", email)
            startActivity(gotopatdata)
        }
        //go to appointments
        val app: Button =findViewById(R.id.Pappointments)
        app.setOnClickListener {
            val gotoapp = Intent(this, Appointments::class.java)
            gotoapp.putExtra("aone", email)
            startActivity(gotoapp)

        }
        firebaseAuth= FirebaseAuth.getInstance()
        val logout: Button=findViewById(R.id.logoutPat)
        logout.setOnClickListener {
            firebaseAuth.signOut()
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}