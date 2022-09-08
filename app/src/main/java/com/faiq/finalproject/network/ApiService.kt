package com.faiq.finalproject.network

import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("")
    fun getApi() : Call<pResponse>
}