package com.perisic.luka.studentperformance.di

import com.perisic.luka.studentperformance.ui.AuthCredentialsViewModel
import com.perisic.luka.studentperformance.ui.addDetails.AddDetailsViewModel
import com.perisic.luka.studentperformance.ui.auth.AuthViewModel
import com.perisic.luka.studentperformance.ui.editUser.EditUserViewModel
import com.perisic.luka.studentperformance.ui.main.MainViewModel
import com.perisic.luka.studentperformance.ui.userList.UserListViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

internal val VMModule = module {

    viewModel {
        AuthViewModel(get())
    }

    viewModel {
        MainViewModel(get())
    }

    viewModel {
        UserListViewModel(get())
    }

    viewModel {
        EditUserViewModel(get())
    }

    viewModel {
        AuthCredentialsViewModel(get())
    }

    viewModel {
        AddDetailsViewModel(get(), get())
    }

}