package com.zahid.challenge.networkUtils

import com.google.gson.annotations.SerializedName


class ApiResponse<T> {
    @SerializedName("status")
    val status: String? = null
    @SerializedName("message")
    val msg: String? = null
    val data: T? = null
}