package com.example.evoting

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore

@Suppress("UNREACHABLE_CODE")
class RegisterActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var fstore: FirebaseFirestore
    private lateinit var txtemail: EditText
    private lateinit var txtpass: EditText
    private lateinit var progressBar: ProgressBar
    private lateinit var canBox : CheckBox
    private lateinit var voterBox: CheckBox
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        //firebase
        auth = FirebaseAuth.getInstance()
        fstore = FirebaseFirestore.getInstance()
        FirebaseApp.initializeApp(this)
        //buttons
        val loginIntent = findViewById<TextView>(R.id.txtGoToLoginPage)
        val registerButton = findViewById<Button>(R.id.btnregister)
        //views
        txtemail = findViewById(R.id.email)
        txtpass = findViewById(R.id.passwd)
        progressBar = findViewById(R.id.progressbarload)
        canBox = findViewById(R.id.candidatecheckbox)
        voterBox = findViewById(R.id.votercheckbox)

        registerButton.setOnClickListener {
            registerUser()
        }
        loginIntent.setOnClickListener {
          onBackPressed()
        }


    }

    private fun registerUser() {
        progressBar.visibility = View.VISIBLE
        // Validations for input email and password
        val email = txtemail.text.toString()
        val pass = txtpass.text.toString()
        if (email.isEmpty()) {
            Toast.makeText(
                this@RegisterActivity,
                "Please enter email!!", Toast.LENGTH_LONG
            ).show()
            progressBar.visibility = View.GONE
        }
        if (pass.isEmpty()) {
            Toast.makeText(
                this@RegisterActivity,
                "Please enter password!!", Toast.LENGTH_LONG
            ).show()
            progressBar.visibility = View.GONE
        }
        if (email.isEmpty() && pass.isEmpty()){
            Toast.makeText(
                this@RegisterActivity,
                "Please enter Password and Email", Toast.LENGTH_LONG
            ).show()
            progressBar.visibility = View.GONE
        }

        if (!(voterBox.isChecked || canBox.isChecked)){
            Toast.makeText(this,"Select Account Type",Toast.LENGTH_SHORT).show()
        }


        // create new user or register new user
        auth.createUserWithEmailAndPassword(email,pass)
            .addOnCompleteListener(OnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Registration successfully", Toast.LENGTH_LONG).show()
                    // hide the progress bar
                    val firebaseUser = auth.currentUser
                    progressBar.visibility = View.GONE
                    val df: DocumentReference = fstore.collection("Users").document(firebaseUser!!.uid)
                    val userInfo: MutableMap<String, String> = HashMap()
                    if (canBox.isChecked){
                        userInfo["IsCan"] = "1"
                    }
                    if (voterBox.isChecked){
                        userInfo["IsVoter"] = "1"
                    }
                    df.set(userInfo)
                    if (voterBox.isChecked){
                        val voterActivity = Intent(this,MainActivity::class.java)
                        startActivity(voterActivity)
                        finish()
                    }
                    if (canBox.isChecked){
                        val canActivity = Intent(this,CandidateMainActivity::class.java)
                        startActivity(canActivity)
                        finish()
                    }
                }
                finish()
            }).addOnFailureListener {
                progressBar.visibility = View.GONE
                Toast.makeText(this, it.message,Toast.LENGTH_LONG).show()
            }
    }
    }

