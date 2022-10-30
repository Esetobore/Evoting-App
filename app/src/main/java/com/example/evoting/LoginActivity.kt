package com.example.evoting

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore

class LoginActivity : AppCompatActivity() {
    private lateinit var progressBarLog: ProgressBar
    private lateinit var auth: FirebaseAuth
    private lateinit var fstore : FirebaseFirestore
    private lateinit var txtemail : EditText
    private lateinit var txtpass : EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        auth = FirebaseAuth.getInstance()
        fstore = FirebaseFirestore.getInstance()
        val registerIntent = findViewById<TextView>(R.id.txtGoToRegisterPage)
        val loginV = findViewById<Button>(R.id.loginV)
        val loginECA = findViewById<Button>(R.id.loginECA)
        progressBarLog = findViewById(R.id.progressBarlog)
        txtemail = findViewById(R.id.emailLogin)
        txtpass = findViewById(R.id.passwordLogin)

        loginV.setOnClickListener {
            loginUser()
        }

        loginECA.setOnClickListener {
            progressBarLog.visibility = View.VISIBLE
            val email = txtemail.text.toString()
            val pass = txtpass.text.toString()
            if (email == "admin@org" && pass == "Eatmyshorts"){
                Toast.makeText(this,
                    "Succesffuly logged in Admin",Toast.LENGTH_SHORT)
                    .show()
                val adminActivity = Intent(this,AdminMainActivity::class.java)
                startActivity(adminActivity)
            }
            else{
                Toast.makeText(this,"Login Credentials don't match any Admin",Toast.LENGTH_SHORT).show()
                progressBarLog.visibility = View.INVISIBLE
            }

        }
        registerIntent.setOnClickListener {
            val loginIntent = Intent(this,RegisterActivity::class.java)
            startActivity(loginIntent)
        }
    }
    private fun loginUser() {
        progressBarLog.visibility = View.VISIBLE
        // Validations for input email and password
        val email = txtemail.text.toString()
        val pass = txtpass.text.toString()
        if (email.isEmpty()) {
            Toast.makeText(
                this@LoginActivity,
                "Please enter email!!", Toast.LENGTH_LONG
            ).show()
            progressBarLog.visibility = View.GONE
        }
        if (pass.isEmpty()) {
            Toast.makeText(
                this@LoginActivity,
                "Please enter password!!", Toast.LENGTH_LONG
            ).show()
            progressBarLog.visibility = View.GONE
        }
        if (email.isEmpty() && pass.isEmpty()){
            Toast.makeText(
                this@LoginActivity,
                "Please enter Password and Email", Toast.LENGTH_LONG
            ).show()
            progressBarLog.visibility = View.GONE
        }

        // create new user or register new user
        auth.signInWithEmailAndPassword(email,pass)
            .addOnCompleteListener (OnCompleteListener { task->
                if (task.isSuccessful){
                    Toast.makeText(this,"Login successfully",Toast.LENGTH_LONG).show()
                    // hide the progress bar
                    progressBarLog.visibility = View.GONE
                    val currentUser = FirebaseAuth.getInstance().currentUser
                    val df: DocumentReference = fstore.collection("Users").document(currentUser!!.uid)
                        df.get().addOnSuccessListener {

                            if (it.getString("IsVoter") != null){
                                val voterActivity = Intent(this,MainActivity::class.java)
                                startActivity(voterActivity)
                                finish()
                            }
                            if (it.getString("IsCan") != null){
                                val canActivity = Intent(this,CandidateMainActivity::class.java)
                                startActivity(canActivity)
                                finish()
                            }
                        }

                }
                else {
                    // Registration failed
                    Toast.makeText(
                        applicationContext, "Login failed!!" + " Please try again later",
                        Toast.LENGTH_LONG).show()
                    // hide the progress bar
                    progressBarLog.visibility = View.GONE
                }
            }).addOnFailureListener {
                progressBarLog.visibility = View.GONE
                Toast.makeText(this, it.message,Toast.LENGTH_LONG).show()
            }


    }
    
 

}



