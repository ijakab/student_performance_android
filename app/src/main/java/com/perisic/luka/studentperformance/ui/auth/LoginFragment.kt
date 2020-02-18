package com.perisic.luka.studentperformance.ui.auth


import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.perisic.luka.studentperformance.R
import com.perisic.luka.studentperformance.base.BaseFragment
import com.perisic.luka.studentperformance.ui.util.stringText
import kotlinx.android.synthetic.main.fragment_login.*
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass.
 */
class LoginFragment : BaseFragment(R.layout.fragment_login) {

    private val loginViewModel: AuthViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        loginViewModel.logout()

        loginViewModel.loginResponse().observe(
            success = {},
            error = {
                it.message?.let { it1 ->
                    Snackbar.make(
                        buttonLogin,
                        it1, Snackbar.LENGTH_SHORT
                    ).show()
                }
            }
        )
        buttonLogin.setOnClickListener {
            loginViewModel.login(editTextUsername.stringText(), editTextPassword.stringText())
        }
    }

}
