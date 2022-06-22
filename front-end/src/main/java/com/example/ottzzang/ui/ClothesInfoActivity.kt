package com.example.ottzzang.ui

import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.util.Log
import com.example.ottzzang.databinding.ActivityClothesInfoBinding
import com.example.ottzzang.model.ClothesService
import com.example.ottzzang.model.DeleteClothesRes
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ClothesInfoActivity : AppCompatActivity() {
    val bigCategory = arrayOf("상의","하의","아우터","기타")
    val smallCategory = arrayOf(arrayOf("반팔","긴팔","셔츠","기타"),
        arrayOf("치마","반바지","긴바지","기타"),
        arrayOf("자켓","패딩","기타"))
    private lateinit var binding:ActivityClothesInfoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityClothesInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val intent = getIntent()
        val file = intent.getStringExtra("img")
        val bc = intent.getIntExtra("bc",0)
        val sc = intent.getIntExtra("sc",0)
        val color = intent.getStringExtra("color")
        val encodeByte = Base64.decode(file, Base64.DEFAULT)
        val bitmap = BitmapFactory.decodeByteArray(encodeByte,0,
            encodeByte.size)
        val clothesIdx = intent.getIntExtra("clothesIdx",0)
        Log.d("clothesIdx",clothesIdx.toString())
        binding.iv.setImageBitmap(bitmap)
        binding.bctxt.setText(bigCategory[bc])
        binding.sctxt.setText(smallCategory[bc][sc])
        binding.color.setText(color)


        binding.correctionBtn.setOnClickListener{
            val intent = Intent(this,CorrectionClothesActivity::class.java)
            intent.putExtra("clothesIdx",clothesIdx)
            startActivity(intent)
        }
        binding.deleteBtn.setOnClickListener {
            var retrofit = Retrofit.Builder()
                .baseUrl("http://10.0.2.2:9000")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            var clothesService: ClothesService = retrofit.create(ClothesService::class.java)
            clothesService.requestDeleteClothes(clothesIdx).enqueue(object : Callback<DeleteClothesRes>
            {
                override fun onResponse(
                    call: Call<DeleteClothesRes>,
                    response: Response<DeleteClothesRes>
                ) {
                    val result = response.body()
                    Log.d("deleterorr", result.toString())
                    finish()
                }

                override fun onFailure(call: Call<DeleteClothesRes>, t: Throwable) {
                    Log.d("fail","응답 x ")
                }
            })
        }

    }
}