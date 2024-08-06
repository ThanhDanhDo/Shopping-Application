package com.example.shopping_application

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.shopping_application.databinding.ActivityIntroBinding

private lateinit var binding: ActivityIntroBinding
class IntroActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)
        binding = ActivityIntroBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //click btn Sign Up Intro
        binding.btnSignUpIntro.setOnClickListener{
            startActivity(Intent(this@IntroActivity, SignUpActivity::class.java))
        }

        //click txt Sign In Intro
        binding.txtSignInIntro.setOnClickListener {
            startActivity(Intent(this@IntroActivity, SignInActivity::class.java))
        }
    }
}