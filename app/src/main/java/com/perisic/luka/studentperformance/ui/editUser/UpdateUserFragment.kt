package com.perisic.luka.studentperformance.ui.editUser

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.perisic.luka.data.remote.model.request.UserDataRequest
import com.perisic.luka.studentperformance.R
import com.perisic.luka.studentperformance.base.BaseFragment
import com.perisic.luka.studentperformance.ui.AuthCredentialsViewModel
import com.perisic.luka.studentperformance.ui.util.stringText
import kotlinx.android.synthetic.main.fragment_add_details.*
import kotlinx.android.synthetic.main.fragment_create_user.*
import kotlinx.android.synthetic.main.fragment_create_user.buttonBack
import org.koin.android.viewmodel.ext.android.viewModel

class UpdateUserFragment : BaseFragment(
    R.layout.fragment_create_user
) {

    private val args: UpdateUserFragmentArgs by navArgs()
    private val editUserViewModel by viewModel<EditUserViewModel>()
    private val authCredentialsViewModel by viewModel<AuthCredentialsViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        editUserViewModel.editUserResponse(args.userId).observe(
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
        editUserViewModel.fetchUser(args.userId).observe(
            success = {
                authCredentialsViewModel.existingEmail = it.email
                authCredentialsViewModel.existingUsername = it.username
                editUserViewModel.userRole.value = it.role
                editTextUsername.setText(it.username)
                editTextEmail.setText(it.email)
                editTextFirstName.setText(it.firstName)
                editTextLastName.setText(it.lastName)

            },
            error = {
                Snackbar.make(editTextFirstName, "ERROR", Snackbar.LENGTH_SHORT).show()
                findNavController().navigateUp()
            }
        )
        initUi()
    }

    private fun initUi() {
        buttonAction.setText(R.string.update)
        buttonBack.setOnClickListener { findNavController().navigateUp() }
        val spinnerAdapter = ArrayAdapter<String>(
            requireContext(),
            R.layout.item_dropdown_menu,
            arrayListOf("teacher", "student", "admin")
        )
        spinnerRoleChoice.setAdapter(spinnerAdapter)
        spinnerRoleChoice.setOnItemClickListener { _, _, position, _ ->
            editUserViewModel.userRole.value = spinnerAdapter.getItem(position)
        }
        editUserViewModel.userRole.observe(viewLifecycleOwner, Observer {
            if (it != null && it != spinnerRoleChoice.stringText())
                spinnerRoleChoice.setText(
                    it,
                    false
                )
        })
        buttonAction.setOnClickListener {
            editUserViewModel.editUser(
                UserDataRequest(
                    username = editTextUsername.stringText(),
                    firstName = editTextFirstName.stringText(),
                    lastName = editTextLastName.stringText(),
                    password = editTextPassword.stringText(),
                    email = editTextEmail.stringText(),
                    role = spinnerRoleChoice.stringText()
                )
            )
        }
    }

}