package com.example.evoting

import android.content.Context
import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.storage.FirebaseStorage
import java.io.File

class MainAdapter : RecyclerView.Adapter<MainAdapter.MyViewHolder>() {
private lateinit var context: Context
    class MyViewHolder(itemView: View ): RecyclerView.ViewHolder(itemView){
        val firstName = itemView.findViewById<TextView>(R.id.TxtFirstName)
        val lastName = itemView.findViewById<TextView>(R.id.TxtLastName)
        val phoneNum = itemView.findViewById<TextView>(R.id.TxtphoneNumber)
        val gender = itemView.findViewById<TextView>(R.id.vGender)
        val dateOfBirth = itemView.findViewById<TextView>(R.id.vDob)
        val profileImg = itemView.findViewById<ImageView>(R.id.profileImg)
    }

    private val voterlist = ArrayList<User>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.info,
            parent,false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = voterlist[position]
        holder.firstName.text = currentItem.fName
        holder.lastName.text = currentItem.lName
        holder.phoneNum.text = currentItem.phoneNumber
        holder.gender.text = currentItem.gender
        holder.dateOfBirth.text = currentItem.dob1
        Glide.with(holder.itemView).load(currentItem).into(holder.profileImg)
    }

    override fun getItemCount(): Int {
       return voterlist.size
    }
    fun updateuserList(voterList: List<User>){
        this.context = context
        this.voterlist.clear()
        this.voterlist.addAll(voterList)
        notifyDataSetChanged()
    }
}