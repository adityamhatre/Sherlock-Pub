package com.thelegacycoder.pubapp.Interfaces

import com.thelegacycoder.pubapp.Models.Pub
import retrofit2.Call
import retrofit2.http.GET

/**
 * Created by Aditya on 005, 5 Nov, 2017.
 */
interface ApiCall {
    @GET("/getPubs")
    fun getPubs(): Call<List<Pub>>


}