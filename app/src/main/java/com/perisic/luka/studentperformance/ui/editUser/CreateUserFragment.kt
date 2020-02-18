package com.perisic.luka.studentperformance.ui.editUser


import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.perisic.luka.data.remote.model.request.UserDataRequest
import com.perisic.luka.studentperformance.R
import com.perisic.luka.studentperformance.base.BaseFragment
import com.perisic.luka.studentperformance.ui.AuthCredentialsViewModel
import com.perisic.luka.studentperformance.ui.util.postDelayedWatcher
import com.perisic.luka.studentperformance.ui.util.stringText
import kotlinx.android.synthetic.main.fragment_create_user.*
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass.
 */
class CreateUserFragment : BaseFragment(
    R.layout.fragment_create_user
) {

    private val editUserViewModel by viewModel<EditUserViewModel>()
    private val authCredentialsViewModel by viewModel<AuthCredentialsViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        editUserViewModel.createUserResponse().observe(
            success = {
                findNavController().navigateUp()
            },
            error = {
                Snackbar.make(editTextFirstName, it.message ?: "ERROR", Snackbar.LENGTH_SHORT)
                    .show()
            }
        )
        authCredentialsViewModel.checkUsernameResponse.observe(
            success = {
                textInputLayoutUsername.error = null
            },
            error = {
                if (it.code == "auth.usernameExists")
                    textInputLayoutUsername.error = getString(R.string.username_exists)
            }
        )
        authCredentialsViewModel.checkEmailResponse.observe(
            success = {
                textInputLayoutEmail.error = null
            },
            error = {
                if (it.code == "auth.emailExists") {
                    textInputLayoutEmail.error = getString(R.string.email_exists)
                }
            }
        )
        initUi()
    }

    private fun initUi() {
        buttonAction.setText(R.string.create)
        buttonBack.setOnClickListener {
            findNavController().navigateUp()
        }
        val spinnerAdapter = ArrayAdapter<String>(
            requireContext(),
            R.layout.item_dropdown_menu,
            arrayListOf("teacher", "student", "admin")
        )
        spinnerRoleChoice.setAdapter(spinnerAdapter)
        buttonAction.setOnClickListener {
            editUserViewModel.createUser(
                UserDataRequest(
                    username = editTextUsername.stringText(),
                    firstName = editTextFirstName.stringText(),
                    lastName = editTextLastName.stringText(),
                    role = spinnerRoleChoice.stringText(),
                    email = editTextEmail.stringText(),
                    password = editTextPassword.stringText()
                )
            )
        }
        editTextUsername.postDelayedWatcher {
            authCredentialsViewModel.checkUsername(it)
        }
        editTextEmail.postDelayedWatcher {
            authCredentialsViewModel.checkEmail(it)
        }
    }

}