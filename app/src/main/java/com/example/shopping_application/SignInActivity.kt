package com.example.shopping_application

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ReportFragment.Companion.reportFragment
import com.example.shopping_application.databinding.ActivitySignInBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

private lateinit var binding: ActivitySignInBinding
class SignInActivity : AppCompatActivity() {
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference
    //SharedPreferences để lưu thông tin người dùng hiện tại
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //khởi tạo database firebase
        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase.reference.child("users")

        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

        binding.btnSignIn.setOnClickListener {
            val loginUsername = binding.edtUsername.text.toString()
            val loginPassword = binding.edtPassword.text.toString()

            when {
                loginUsername.isEmpty() -> binding.edtUsername.error = "Please enter Username"
                loginPassword.isEmpty() -> binding.edtPassword.error = "Please enter Password"
                else -> loginUser(loginUsername, loginPassword)
            }
        }

        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.txtRegister.setOnClickListener {
            startActivity(Intent(this@SignInActivity, SignUpActivity::class.java))
        }
    }

    private fun loginUser(username: String, password: String) {
        databaseReference.orderByChild("username").equalTo(username).addListenerForSingleValueEvent(object : ValueEventListener {
            //ctrl + i
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (userSnapshot in dataSnapshot.children) {
                        val userData = userSnapshot.getValue(UserData::class.java)

                        if (userData != null && userData.password == password) {
                            // lưu ID user hiện tại vào SharedPreferences
                            val editor = sharedPreferences.edit()
                            editor.putString("userId", userData.id)
                            editor.apply()

                            Toast.makeText(this@SignInActivity, "Login Successful", Toast.LENGTH_SHORT).show()
                            // Gửi username sang MainActivity
                            val intent = Intent(this@SignInActivity, MainActivity::class.java)
                            intent.putExtra("username", username)
                            startActivity(intent)
                            finish()
                            return
                        }
                    }
                    //chạy hết vòng for vẫn không tìm được -> không đúng username hoặc password
                    binding.edtUsername.setError("Incorrect Username")
                    binding.edtPassword.setError("Incorrect Password")
                }
                else {
                    Toast.makeText(this@SignInActivity, "Login Failed", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@SignInActivity, "Database Error ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}