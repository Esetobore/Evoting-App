package com.example.evoting

import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.*

// serves as the class for the repository connected to the firebase instance
class UserRepository {


    private val databaseReference : DatabaseReference = FirebaseDatabase.getInstance().getReference("Voters")
    fun loadUsers(userList: MutableLiveData<List<User>>){
        databaseReference.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                try {
                    val MvoterList : List<User> = snapshot.children.map { dataSnapshot->
                        dataSnapshot.getValue(User::class.java)!!
                    }
                    userList.postValue(MvoterList)
                }
                catch (e :Exception){

                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }


        })
    }
     @Volatile private var INSTANCE : UserRepository?= null
    fun getInstance(): UserRepository {
        return INSTANCE ?: synchronized(this){
                val instance = UserRepository()
                INSTANCE = instance
                instance
            }
    }
}