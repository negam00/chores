package com.example.mychores.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mychores.R
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        btnSignup.setOnClickListener { signUp() }
    }

    private fun signUp() {
        TODO("Not yet implemented")
    }


}
