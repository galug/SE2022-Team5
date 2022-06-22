package com.example.ottzzang.ui

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Button
import android.widget.TextView
import com.example.ottzzang.R
import com.example.ottzzang.adapter.PostGridViewAapter
import com.example.ottzzang.adapter.PostItem
import com.example.ottzzang.databinding.FragmentPostBinding
import com.example.ottzzang.model.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PostFragment : Fragment() {

    private lateinit var binding: FragmentPostBinding
    private lateinit var bitmap:Bitmap
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPostBinding.inflate(inflater,container,false)

        val gridView = binding.gridview
        val adapter = PostGridViewAapter()

        var retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:9000")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        var postService: PostService = retrofit.create(PostService::class.java)
        val getPostListReq = GetPostListReq(UserIndex.userIdx,0)
        postService.requestGetPosts(getPostListReq).enqueue(object: Callback<GetPostListRes>{
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
                        val encodeByte = Base64.decode(item.file,Base64.DEFAULT)
                        val bitmap = BitmapFactory.decodeByteArray(encodeByte,0,
                            encodeByte.size)
                        adapter.addItem(PostItem(item.name,item.title,item.postLikeCount,item.likeOrNot,bitmap,item.postIdx))
                        Log.d("byte",""+encodeByte)
                    }
                    gridView.adapter = adapter
                    gridView.setOnItemClickListener(AdapterView.OnItemClickListener
                    { parent, view, position, id ->
                        val intent = Intent(activity, PostInfoActivity::class.java)
                        intent.putExtra("name",postList[position].name)
                        intent.putExtra("title",postList[position].title)
                        intent.putExtra("postLikeCount",postList[position].postLikeCount)
                        intent.putExtra("likeOrNot",postList[position].likeOrNot)
                        intent.putExtra("img",postList[position].file)
                        intent.putExtra("isMyFeed",1)
                        intent.putExtra("postIdx",postList[position].postIdx)
                        intent.putExtra("userIdx",UserIndex.userIdx)
                        startActivity(intent)
                    })
                }else{
                    Log.d("fail","응답 x ")
                }
            }

            override fun onFailure(call: Call<GetPostListRes>, t: Throwable) {
                Log.d("실패","실패")
            }
        })
        return binding.root
    }

}