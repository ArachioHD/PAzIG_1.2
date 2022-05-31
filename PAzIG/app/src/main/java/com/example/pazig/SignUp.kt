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

class SignUp : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        firebaseAuth = FirebaseAuth.getInstance()

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please wait...")
        progressDialog.setCanceledOnTouchOutside(false)
        val newPat: Button =findViewById(R.id.signup2)
        newPat.setOnClickListener {
            validationData()
        }
    }
    private var signupname=""
    private var signupemail=""
    private var signuppassword=""
    private var signuppassword2=""

    private fun validationData() {
        val email1: EditText =findViewById(R.id.mail2)
        val pass1: EditText =findViewById(R.id.password2)
        val pass2: EditText =findViewById(R.id.password3)

        signupemail=email1.text.toString().trim()
        signuppassword=pass1.text.toString().trim()
        signuppassword2=pass2.text.toString().trim()


        if(!Patterns.EMAIL_ADDRESS.matcher(signupemail).matches()){
            Toast.makeText(this, "Invalid mail", Toast.LENGTH_SHORT).show()
        }
        else if(signuppassword.isEmpty()){
            Toast.makeText(this, "Enter your password", Toast.LENGTH_SHORT).show()
        }
        else if(signuppassword2.isEmpty()){
            Toast.makeText(this, "Enter your password", Toast.LENGTH_SHORT).show()
        }
        else if(signuppassword != signuppassword2){
            Toast.makeText(this, "Passwords don't match", Toast.LENGTH_SHORT).show()

        }
        else{
            createUserAccount()
        }

    }
    private fun createUserAccount(){
        progressDialog.setMessage("Account creating...")
        progressDialog.show()

        firebaseAuth.createUserWithEmailAndPassword(signupemail,signuppassword)
            .addOnSuccessListener {
                progressDialog.dismiss()
                updateUserInfo()
            }
            .addOnFailureListener{e ->
                progressDialog.dismiss()
                Toast.makeText(this, "Account creation failed ${e.message}", Toast.LENGTH_SHORT).show()

            }
    }
    private fun updateUserInfo(){
        progressDialog.setMessage("Saving user information...")
        val timestamp=System.currentTimeMillis()

        val uid=firebaseAuth.uid
        //
        val hashMap: HashMap<String, Any?> = HashMap()
        hashMap["uid"]=uid
        hashMap["email"]=signupemail
        hashMap["userType"]="patient"
        hashMap["timestamp"]=timestamp
        val ref= FirebaseDatabase.getInstance().getReference("Users")
        ref.child(uid!!)
            .setValue(hashMap)
            .addOnSuccessListener {
                Toast.makeText(this, "Account created", Toast.LENGTH_SHORT).show()
                val signinP= Intent(this, Patient::class.java)
                startActivity(signinP)


            }
            .addOnFailureListener{e->
                progressDialog.dismiss()
                Toast.makeText(this, "User information could not be saved ${e.message}", Toast.LENGTH_SHORT).show()

            }
        //goback
        val back = findViewById<Button>(R.id.backSignUp)
        back.setOnClickListener {
            val goback = Intent(applicationContext, MainActivity::class.java)
            startActivity(goback)
        }
    }
}