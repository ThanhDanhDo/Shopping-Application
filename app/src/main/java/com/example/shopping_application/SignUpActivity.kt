package com.example.shopping_application

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.shopping_application.databinding.ActivitySignUpBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import kotlin.math.sign

private lateinit var binding: ActivitySignUpBinding
class SignUpActivity : AppCompatActivity() {
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //khởi tạo database firebase
        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase.reference.child("users")

        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.btnCreateAccount.setOnClickListener {
            val signupUsername = binding.edtUsername.text.toString()
            val signupPassword = binding.edtPassword.text.toString()
            val signupConfirmPassword = binding.edtConfirmPassword.text.toString()

            when {
                signupUsername.isEmpty() -> binding.edtUsername.error = "Please enter Username"
                signupPassword.isEmpty() -> binding.edtPassword.error = "Please enter Password"
                signupConfirmPassword.isEmpty() -> binding.edtConfirmPassword.error = "Please enter Confirm Password"
                signupPassword != signupConfirmPassword -> binding.edtConfirmPassword.error = "Password and Confirm Password are different"
                else -> signupUser(signupUsername, signupPassword)
            }
        }
    }

    private fun signupUser(username: String, password: String) {
        databaseReference.orderByChild("username").equalTo(username).addListenerForSingleValueEvent(object : ValueEventListener {
            //ctrl + i
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (!dataSnapshot.exists()) {
                    val id = databaseReference.push().key
                    val userData = UserData(id, username, password)
                    databaseReference.child(id!!).setValue(userData)
                    Toast.makeText(this@SignUpActivity, "Sign Up Successful", Toast.LENGTH_SHORT).show()

                    startActivity(Intent(this@SignUpActivity, SignInActivity::class.java))
                    finish()
                }
                else {
                    Toast.makeText(this@SignUpActivity, "User already exists", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@SignUpActivity, "Database Error ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}