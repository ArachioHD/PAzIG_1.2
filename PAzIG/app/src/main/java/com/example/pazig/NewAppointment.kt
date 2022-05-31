package com.example.pazig

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import java.io.ByteArrayOutputStream
import java.util.*


class NewAppointment : AppCompatActivity() {

    private lateinit var database: DatabaseReference
    private lateinit var storageReference: StorageReference

    private lateinit var id: TextView
    private lateinit var date: TextView
    private lateinit var description: TextView
    private lateinit var humor: TextView
    private lateinit var medications: TextView

    private lateinit var apid:String
    private lateinit var apdate: String
    private lateinit var apdescription: String
    private lateinit var aphumor: String
    private lateinit var apmedications: String

    lateinit var imageView: ImageView
    lateinit var button: Button
    private val pickImage = 100
    private var imageUri: Uri? = null

    @SuppressLint("WrongThread")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_appointment)

        imageView = findViewById(R.id.imageView)
        button = findViewById(R.id.buttonLoadPicture)
        button.setOnClickListener {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, pickImage)
        }

        val i1 = intent
        val mailid = i1.getStringExtra("atwo")
        val appId = mailid?.subSequence(0, 5).toString()

        database = Firebase.database.reference
        fun writeNewApp(appId: String, appointmentID: String, appointmentdate: String, appointmentdescription: String, humor: String, medications: String ) {
            val appointment = Appointment(
                appointmentID,
                appointmentdate,
                appointmentdescription,
                humor,
                medications
            )
            val uid = FirebaseAuth.getInstance().currentUser!!.uid
            //
            val hashMap: HashMap<String, Any?> = HashMap()
            hashMap["appointment ID"]=appointmentID
            hashMap["appointment date"]=appointmentdate
            hashMap["appointment description"]=appointmentdescription
            hashMap["humor"]=humor
            hashMap["medications"]=medications
           val ref= FirebaseDatabase.getInstance().getReference("appointments")
            ref.child(uid!!).child(appointmentID)
                .setValue(hashMap)
                .addOnSuccessListener {
                    //Toast.makeText(this, "Good", Toast.LENGTH_SHORT).show()
                    //val uid = FirebaseAuth.getInstance().currentUser!!.uid
                    //database.push().setValue(appointment)

                }
                .addOnFailureListener{e->
                   //Toast.makeText(this, "Jebać" , Toast.LENGTH_SHORT).show()

                }
        }
        fun uploadImageToFirebase(fileUri: Uri) {
            if (fileUri != null) {
                val fileName = UUID.randomUUID().toString() +".jpg"

                val refStorage = FirebaseStorage.getInstance().reference.child("images/$fileName")

                refStorage.putFile(fileUri)
                    .addOnSuccessListener(
                        OnSuccessListener<UploadTask.TaskSnapshot> { taskSnapshot ->
                            taskSnapshot.storage.downloadUrl.addOnSuccessListener {
                                val imageUrl = it.toString()
                            }
                        })

                    ?.addOnFailureListener(OnFailureListener { e ->
                        print(e.message)
                    })
            }
        }

        val saveData: Button =findViewById(R.id.saveappNewApp)
        saveData.setOnClickListener{
            id=findViewById(R.id.app_idNewApp)
            date=findViewById(R.id.appdateNewApp)
            description=findViewById(R.id.app_descriptionNewApp)
            humor=findViewById(R.id.app_humorNewApp)
            medications=findViewById(R.id.app_medicationsNewApp)

            apid=id.text.toString().trim()
            apdate=date.text.toString().trim()
            apdescription=description.text.toString().trim()
            aphumor=humor.text.toString().trim()
            apmedications=medications.text.toString().trim()

            writeNewApp(appId, apid, apdate, apdescription, aphumor, apmedications)
            uploadImageToFirebase(imageUri!!)
            //go to app calendar
            val gotocalendar= Intent(applicationContext, Calendar::class.java)
            gotocalendar.putExtra("id", appId)
            startActivity(gotocalendar)
        }

        //goback
        val back = findViewById<Button>(R.id.backNewApp)
        back.setOnClickListener {
            val goback = Intent(applicationContext, Appointments::class.java)
            startActivity(goback)
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == pickImage) {
            imageUri = data?.data
            imageView.setImageURI(imageUri)
        }
    }

}