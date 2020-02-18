package com.perisic.luka.studentperformance.ui.main

import androidx.lifecycle.ViewModel
import com.perisic.luka.data.repo.AuthRepository

class MainViewModel(
    private val authRepository: AuthRepository
): ViewModel() {

    fun tokenCount() = authRepository.tokenCount()

    fun fetchRole() = authRepository.userRole()

}