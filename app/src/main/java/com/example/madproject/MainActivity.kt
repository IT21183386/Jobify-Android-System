package com.example.madproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.madproject.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityMainBinding.inflate(layoutInflater)


        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.textView.setOnClickListener {


            val intent = Intent(this, Login::class.java)
            startActivity(intent)

        }


        binding.button.setOnClickListener {


            val fname = binding.editTextTextPersonName.text.toString()
            val lname = binding.editTextTextPersonName2.text.toString()
            val phone = binding.editTextPhone.text.toString()


            val email = binding.editTextTextEmailAddress.text.toString()
            val Pass = binding.editTextTextPassword.text.toString()
            val ConcPass = binding.editTextTextPassword2.text.toString()


            if (email.isNotEmpty() && Pass.isNotEmpty() && ConcPass.isNotEmpty()) {
                if (Pass == ConcPass) {


                    firebaseAuth.createUserWithEmailAndPassword(email, Pass).addOnCompleteListener {
                        if (it.isSuccessful) {
                            val intent = Intent(this, Login::class.java)
                            startActivity(intent)

                        } else {
                            Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT)
                        }
                    }

                } else {
                    Toast.makeText(this, "Password not matching", Toast.LENGTH_SHORT)
                }


            } else {
                Toast.makeText(this, "Empty Fields", Toast.LENGTH_SHORT)
            }


        }
    }
}