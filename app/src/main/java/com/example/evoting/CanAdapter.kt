package com.example.evoting

import android.content.Context
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.storage.FirebaseStorage
import java.io.File

class CanAdapter : RecyclerView.Adapter<CanAdapter.MyViewHolder>() {
private lateinit var context: Context


    private val canList = ArrayList<User>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.info,
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

    class MyViewHolder(itemView: View ): RecyclerView.ViewHolder(itemView){
        val firstName: TextView = itemView.findViewById<TextView>(R.id.TxtFirstName)
        val lastName: TextView = itemView.findViewById<TextView>(R.id.TxtLastName)
        val phoneNum: TextView = itemView.findViewById<TextView>(R.id.TxtphoneNumber)
        val gender: TextView = itemView.findViewById<TextView>(R.id.vGender)
        val dateOfBirth: TextView = itemView.findViewById<TextView>(R.id.vDob)
        val profileImg: ImageView = itemView.findViewById<ImageView>(R.id.profileImg)

    }

}