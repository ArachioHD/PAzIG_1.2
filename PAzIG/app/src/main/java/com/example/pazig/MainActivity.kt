package com.example.pazig

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var progressDialog: ProgressDialog
    private lateinit var em: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        firebaseAuth = FirebaseAuth.getInstance()

            progressDialog = ProgressDialog(this)
            progressDialog.setTitle("Please wait...")
            progressDialog.setCanceledOnTouchOutside(false)
            //signup
            val patsignup: Button =findViewById(R.id.signup)
            patsignup.setOnClickListener{
                startActivity(Intent(this, SignUp::class.java))

            //signin
            }
            val patsignin: Button =findViewById(R.id.signin)
            patsignin.setOnClickListener{
                validateData()
            }
        }
        private var signinemail  = ""
        private var signinpassword = ""

        private fun validateData(){
            val email1: EditText =findViewById(R.id.mail)
            val pass1: EditText =findViewById(R.id.password)

            signinemail=email1.text.toString().trim()
            signinpassword=pass1.text.toString().trim()
            if(!Patterns.EMAIL_ADDRESS.matcher(signinemail).matches()){
                Toast.makeText(this, "Invalid email", Toast.LENGTH_SHORT).show()
            }
            else if (signinpassword.isEmpty()){
                Toast.makeText(this, "Enter your password", Toast.LENGTH_SHORT).show()
            }
            else if(signinemail.contentEquals("julimie273@student.polsl.pl")){
                loginUser2()
            }
            else{
                loginUser()
            }


        }
        private fun loginUser(){
            progressDialog.setMessage("Please wait...")
            progressDialog.show()

            firebaseAuth.signInWithEmailAndPassword(signinemail, signinpassword)
                .addOnSuccessListener {
                    Toast.makeText(this, "Successful login", Toast.LENGTH_SHORT).show()
                    val log= Intent(this, Patient::class.java)
                    log.putExtra("one", signinemail)
                    startActivity(log)
                }
                .addOnFailureListener{e->
                    Toast.makeText(this, "Login failed ${e.message}", Toast.LENGTH_SHORT).show()
                    progressDialog.dismiss()

                }
        }
    private fun loginUser2(){
        progressDialog.setMessage("Please wait...")
        progressDialog.show()

        firebaseAuth.signInWithEmailAndPassword(signinemail, signinpassword)
            .addOnSuccessListener {
                Toast.makeText(this, "Successful login", Toast.LENGTH_SHORT).show()
                val log= Intent(this, Doctor::class.java)
                log.putExtra("one2", signinemail)
                startActivity(log)
            }
            .addOnFailureListener{e->
                Toast.makeText(this, "Login failed ${e.message}", Toast.LENGTH_SHORT).show()
                progressDialog.dismiss()

            }
    }
}