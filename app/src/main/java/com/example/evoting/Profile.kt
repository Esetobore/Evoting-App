package com.example.evoting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class Profile : AppCompatActivity() {
    private lateinit var databaseReference: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private lateinit var uid : String
    private lateinit var firstName: TextView
    private lateinit var lastName: TextView
    private lateinit var dateOfBirth: TextView
    private lateinit var gender : TextView
    private lateinit var phoneNumber: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        databaseReference = FirebaseDatabase.getInstance().getReference("Voters")
        databaseReference.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
               auth = FirebaseAuth.getInstance()
                uid = auth.currentUser.toString()

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

    }
}