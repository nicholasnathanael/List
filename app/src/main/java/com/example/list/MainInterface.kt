package com.example.list

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface MainInterface {
    @GET("api/users")
    fun getTotalUser(): Observable<UserListResponse>

    @GET("api/users")
    fun getUserList(@Query("page") page: Int, @Query("per_page") perPage: Int): Observable<UserListResponse>
}