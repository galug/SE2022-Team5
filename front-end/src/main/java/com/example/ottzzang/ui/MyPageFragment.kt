package com.example.ottzzang.ui


import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.ottzzang.R
import com.example.ottzzang.databinding.FragmentMyPageBinding
import com.example.ottzzang.model.GetPostListReq
import com.example.ottzzang.model.GetPostListRes
import com.example.ottzzang.model.PostService
import com.example.ottzzang.model.UserIndex
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MyPageFragment : Fragment() {
    private lateinit var binding : FragmentMyPageBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyPageBinding.inflate(inflater,container,false)

        var retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:9000")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        var postService: PostService = retrofit.create(PostService::class.java)
        val getPostListReq = GetPostListReq(UserIndex.userIdx,1)
        postService.requestGetPosts(getPostListReq).enqueue(object: Callback<GetPostListRes> {
            override fun onResponse(
                call: Call<GetPostListRes>,
                response: Response<GetPostListRes>
            ) {
                var getPostsListRes =response.body()
                Log.d("확인",getPostsListRes?.message+getPostsListRes?.code+getPostsListRes?.isSuccess)
                Log.d("확인2",response.body().toString())
                if(getPostsListRes?.isSuccess==true){
                    val postList= getPostsListRes.result
                    for(item in postList){
                        val encodeByte = Base64.decode(item.file, Base64.DEFAULT)
                        val bitmap = BitmapFactory.decodeByteArray(encodeByte,0,
                            encodeByte.size)
                        Log.d("byte",""+encodeByte)
                    }
                }else{
                    Log.d("fail","응답 x ")
                }
            }

            override fun onFailure(call: Call<GetPostListRes>, t: Throwable) {
                Log.d("실패","실패")
            }
        })



        binding.addPostBtn.setOnClickListener{
            val intent = Intent(getActivity(), AddPostActivity::class.java)
            startActivity(intent)
        }
        return binding.root
    }

}