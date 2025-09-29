package com.example.a98811_lab_week05

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.a98811_lab_week05.api.CatApiService
import retrofit2.Callback
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import android.util.Log
import com.example.a98811_lab_week05.model.ImageData
import android.widget.ImageView
import retrofit2.converter.moshi.MoshiConverterFactory


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        getCatImageResponse()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun getCatImageResponse(){
        val call = catApiService.searchImages(1,"full")
        call.enqueue(object: Callback<List<ImageData>>{
            override fun onFailure(call: Call<List<ImageData>>, t : Throwable){
                Log.e(MAIN_ACTIVITY, "Failed to get response",t)
            }

            override fun onResponse(call: Call<List<ImageData>>,
                                    response: Response<List<ImageData>>) {
                if(response.isSuccessful){
                    val image = response.body()
                    val firstImage = image?.firstOrNull()?.imageUrl
                    if (firstImage != null) {
                        imageLoader.loadImage(firstImage, imageResultView)
                        apiResponseView.text = getString(R.string.image_placeholder, firstImage)
                    } else {
                        apiResponseView.text = getString(R.string.image_placeholder, "No URL")
                        Log.e(MAIN_ACTIVITY, "Image URL is null")
                    }
                }
                else{
                    Log.e(MAIN_ACTIVITY, "Failed to get response\n" +
                            response.errorBody()?.string().orEmpty()
                    )
                }
            }
        })

    }

    private val retrofit by lazy{
        Retrofit.Builder()
            .baseUrl("https://api.thecatapi.com/v1/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    private val catApiService by lazy{
        retrofit.create(CatApiService::class.java)
    }

    private val apiResponseView : TextView by lazy{
        findViewById(R.id.api_response)
    }

    private val imageResultView: ImageView by lazy {
        findViewById(R.id.image_result)
    }
    private val imageLoader: ImageLoader by lazy {
        GlideLoader(this)
    }


    companion object{
        const val  MAIN_ACTIVITY = "MAIN ACTIVITY"
    }
}