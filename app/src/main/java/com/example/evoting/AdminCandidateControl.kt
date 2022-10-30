package com.example.evoting

import android.annotation.SuppressLint
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.File

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


private lateinit var viewModel: CanViewModel
private lateinit var CanRecyclerView: RecyclerView
@SuppressLint("StaticFieldLeak")
lateinit var Canadapt: CanAdapter
lateinit var imageRef : StorageReference
private lateinit var auth : FirebaseAuth
private lateinit var databaseReference: DatabaseReference
lateinit var uid : String
class AdminCandidateControl : Fragment() {

    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_admin_candidate_control, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AdminCandidateControl.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            AdminCandidateControl().apply {
                arguments = Bundle().apply {
                    arguments = Bundle().apply {}

                }
            }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        CanRecyclerView = view.findViewById(R.id.CanList)
        CanRecyclerView.layoutManager = LinearLayoutManager(context)
        CanRecyclerView.setHasFixedSize(true)
        Canadapt = CanAdapter()
        CanRecyclerView.adapter = Canadapt
        viewModel = ViewModelProvider(this)[CanViewModel::class.java]
        viewModel.allusers.observe(viewLifecycleOwner, Observer {
            Canadapt.updateUserList(it)
        })
        auth = FirebaseAuth.getInstance()
        uid = auth.currentUser?.uid.toString()


    }

}