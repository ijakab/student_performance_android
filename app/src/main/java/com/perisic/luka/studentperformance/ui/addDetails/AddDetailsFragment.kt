package com.perisic.luka.studentperformance.ui.addDetails


import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.perisic.luka.studentperformance.R
import com.perisic.luka.studentperformance.adapters.SeekBarChoiceAdapter
import com.perisic.luka.studentperformance.adapters.SingleChoiceAdapter
import com.perisic.luka.studentperformance.adapters.SpinnerChoiceAdapter
import com.perisic.luka.studentperformance.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_add_details.*
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass.
 */
class AddDetailsFragment : BaseFragment(
    R.layout.fragment_add_details
) {

    private val viewModel: AddDetailsViewModel by viewModel()
    private val spinnerChoiceAdapter = SpinnerChoiceAdapter()
    private val singleChoiceAdapter = SingleChoiceAdapter()
    private val seekBarChoiceAdapter = SeekBarChoiceAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModel.fetchUserId().observe(viewLifecycleOwner, Observer {
            if (it != null) {
                viewModel.getUserDetails(it.id)
            }
        })

        viewModel.userDetailsResponse.observe(
            success = {
                it.spinnerChoices?.let { spinnerChoices ->
                    spinnerChoiceAdapter.setData(
                        spinnerChoices
                    )
                }
                it.seekBarChoices?.let { seekBarChoices ->
                    seekBarChoiceAdapter.setData(
                        seekBarChoices
                    )
                }
                it.singleChoices?.let { singleChoices ->
                    singleChoiceAdapter.setData(
                        singleChoices
                    )
                }
                it.G3?.let { it1 ->
                    Snackbar.make(recyclerViewSpinnerChoices, it1, Snackbar.LENGTH_SHORT)
                        .show()
                }
            },
            error = {}
        )

        viewModel.addUserDetailsResponse.observe(
            success = {
                it.details?.G3?.let { it1 ->
                    Snackbar.make(recyclerViewSpinnerChoices, "${getString(R.string.predicted_performance)} ${if(it1.toFloat() < 0) 0 else (it1.toFloat() * 100).toInt()}%", Snackbar.LENGTH_SHORT)
                        .show()
                }
            },
            error = {}
        )

        recyclerViewSpinnerChoices.adapter = spinnerChoiceAdapter
        recyclerViewSingleChoices.adapter = singleChoiceAdapter
        recyclerViewSeekBarChoices.adapter = seekBarChoiceAdapter

        buttonEditDetails.setOnClickListener {
            val userDetails = viewModel.userDetailsResponse.value?.data
            if (userDetails != null) {
                userDetails.applySeekBarChoices(seekBarChoiceAdapter.data)
                userDetails.applySpinnerChoices(spinnerChoiceAdapter.data)
                userDetails.applySingleChoices(singleChoiceAdapter.data)
                viewModel.addUserDetails(
                    userDetails
                )
            }
        }

    }

}
