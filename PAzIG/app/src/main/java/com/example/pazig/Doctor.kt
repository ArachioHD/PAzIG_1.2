package com.example.pazig

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class Doctor : AppCompatActivity() {

    private lateinit var database: DatabaseReference

    private lateinit var mail: TextView
    private lateinit var id: TextView

    private lateinit var dmail: String
    private lateinit var did:String
    private lateinit var usID: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doctor)

        val show = findViewById<Button>(R.id.showinfodoc)
        show.setOnClickListener {
            database = Firebase.database.reference

            mail = findViewById(R.id.app_user)
            id = findViewById(R.id.appenterdocid)

            dmail = mail.text.toString().trim()
            did = id.text.toString().trim()
            usID = dmail?.subSequence(0, 5).toString()


            database = FirebaseDatabase.getInstance().reference
            val postListener = object : ValueEventListener {

                @SuppressLint("SetTextI18n")
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    // Get Post object and use the values to update the UI
                    for (i in dataSnapshot.children) {
                        val datas=dataSnapshot as String
                        val apid = dataSnapshot.child("appointments/$usID/$did/appointmentID").value
                        val apdate =
                            dataSnapshot.child("appointments/$usID/$did/appointmentdate").value
                        val apdescription =
                            dataSnapshot.child("appointments/$usID/$did/appointmentdescription").value
                        val aphumor = dataSnapshot.child("appointments/$usID/$did/humor").value
                        val apmedications =
                            dataSnapshot.child("appointments/$usID/$did/medications").value

                        val text = findViewById<TextView>(R.id.calendarshowpat)
                        //text.text =
                          //  "ID: $apid \n DATE: $apdate \n DESCRIPTION: $apdescription \n HUMOR: $aphumor \n PRESCRIBED MEDICATIONS: $apmedications \n"
                         text.setText(datas)
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
        //goback
        val back = findViewById<Button>(R.id.backDoc)
        back.setOnClickListener {
            val goback = Intent(applicationContext, MainActivity::class.java)
            startActivity(goback)
        }
    }
}