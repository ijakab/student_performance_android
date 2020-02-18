package com.perisic.luka.studentperformance.ui.userSingle


import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.perisic.luka.studentperformance.R
import com.perisic.luka.studentperformance.base.BaseFragment
import com.perisic.luka.studentperformance.ui.editUser.EditUserViewModel
import kotlinx.android.synthetic.main.fragment_user_details.*
import org.koin.android.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*

class UserDetailsFragment : BaseFragment(
    R.layout.fragment_user_details
) {

    private val viewModel: EditUserViewModel by viewModel()
    private val args: UserDetailsFragmentArgs by navArgs()

    private val dateFormatFrom = SimpleDateFormat("yyy-MM-dd", Locale.getDefault())
    private val dateFormatTo = SimpleDateFormat("dd. MMMM yyy", Locale.getDefault())

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModel.fetchUser(args.userId).observe(
            success = {
                if (it.role == "student") {
                    circularProgressBar.visibility = View.VISIBLE
                    textViewPerformanceTitle.visibility = View.VISIBLE
                    val progress = if (it.details?.G3 != null) {
                        var prediction: Float? = it.details?.G3?.toFloat()
                        if (prediction != null) {
                            if (prediction < 0) prediction = 0f
                        }
                        prediction
                    } else 0f
                    circularProgressBar.setProgressWithAnimation(
                        (100f * (progress ?: 0f)),
                        1000,
                        interpolator = AccelerateDecelerateInterpolator()
                    )
                }
                textViewUsername.text = it.username
                textViewEmail.text = it.email
                val date = dateFormatFrom.parse(it.createdAt)
                date?.let {
                    textViewRegistrationDate.text = dateFormatTo.format(date)
                }
            },
            error = {}
        )
        buttonBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

}