package com.example.evoting

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.cardview.widget.CardView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class CandidateMainActivity : AppCompatActivity() {
    private lateinit var firstname: EditText
    private lateinit var lastname: EditText
    private lateinit var phonenum: EditText
    private lateinit var male: RadioButton
    private lateinit var female: RadioButton
    private lateinit var others: RadioButton
    private lateinit var dob : DatePicker
    private lateinit var database : DatabaseReference
    private lateinit var storage: StorageReference
    private lateinit var linearLayout: CardView
    private lateinit var image : Button
    private lateinit var submit : Button
    private lateinit var imageUri : Uri
    private lateinit var auth : FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_candidate_main)
        firstname = findViewById(R.id.firstName)
        lastname = findViewById(R.id.lastName)
        phonenum = findViewById(R.id.phoneNumber)
        male = findViewById(R.id.male)
        female = findViewById(R.id.female)
        others = findViewById(R.id.others)
        dob = findViewById(R.id.datePicker)
        image = findViewById(R.id.chooseFileBtn)
        submit = findViewById(R.id.submitBtn)
        linearLayout = findViewById(R.id.loading)
        val uid = auth.currentUser?.uid
        database = FirebaseDatabase.getInstance().getReference("Candit")
        image.setOnClickListener {
            selectimage()
        }


        submit.setOnClickListener {
            uploadImage()
            val fname = firstname.text.toString()
            val lname = lastname.text.toString()
            val phonum = phonenum.text.toString()
            var gender: String? = null
            gender = if (male.isChecked) "male"
            else if (female.isChecked) "female"
            else "other"
            val day: Int = dob.dayOfMonth
            val month: Int = dob.month
            val year: Int = dob.year
            val dob = "$day/$month/$year"
            if (fname == ""){
                Toast.makeText(this,"First name cant be empty", Toast.LENGTH_LONG).show()
            }
            else if (lname == ""){
                Toast.makeText(this,"Last name cant be empty", Toast.LENGTH_LONG).show()
            }
            else if (phonum.length !=11){
                Toast.makeText(this,"phone number entered is invalid", Toast.LENGTH_LONG).show()
            }
            else{
                val User = User(fname,lname,dob,gender,"false",phonum,"0")
                if (uid != null) {
                    database.child(uid).setValue(User).addOnSuccessListener {
                        Toast.makeText(this,"Successfully Registered", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this, VoterSuc::class.java))
                    }.addOnFailureListener {
                        Toast.makeText(this,"Registration Failed$it", Toast.LENGTH_SHORT).show()
                    }
                }
            }

        }
    }

    private fun uploadImage() {
        linearLayout.visibility = View.VISIBLE
        storage = FirebaseStorage.getInstance().getReference("UserImg/"+auth.currentUser?.uid+".jpg")
        storage.putFile(imageUri).
        addOnSuccessListener {
            Toast.makeText(this,"Image Uploaded", Toast.LENGTH_SHORT).show()
            linearLayout.visibility = View.GONE
        }.addOnFailureListener {
            Toast.makeText(this,"Upload Failed", Toast.LENGTH_LONG).show()
            linearLayout.visibility = View.GONE
        }
    }

    private fun selectimage() {
        val intent = Intent()
        intent.type = "images/*"
        intent.action = Intent.ACTION_GET_CONTENT

        startActivityForResult(intent,100)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100 && resultCode == RESULT_OK){
            imageUri = data?.data!!
        }
    }
}