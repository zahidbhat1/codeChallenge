package com.zahid.challenge.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zahid.challenge.networkUtils.ApiInterface
import com.zahid.challenge.networkUtils.models.EmployeeModel
import com.zahid.challenge.networkUtils.models.EmployeeRequestModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class EmployeeViewModel@Inject constructor(private val api: ApiInterface) : ViewModel() {
    val loading by lazy { MutableLiveData<Boolean>() }
    val employeesList by lazy { MutableLiveData<List<EmployeeModel>>() }
    val employeeData by lazy { MutableLiveData<EmployeeModel>() }
    val addEmployeeData by lazy { MutableLiveData<EmployeeRequestModel>() }
    val deleteEmployee by lazy { MutableLiveData<String>() }
    val selectedEmployee by lazy { MutableLiveData<EmployeeModel>() }
    val error by lazy { MutableLiveData<String>() }


    fun addEmployee(data:EmployeeRequestModel) {
        loading.value=true
        viewModelScope.launch {
            try {
                val response = api.addEmployee(data)
                loading.value=false
                if(response.isSuccessful){
                    addEmployeeData.value = response.body()?.data
                }else{
                    if(response.code()==429){
                        error.value="API Response Error!"
                    }else{
                        error.value=response.message()
                    }
                }
            } catch (e: Exception) {
                loading.value=false
                error.value=e.message
            }
        }
    }

    fun getEmployee(employeeId: String) {
        loading.value=true
        viewModelScope.launch {
            try {
                val response = api.getEmployee(employeeId)
                loading.value=false
                if(response.isSuccessful){
                    employeeData.value = response.body()?.data
                }else{
                    if(response.code()==429){
                        error.value="API Response Error!"
                    }else{
                        error.value=response.message()
                    }
                }
            } catch (e: Exception) {
                loading.value=false
                error.value=e.message
            }
        }
    }
    fun loadEmployees() {
        loading.value=true
        viewModelScope.launch {
            try {
                val response = api.getEmployees()
                loading.value=false
                if(response.isSuccessful){
                    employeesList.value = response.body()?.data
                }else{
                    if(response.code()==429){
                        error.value="API Response Error!"
                    }else{
                        error.value=response.message()
                    }
                }
            } catch (e: Exception) {
                loading.value=false
                error.value=e.message
            }
        }
    }
    fun updateEmployee(data: EmployeeRequestModel, employeeId: String?) {
        loading.value=true
        viewModelScope.launch {
            try {
                val response = api.updateEmployee(employeeId?:"",data)
                loading.value=false
                if(response.isSuccessful){
                    addEmployeeData.value = response.body()?.data
                }else{
                    if(response.code()==429){
                        error.value="API Response Error!"
                    }else{
                        error.value=response.message()
                    }
                }
            } catch (e: Exception) {
                loading.value=false
                error.value=e.message
            }
        }
    }

    fun deleteEmployee(employeeId: String) {
        loading.value=true
        viewModelScope.launch {
            try {
                val response = api.deleteEmployee(employeeId)
                loading.value=false
                if(response.isSuccessful){
                    deleteEmployee.value = response.body()?.data
                }else{
                    if(response.code()==429){
                        error.value="API Response Error!"
                    }else{
                        error.value=response.message()
                    }
                }
            } catch (e: Exception) {
                loading.value=false
                error.value=e.message
            }
        }
    }
}
