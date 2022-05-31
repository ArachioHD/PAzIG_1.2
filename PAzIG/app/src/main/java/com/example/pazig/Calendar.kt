package com.example.pazig

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.google.common.collect.Collections2.filter
import com.google.common.collect.Iterables.filter
import com.google.common.collect.Multisets.filter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import java.util.HashMap
import java.util.Locale.filter


class Calendar : AppCompatActivity() {

    private lateinit var database: DatabaseReference


    private lateinit var apid: String
    private lateinit var apdate: String
    private lateinit var apdescription: String
    private lateinit var aphumor: String
    private lateinit var apmedications: String
    private lateinit var app: Appointment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar)

        //show
        val show = findViewById<Button>(R.id.showinfo)
        show.setOnClickListener {

            val showid = findViewById<TextView>(R.id.app_id)
            //showapp = showid.text.toString().trim()
            val ical = intent
            val mailid = ical.getStringExtra("id")
/*
            database = FirebaseDatabase.getInstance().reference
            val postListener = object : ValueEventListener {

                @SuppressLint("SetTextI18n")
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    // Get Post object and use the values to update the UI
                    for (i in dataSnapshot.children) {
                        val apid = dataSnapshot.child("appointments/$mailid/$showapp/appointmentID").value
                        val apdate = dataSnapshot.child("appointments/$mailid/$showapp/appointmentdate").value
                        val apdescription = dataSnapshot.child("appointments/$mailid/$showapp/appointmentdescription").value
                        val aphumor = dataSnapshot.child("appointments/$mailid/$showapp/humor").value
                        val apmedications = dataSnapshot.child("appointments/$mailid/$showapp/medications").value

                        val text = findViewById<TextView>(R.id.calendarshow)
                        text.text = "ID: $apid \n DATE: $apdate \n DESCRIPTION: $apdescription \n HUMOR: $aphumor \n PRESCRIBED MEDICATIONS: $apmedications \n"

                    }
                    // ...
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Getting Post failed, log a message

                }
            }

            database.addValueEventListener(postListener)
            database.addListenerForSingleValueEvent(postListener)

            */
            val uid = FirebaseAuth.getInstance().currentUser!!.uid
            database = FirebaseDatabase.getInstance().reference.child("appointments").child(uid)
            val postListener = object : ValueEventListener {
                @RequiresApi(Build.VERSION_CODES.N)
                @SuppressLint("SetTextI18n")
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    // Get Post object and use the values to update the UI

                    val post: HashMap<String, Any?> = dataSnapshot.value as HashMap<String, Any?>
                    val ks = post.mapKeys {it.key.toString() + '-' + it.value}
                    for (key in post){
                        val text = findViewById<TextView>(R.id.calendarshow)
                        val x= post.keys.toString().trim()
                        for( i in 0..2) {
                            val id=x.getOrNull(i)
                            //val x1 = x.elementAtOrNull(2)
                            text.text = "$x"
                        }
                    }
                        for ((key, value) in post) {


                    }

                    //val text = findViewById<TextView>(R.id
                       // .calendarshow)
                    //text.setText ("ID: $post")
                    // ...
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Getting Post failed, log a message
                    Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
                }
            }
            database.addValueEventListener(postListener)
        }
            /*
            val postListener = object : ValueEventListener {

                @SuppressLint("SetTextI18n")
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    // Get Post object and use the values to update the UI
                    for (i in dataSnapshot.children) {
                        val dzien = i.child("Users").child(uid).key
                    //if (dataSnapshot.exists()) {
                        //val key = database.child("Users").child(uid).push().key.toString().trim()
                        //val key2=database.get()
                        val text = findViewById<TextView>(R.id.calendarshow)


                        //val id=dataSnapshot.child("Appointments/$appid").key

                        text.setText("$dzien")
                    }

                    // ...
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Getting Post failed, log a message

                }
            }

            database.addValueEventListener(postListener)
            database.addListenerForSingleValueEvent(postListener)
        }
         */
            //goback
            val back = findViewById<Button>(R.id.backCal)
            back.setOnClickListener {
                val goback = Intent(applicationContext, Appointments::class.java)
                startActivity(goback)
            }
        }

    }