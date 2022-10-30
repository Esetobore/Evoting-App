package com.example.evoting

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import java.io.File

class CanAdapterVote : RecyclerView.Adapter<CanAdapterVote.MyViewHolder>() {
private lateinit var context: Context
private lateinit var voterRef: DatabaseReference
    private lateinit var url : List<String>
private var testclick= false
private var count = 0

    private val canList = ArrayList<User>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.can,
            parent,false)
        return MyViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = canList.get(position)
        holder.firstName.text = currentItem.fName
        holder.lastName.text = currentItem.lName
        holder.phoneNum.text = currentItem.phoneNumber
        holder.gender.text = currentItem.gender
        holder.dateOfBirth.text = currentItem.dob1
        Glide.with(holder.itemView).load(currentItem).into(holder.profileImg)
        getPofileImage()
        val firebaseUser = FirebaseAuth.getInstance().currentUser
        val userid = firebaseUser!!.uid



    }
    override fun getItemCount(): Int {
       return canList.size
    }
    fun updateUserList(voterList: List<User>){
        this.context = context
        this.canList.clear()
        this.canList.addAll(voterList)
        notifyDataSetChanged()
    }
    fun getPofileImage(){
        imageRef = FirebaseStorage.getInstance().reference.child("UserImg/$uid.jpg")
        val localFile = File.createTempFile("ElectionImg","jpg")
        imageRef.getFile(localFile)
    }

    class MyViewHolder(itemView: View ): RecyclerView.ViewHolder(itemView) {
        private lateinit var voterRef: DatabaseReference
        val firstName: TextView = itemView.findViewById<TextView>(R.id.TxtFirstName)
        val lastName: TextView = itemView.findViewById<TextView>(R.id.TxtLastName)
        val phoneNum: TextView = itemView.findViewById<TextView>(R.id.TxtphoneNumber)
        val gender: TextView = itemView.findViewById<TextView>(R.id.vGender)
        val dateOfBirth: TextView = itemView.findViewById<TextView>(R.id.vDob)
        val profileImg: ImageView = itemView.findViewById<ImageView>(R.id.profileImg)
        val votecheck: CheckBox = itemView.findViewById(R.id.votecheckbox)
        val voteNum: TextView = itemView.findViewById(R.id.vote_text)

    fun getlikebuttonstatus(userid: String) {
            voterRef = FirebaseDatabase.getInstance().getReference("Votes")
            voterRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.hasChild(userid)) {
                        val likecount = snapshot.child(lastName.toString()).childrenCount.toInt()
                        voteNum.text = "$likecount Votes"

                    } else {
                        val likecount = snapshot.child(lastName.toString()).childrenCount.toInt()
                        voteNum.text = "$likecount Votes"

                    }
                }

                override fun onCancelled(error: DatabaseError) {}
            })
        }

    }
//    private fun nrLikes(likes: TextView) {
//        val reference = FirebaseDatabase.getInstance().reference.child("Likes")
//        reference.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                likes.text = dataSnapshot.childrenCount.toString() + " likes"
//            }
//
//            override fun onCancelled(databaseError: DatabaseError) {}
//        })
//    }
//
//    private fun isLiked(postid: String, imageView: ImageView) {
//        val firebaseUser = FirebaseAuth.getInstance().currentUser
//        val reference = FirebaseDatabase.getInstance().reference
//            .child("Likes").child(postid)
//        reference.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                if (dataSnapshot.child(firebaseUser!!.uid).exists()) {
//                    imageView.setImageResource(R.drawable.ic_vote)
//                    imageView.tag = "liked"
//                } else {
//                    imageView.setImageResource(R.drawable.ic_vote)
//                    imageView.tag = "like"
//                }
//            }
//
//            override fun onCancelled(databaseError: DatabaseError) {}
//        })
//    }
}