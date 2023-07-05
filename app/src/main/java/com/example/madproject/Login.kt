package com.example.madproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.madproject.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class Login : AppCompatActivity() {


    private lateinit var binding: ActivityLoginBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)




        setContentView(binding.root)



        firebaseAuth = FirebaseAuth.getInstance()



        binding.textView3.setOnClickListener {


            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

            binding.button3.setOnClickListener {


                val email = binding.editTextTextEmailAddress2.text.toString()
                val Pass = binding.editTextTextPassword3.text.toString()



                if (email.isNotEmpty() && Pass.isNotEmpty()) {


                    firebaseAuth.signInWithEmailAndPassword(email, Pass).addOnCompleteListener {
                        if (it.isSuccessful) {
                            val intent = Intent(this, userAccount::class.java)
                            startActivity(intent)

                        } else {
                            Toast.makeText(this, "not working", Toast.LENGTH_SHORT)
                        }
                    }


                } else {
                    Toast.makeText(this, "Empty Fields", Toast.LENGTH_SHORT)
                }


            }



    }
}