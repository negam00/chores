package com.example.mychores.ui.main

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mychores.R
import com.example.mychores.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() {
    private val auth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }
    private lateinit var ref: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        btnSignup.setOnClickListener { signUp() }
    }

    private fun signUp() {
        val name = etSignupName.text.toString()
        val email = etSignUpEmail.text.toString()
        val password = etSignupPassword.text.toString()
        val confirmPassword = etSignupConfirmPassword.text.toString()

        if (password == confirmPassword) {
            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful){
                    ref = FirebaseDatabase.getInstance().getReference("/Users/${auth.currentUser?.uid}")
                    val newUser = User(
                        userId = auth.currentUser?.uid.toString(),
                        userName = name,
                        email = email,
                        household = "",
                        finishedChores = emptyList()
                    )
                    ref.setValue(newUser)
                    Toast.makeText(this, "Registration successful!", Toast.LENGTH_SHORT).show()
                    returnToLoginActivity()
                } else Toast.makeText(this, "Registration failed!", Toast.LENGTH_SHORT).show()
            }
        } else Toast.makeText(this, "Passwords are not the same.", Toast.LENGTH_SHORT).show()
    }

    private fun returnToLoginActivity() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}
