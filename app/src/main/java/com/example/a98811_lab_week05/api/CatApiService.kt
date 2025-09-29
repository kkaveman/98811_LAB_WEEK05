package com.example.a98811_lab_week05.api
import android.media.Image
import android.text.style.ImageSpan
import com.example.a98811_lab_week05.model.ImageData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface CatApiService {
    @GET("images/search")
    fun searchImages(
        @Query("limit") limit:Int,
        @Query("size") format: String

    ): Call<List<ImageData>>
}