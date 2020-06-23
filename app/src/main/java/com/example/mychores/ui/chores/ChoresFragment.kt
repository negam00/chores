package com.example.mychores.ui.chores

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.afollestad.materialdialogs.MaterialDialog
import com.example.mychores.R
import com.example.mychores.model.Chore
import com.example.mychores.ui.chores.add.AddChoreActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_chores.*

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class ChoresFragment : Fragment() {
    private val chores = mutableListOf<Chore>()
    private val choresAdapter = ChoresAdapter(chores)
    private val dialog by lazy { MaterialDialog(requireActivity()) }
    private val viewModel: ChoreViewModel by viewModels()
    private lateinit var triviaText: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chores, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.getChores.observe(viewLifecycleOwner, Observer {
            chores.clear()
            chores.addAll(it)
            choresAdapter.notifyDataSetChanged()
        })

        viewModel.trivia.observe(viewLifecycleOwner, Observer {
            triviaText = it.text
        })
    }

    private fun initViews() {
        rvChores.adapter = choresAdapter
        rvChores.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        fabChores.setOnClickListener { addChoreOnClicked() }
        createItemTouchHelper().attachToRecyclerView(rvChores)
    }

    private fun createItemTouchHelper(): ItemTouchHelper {
        val callback = object :
            ItemTouchHelper.SimpleCallback(1, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val choreToUse = chores[position]

                if (direction == ItemTouchHelper.RIGHT) {

                    chores.remove(choreToUse)
                    choresAdapter.notifyDataSetChanged()
                    //todo update firebase with finished chore

                    showTriviaDialog()
                } else {
                    Snackbar.make(
                        rvChores,
                        "Deleted chore ${choreToUse.choreName}",
                        Snackbar.LENGTH_LONG
                    ).show()
                }
            }
        }
        return ItemTouchHelper(callback)
    }

    private fun showTriviaDialog() {
        //todo change to total finished

        dialog.title(R.string.trivia_title).message(1, triviaText)
        dialog.positiveButton { dialog.dismiss() }
        dialog.show()
    }

    private fun addChoreOnClicked() {
        val intent = Intent(activity, AddChoreActivity::class.java)
        startActivity(intent)
    }
}
