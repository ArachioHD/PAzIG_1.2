package com.example.pazig

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.lang.StringBuilder
import java.util.*
import kotlin.properties.Delegates
import kotlin.random.Random.Default.nextInt

class PatientData : AppCompatActivity() {

    private lateinit var database: DatabaseReference

    private lateinit var name:TextView
    private lateinit var lastName: TextView
    private lateinit var age: TextView
    private lateinit var hello: TextView

    private lateinit var uname:String
    private lateinit var ulastName: String
    private lateinit var uage: String
    private lateinit var uid: String

    private lateinit var sub: String


    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_patient_data)

        database = Firebase.database.reference
        fun writeNewUser(userId: String, userName: String, userLastName: String, userAge: String) {
            val user = Users(userName, userLastName, userAge)
            database.child("patients").child(userId).setValue(user)
        }

        val i1 = intent
        val id = i1.getStringExtra("two")

        val saveData: Button = findViewById(R.id.save)
        saveData.setOnClickListener {
            name = findViewById(R.id.user_name)
            lastName = findViewById(R.id.user_lastname)
            age = findViewById(R.id.user_age)
            hello = findViewById(R.id.hello)

            uname = name.text.toString().trim()
            ulastName = lastName.text.toString().trim()
            uage = age.text.toString().trim()
            uid = id.toString().trim()

            hello.text = "hello " + uname
            name.setText(uname)
            lastName.setText(ulastName)
            sub = uid.subSequence(0, 5).toString()
            age.setText(uage)
            writeNewUser(sub, uname, ulastName, uage)

        }
        //goback
        val back = findViewById<Button>(R.id.back)
        back.setOnClickListener {
            val goback = Intent(applicationContext, Patient::class.java)
            startActivity(goback)
        }
    }
}