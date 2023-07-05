package com.example.madproject

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.madproject.adapter.RvServicesAdapter
import com.example.madproject.databinding.FragmentHomeBinding
import com.example.madproject.models.Services
import com.google.firebase.FirebaseApp
import com.google.firebase.database.*
import java.sql.Ref



class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var contactList: ArrayList<Services>
    private lateinit var firebaseRef: DatabaseReference


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        binding.AdSrv.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_addFragment)
        }
        firebaseRef = FirebaseDatabase.getInstance().getReference("services")
        contactList = arrayListOf()

        fetchData()
        binding.rvServices.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this.context)
        }


        // Inflate the layout for this fragment
        return binding.root
    }

    private fun fetchData() {
        firebaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                contactList.clear()
                if (snapshot.exists()) {
                    for (serviceSnap in snapshot.children) {
                        val services = serviceSnap.getValue(Services::class.java)
                        contactList.add(services!!)
                    }

                }
                val rvServicesAdapter = RvServicesAdapter(contactList)
                binding.rvServices.adapter = rvServicesAdapter

            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "error:$error", Toast.LENGTH_SHORT).show()
            }


        })


    }
}
