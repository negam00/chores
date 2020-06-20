package com.example.mychores.ui.chores.add

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.mychores.R
import com.example.mychores.model.Chore
import com.example.mychores.ui.main.MainActivity
import kotlinx.android.synthetic.main.activity_add_chore.*

class AddChoreActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_chore)
        initViews()
    }

    private fun initViews() {
        btnAddChore.setOnClickListener { addChoreOnClicked() }
    }

    private fun addChoreOnClicked() {
            if (checkInput()) {
                val newChore = Chore(
                    choreName = etChoreTitle.text.toString(),
                    choreDescription = etChoreDescription.text.toString(),
                    recurrence = etChoreRecurrence.text.toString().toInt()
                )
                // todo actually add chore
                Toast.makeText(this, "Chore was added!", Toast.LENGTH_SHORT).show()
                returnToChoresFragment()
            } else Toast.makeText(this, "Please fill in all fields!", Toast.LENGTH_SHORT).show()

    }

    private fun checkInput(): Boolean {
        return etChoreTitle.text.toString().isNotBlank()
                && etChoreDescription.text.toString().isNotBlank()
                && etChoreRecurrence.text.toString().isNotBlank()
    }

    private fun returnToChoresFragment() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
