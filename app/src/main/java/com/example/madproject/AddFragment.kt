package com.example.madproject

import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Email
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.madproject.databinding.FragmentAddBinding
import com.example.madproject.models.Services
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.util.Locale.Category
import kotlin.jvm.internal.Ref


class AddFragment : Fragment() {
    private var _binding: FragmentAddBinding? =null
    private val binding get() = _binding!!

    private lateinit var firebaseRef: DatabaseReference
    private lateinit var storageRef:StorageReference

    private var uri:Uri?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddBinding.inflate(inflater,container,false)
        firebaseRef = FirebaseDatabase.getInstance().getReference("services")
        storageRef = FirebaseStorage.getInstance().getReference("ServicesImages")

        binding.editPostAd.setOnClickListener{
            savedata()
        }

        val pickImage = registerForActivityResult(ActivityResultContracts.GetContent()){
            binding.image1.setImageURI(it)
            if (it !=null) {
                uri=it

            }
        }
        binding.addImage.setOnClickListener{
               pickImage.launch("image/*")
        }
        // Inflate the layout for this fragment
        return binding.root
    }
    private fun savedata(){
        val description = binding.editDescription.text.toString()
        val name =binding.editName.text.toString()
        val email = binding.editEmail.text.toString()
        val contact_Number=binding.editContact.toString()
        val location = binding.editLocation.text.toString()
        val category = binding.editCategory.text.toString()

        if (description.isEmpty())binding.editDescription.error= "write a description"
        if (name.isEmpty())binding.editName.error= "write a name"
        if (email .isEmpty())binding.editEmail.error= "write a email"
        if (contact_Number.isEmpty())binding.editContact.error= "write a number"
        if (location.isEmpty())binding.editLocation.error= "write a locatioc"
        if (category .isEmpty())binding.editCategory.error= "write a category"

        val servicesId = firebaseRef.push().key!!
       var services : Services

       uri?.let {
           storageRef.child(servicesId).putFile(it)
               .addOnSuccessListener {task->
                   task.metadata !!.reference !!.downloadUrl
                       .addOnSuccessListener{url->
                           Toast.makeText(context,"image stored Successfully",Toast.LENGTH_SHORT).show()
                              val imageUrl =url.toString()

                                 services=Services(servicesId,description,name,email,contact_Number,location,category,imageUrl)

                           firebaseRef.child(servicesId).setValue(services)
                               .addOnCompleteListener{
                                   Toast.makeText(context,"Data stored Successfully",Toast.LENGTH_SHORT).show()
                               }
                               .addOnFailureListener{
                                   Toast.makeText(context,"error ${it.message}",Toast.LENGTH_SHORT).show()
                               }


                       }
               }
       }






    }


}