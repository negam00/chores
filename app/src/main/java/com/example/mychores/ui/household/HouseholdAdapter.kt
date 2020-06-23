package com.example.mychores.ui.household

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mychores.R
import com.example.mychores.model.User
import kotlinx.android.synthetic.main.item_member.view.*

class HouseholdAdapter(private val household: List<User?>) :    RecyclerView.Adapter<HouseholdAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HouseholdAdapter.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_household, parent, false)
        )
    }

    override fun getItemCount(): Int = household.size

    override fun onBindViewHolder(holder: HouseholdAdapter.ViewHolder, position: Int) =
        holder.bind(household[position])

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(member: User?) {
            itemView.run {
                tvUsernameHousehold.text = member?.userName
                tvFinishedChoresHousehold.text = member?.finishedChores?.size.toString()
            }
        }
    }
}