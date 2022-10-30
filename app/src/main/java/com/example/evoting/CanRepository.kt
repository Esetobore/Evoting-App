package com.example.evoting

import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.*

// serves as the class for the repository connected to the firebase instance
class CanRepository {


    private val databaseReference : DatabaseReference = FirebaseDatabase.getInstance().getReference("Candit")
    fun loadUsers(userList: MutableLiveData<List<User>>){
        databaseReference.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                try {
                    val CanList : List<User> = snapshot.children.map { dataSnapshot->
                        dataSnapshot.getValue(User::class.java)!!
                    }
                    userList.postValue(CanList)
                }
                catch (e :Exception){

                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }


        })
    }
     @Volatile private var INSTANCE : CanRepository?= null
    fun getInstance(): CanRepository {
        return INSTANCE ?: synchronized(this){
                val instance = CanRepository()
                INSTANCE = instance
                instance
            }
    }
}