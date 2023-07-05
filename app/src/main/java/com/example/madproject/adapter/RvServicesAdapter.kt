package com.example.madproject.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.madproject.HomeFragmentDirections
import com.example.madproject.databinding.RvServicesItemBinding
import com.example.madproject.models.Services
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso

class RvServicesAdapter(private val contactList: java.util.ArrayList<Services>):RecyclerView.Adapter<RvServicesAdapter.viewHolder>() {
    class viewHolder(val binding:RvServicesItemBinding):RecyclerView.ViewHolder (binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        return viewHolder(RvServicesItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
       return contactList.size
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onBindViewHolder(holder: viewHolder, position: Int) {
       val currentItem =contactList[position]
        holder.apply {
            binding.apply {

               editDd.text=currentItem.description
                editCompany.text=currentItem.name
                editAdress.text=currentItem.location
                Picasso.get().load(currentItem.imgUri).into((imageHome))
                //Update
                rvContainer.setOnClickListener {

                    val action=HomeFragmentDirections.actionHomeFragmentToUpdateFragment(
                        currentItem.id.toString(),
                        currentItem.description.toString(),
                        currentItem.name.toString(),
                        currentItem.email.toString(),
                        currentItem.contact_Number.toString(),
                        currentItem.location.toString(),
                        currentItem.Category.toString(),
                      )
                      findNavController(holder.itemView).navigate(action)
                  }
                //DeleteFunction
                rvContainer.setOnLongClickListener {

                    MaterialAlertDialogBuilder(holder.itemView.context)
                        .setTitle("Delete item permanently")
                        .setMessage("Are you sure you want to delete this item?")
                        .setPositiveButton( "Yes"){_,_ ->

                            val firebaseRef=FirebaseDatabase.getInstance().getReference( "services")
                            firebaseRef.child(currentItem.id.toString()).removeValue()
                                .addOnSuccessListener {
                                    Toast.makeText(holder.itemView.context,"Item removed successfully",Toast.LENGTH_SHORT).show()
                                }
                                .addOnFailureListener{error ->
                                    Toast.makeText(holder.itemView.context,"error ${error.message}",Toast.LENGTH_SHORT).show()
                                }

                        }
                                .setNegativeButton( "No"){_,_ ->
                                    Toast.makeText(holder.itemView.context,"canceled",Toast.LENGTH_SHORT).show()

                        }
                                .show()

                    return@setOnLongClickListener true

                }









            }
        }


    }
}