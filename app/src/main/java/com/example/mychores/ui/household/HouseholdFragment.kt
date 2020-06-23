package com.example.mychores.ui.household

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.example.mychores.R
import com.example.mychores.model.Household
import com.example.mychores.model.User
import com.example.mychores.ui.chores.ChoreViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.dialog_add_household.*
import kotlinx.android.synthetic.main.fragment_household.*
import java.util.*

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class HouseholdFragment : Fragment() {
    private val dialog by lazy { MaterialDialog(requireActivity()) }
    private val household = mutableListOf<User?>()
    private val householdAdapter = HouseholdAdapter(household)
    private val viewModel: ChoreViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_household, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()

    }

    private fun initViews() {
        rvHousehold.adapter = householdAdapter
        rvHousehold.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)

        viewModel.getUserReference.child("/${viewModel.getCurrentUser?.uid}").addValueEventListener(
            object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(activity, "Could not load data", Toast.LENGTH_SHORT).show()
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    // Check if user is part of a household
                    val currentUserHousehold = snapshot.child("/household")

                    if (currentUserHousehold.value.toString().isNotBlank()) {
                        tvNoHousehold.visibility = View.GONE
                        fabHousehold.setOnClickListener {
                            addFamilyMemberOnClicked(
                                currentUserHousehold
                            )
                        }

                        viewModel.getHouseholdReference.child("/${snapshot.child("/household")}")
                            .addValueEventListener(object : ValueEventListener {
                                override fun onCancelled(error: DatabaseError) {
                                    Toast.makeText(
                                        activity,
                                        "Could not load household",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }

                                override fun onDataChange(snapshot: DataSnapshot) {
                                    household.clear()
                                    snapshot.children.forEach {
                                        val member = snapshot.getValue(User::class.java)
                                        member?.let {
                                            household.add(it)
                                        }
                                        householdAdapter.notifyDataSetChanged()
                                    }
                                }
                            })
                    }
                    else fabHousehold.setOnClickListener {
                        addHouseholdOnClicked(snapshot.getValue(User::class.java))
                    }
                }
            }
        )
    }

    private fun addFamilyMemberOnClicked(currentUserHousehold: DataSnapshot) {
        dialog.noAutoDismiss().customView(R.layout.dialog_add_household)
        dialog.etAddHousehold.hint = "Email of household member"
        dialog.btnAddHousehold.text = "Add"

        dialog.btnAddHousehold.setOnClickListener {
//            viewModel.getUserReference.orderByChild("email")
//                .equalTo(dialog.etAddHousehold.text.toString()).addListenerForSingleValueEvent(object : ValueEventListener{
//                    override fun onCancelled(p0: DatabaseError) {
//                    }
//
//                    override fun onDataChange(p0: DataSnapshot) {
//                        p0.children.forEach{
//                            val member = p0.getValue(User::class.java)
//                            p0.child("/household")
//                        }
//                    }
//
//                })
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun addHouseholdOnClicked( userData : User?) {
        dialog.customView(R.layout.dialog_add_household)
        dialog.etAddHousehold.hint = "Household name"
        dialog.btnAddHousehold.text = "Save"

        dialog.btnAddHousehold.setOnClickListener {
            household.add(userData) //todo
            val newHouseHold = Household(
                householdId = UUID.randomUUID().toString().replace("-", ""),
                householdName = dialog.etAddHousehold.text.toString(),
                creator = userData,
                members = household
            )
            viewModel.getHouseholdReference.setValue(newHouseHold)
            // Update user in firebase
            viewModel.getUserReference.child("/${viewModel.getCurrentUser?.uid}/household")
                .setValue(newHouseHold.householdId)
            dialog.dismiss()
        }

        dialog.show()
    }
}
