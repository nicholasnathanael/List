package com.example.list

import com.google.gson.annotations.SerializedName

class UserListResponse(
    @SerializedName("page") val page: Int,
    @SerializedName("per_page") val perPage: Int,
    @SerializedName("total") val total: Int,
    @SerializedName("total_pages") val totalPages: Int,
    @SerializedName("data") val userList: MutableList<User>,
    @SerializedName("support") val support: Support
) {
    class Support(
        @SerializedName("url") val url: String,
        @SerializedName("text") val text: String
    )
}