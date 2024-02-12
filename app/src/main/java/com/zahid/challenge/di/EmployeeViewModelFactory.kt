package com.zahid.challenge.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.zahid.challenge.networkUtils.ApiInterface
import com.zahid.challenge.viewModels.EmployeeViewModel
import javax.inject.Inject

class EmployeeViewModelFactory @Inject constructor(private val api: ApiInterface) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EmployeeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return EmployeeViewModel(api) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
