package com.zahid.challenge.networkUtils

import com.zahid.challenge.networkUtils.ApiConstants.Companion.ADD_EMPLOYEE
import com.zahid.challenge.networkUtils.ApiConstants.Companion.DELETE_EMPLOYEE
import com.zahid.challenge.networkUtils.ApiConstants.Companion.EMPLOYEE
import com.zahid.challenge.networkUtils.ApiConstants.Companion.EMPLOYEES
import com.zahid.challenge.networkUtils.ApiConstants.Companion.UPDATE_EMPLOYEE
import com.zahid.challenge.networkUtils.models.EmployeeModel
import com.zahid.challenge.networkUtils.models.EmployeeRequestModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiInterface {
    @POST(ADD_EMPLOYEE)
    suspend fun addEmployee(@Body data:EmployeeRequestModel): Response<ApiResponse<EmployeeRequestModel>>

    @GET(EMPLOYEES)
    suspend fun getEmployees(): Response<ApiResponse<List<EmployeeModel>>>

    @GET("$EMPLOYEE/{id}")
    suspend fun getEmployee(@Path("id") id:String): Response<ApiResponse<EmployeeModel>>

    @PUT(UPDATE_EMPLOYEE)
    suspend fun updateEmployee(@Path("id")id:String,@Body data:EmployeeRequestModel): Response<ApiResponse<EmployeeRequestModel>>

    @DELETE(DELETE_EMPLOYEE)
    suspend fun deleteEmployee(@Path("id")id:String): Response<ApiResponse<String>>
}