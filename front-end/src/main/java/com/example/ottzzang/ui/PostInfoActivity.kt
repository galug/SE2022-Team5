package com.example.ottzzang.ui

import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.util.Log
import com.example.ottzzang.R
import com.example.ottzzang.databinding.ActivityClothesInfoBinding
import com.example.ottzzang.databinding.ActivityPostInfoBinding
import com.example.ottzzang.model.ClothesService
import com.example.ottzzang.model.PostPostLikReq
import com.example.ottzzang.model.PostPostLikeRes
import com.example.ottzzang.model.PostService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PostInfoActivity : AppCompatActivity() {
    lateinit var binding: ActivityPostInfoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val isMyFeed = intent.getIntExtra("isMyFeed",0)
        val intent = getIntent()
        val name = intent.getStringExtra("name")
        val title = intent.getStringExtra("title")
//        val postLikeCount = intent.getIntExtra("postLikeCount",0)
        val file = intent.getStringExtra("img")
        val encodeByte = Base64.decode(file, Base64.DEFAULT)
        val bitmap = BitmapFactory.decodeByteArray(encodeByte,0, encodeByte.size)
        binding.iv.setImageBitmap(bitmap)
        binding.mainTitle.setText(title)
        binding.name.setText(name)
        if(isMyFeed==1){
            val postIdx = intent.getIntExtra("postIdx",0)
            val userIdx = intent.getIntExtra("userIdx",0)
            val postPostLikReq = PostPostLikReq(userIdx,postIdx)
            binding.likeBtn.setOnClickListener{
                binding.likeBtn.setSelected(true)
                var retrofit = Retrofit.Builder()
                    .baseUrl("http://10.0.2.2:9000")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                var postService: PostService = retrofit.create(PostService::class.java)
                postService.requestLikePosts(postPostLikReq).enqueue(object :Callback<PostPostLikeRes>{
                    override fun onResponse(
                        call: Call<PostPostLikeRes>,
                        response: Response<PostPostLikeRes>
                    ) {
                        val result = response.body()
                        val str = result?.result
                        Log.d("result",str.toString())
                    }

                    override fun onFailure(call: Call<PostPostLikeRes>, t: Throwable) {
                        Log.d("실패","실패")
                    }
                })
            }
        }

    }
}