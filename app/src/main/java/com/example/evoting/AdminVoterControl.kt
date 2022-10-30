package com.example.evoting

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import java.io.File


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private lateinit var viewModel: UserViewModel
private lateinit var voterRecyclerView: RecyclerView
private lateinit var adapter: MainAdapter

class AdminVoterControl : Fragment() {
    // TODO: Rename and change types of parameters
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
        return inflater.inflate(R.layout.fragment_admin_voter_control, container, false)
    }
    companion object {
        @JvmStatic
        fun newInstance() =
            AdminVoterControl().apply {
                arguments = Bundle().apply {
                    arguments = Bundle().apply {}
                }
            }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        voterRecyclerView = view.findViewById(R.id.voterList)
        voterRecyclerView.layoutManager = LinearLayoutManager(context)
        voterRecyclerView.setHasFixedSize(true)
        adapter = MainAdapter()
        voterRecyclerView.adapter = adapter
        viewModel = ViewModelProvider(this)[UserViewModel::class.java]
        viewModel.allusers.observe(viewLifecycleOwner, Observer {
            adapter.updateuserList(it)
        })


    }

}