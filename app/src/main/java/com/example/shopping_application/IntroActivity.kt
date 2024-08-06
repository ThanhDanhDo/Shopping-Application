package com.example.shopping_application

import android.content.Intent
import android.os.Bundle
import com.example.shopping_application.databinding.ActivityIntroBinding

private lateinit var binding: ActivityIntroBinding
class IntroActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)
        binding = ActivityIntroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSignUpIntro.setOnClickListener{
            startActivity(Intent(this@IntroActivity, SignUpActivity::class.java))
        }
    }
}