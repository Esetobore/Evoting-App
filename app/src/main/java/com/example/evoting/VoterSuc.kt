package com.example.evoting

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.storage.FirebaseStorage
import java.io.File

class VoterSuc : AppCompatActivity() {
    private lateinit var viewModel : CanViewModel
    private lateinit var canVoteRecyclerView: RecyclerView
    private lateinit var canAdaptVote: CanAdapterVote
    private lateinit var gotoProfile: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_voter_succ)
        gotoProfile = findViewById(R.id.btn_profile)
        canVoteRecyclerView = findViewById(R.id.CanList)
        canVoteRecyclerView.layoutManager = LinearLayoutManager(this)
        canVoteRecyclerView.setHasFixedSize(true)
        canAdaptVote = CanAdapterVote()
        canVoteRecyclerView.adapter = canAdaptVote
        viewModel = ViewModelProvider(this)[CanViewModel::class.java]
        viewModel.allusers.observe(this, Observer{
           canAdaptVote.updateUserList(it)
       })

        gotoProfile.setOnClickListener {
            val intent = Intent(this,Profile::class.java)
            startActivity(intent)
        }
    }

}