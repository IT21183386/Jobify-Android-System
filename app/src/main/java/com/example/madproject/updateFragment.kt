package com.example.madproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.madproject.databinding.FragmentUpdateBinding
import com.example.madproject.models.Services
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.sql.Ref


class updateFragment : Fragment() {

    private var _binding : FragmentUpdateBinding? = null
    private val binding get() = _binding!!

    private val args:updateFragmentArgs by navArgs()

    private lateinit var firebaseRef: DatabaseReference


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding=FragmentUpdateBinding.inflate(inflater,container,false)

        firebaseRef = FirebaseDatabase.getInstance().getReference( "services")

        binding.apply {
            updateDescription.setText(args.description)
            updateName.setText((args.name))
            updateEmail.setText(args.email)
            updateContact.setText(args.contactNumber)
            updateLocation.setText(args.location)
            updateCategory.setText(args.category)
            updatePostAd.setOnClickListener {
                updateData()

                findNavController().navigate(R.id.action_updateFragment_to_homeFragment)
            }
        }

        return binding.root
    }

    private fun updateData() {
        val description=binding.updateDescription.text.toString()
        val name=binding.updateName.text.toString()
        val email=binding.updateEmail.text.toString()
        val contactNumber=binding.updateContact.text.toString()
        val location=binding.updateLocation.text.toString()
        val category=binding.updateCategory.text.toString()
        val services=Services(args.id,description,name,email,contactNumber,location,category)

        firebaseRef.child(args.id).setValue(services)
            .addOnCompleteListener{
                Toast.makeText(context,"Data updated Successfully", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener{
                Toast.makeText(context,"error ${it.message}", Toast.LENGTH_SHORT).show()
            }

    }


}