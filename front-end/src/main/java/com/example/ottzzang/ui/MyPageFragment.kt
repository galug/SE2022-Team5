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
import android.widget.AdapterView
import androidx.core.view.children
import com.example.ottzzang.R
import com.example.ottzzang.adapter.*
import com.example.ottzzang.databinding.FragmentMyPageBinding
import com.example.ottzzang.model.*
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

        val gridView = binding.gridView
        var deletegridView = binding.deletegridView
        val adapter = MyPagePostGridViewAdapter()
        val deleteAdapater = MyPagePostDeleteGridViewAdapter()

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
                Log.d("mypage",getPostsListRes?.message+getPostsListRes?.code+getPostsListRes?.isSuccess)
                Log.d("mapage2",response.body().toString())
                if(getPostsListRes?.isSuccess==true){
                    val postList= getPostsListRes.result
                    for(item in postList){
                        val encodeByte = Base64.decode(item.file, Base64.DEFAULT)
                        val bitmap = BitmapFactory.decodeByteArray(encodeByte,0,
                            encodeByte.size)
                        Log.d("mypage2byte",""+encodeByte)
                        adapter.addItem(PostItem(item.name,item.title,item.postLikeCount,item.likeOrNot,bitmap,item.postIdx))
                        deleteAdapater.addItem(PostDeleteItem(item.postIdx))
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
                        startActivity(intent)
                    })
                    deletegridView.adapter = deleteAdapater
                    deletegridView.setOnItemClickListener(AdapterView.OnItemClickListener
                    {
                            parent, view, position, id ->
                        Log.d("deleteAdapter","지워져라 ")
                        postService.requestDeletePosts(postList[position].postIdx).enqueue(object :Callback<DeletePostRes>{
                            override fun onResponse(
                                call: Call<DeletePostRes>,
                                response: Response<DeletePostRes>
                            ) {
                                val result = response.body()
                                Log.d("deleterorr", result.toString())
                            }

                            override fun onFailure(call: Call<DeletePostRes>, t: Throwable) {
                                Log.d("실패","실패")
                            }
                        })
                    })
                }else{
                    Log.d("fail","응답 x ")
                }
            }

            override fun onFailure(call: Call<GetPostListRes>, t: Throwable) {
                Log.d("실패","실패")
            }
        })


        var retrofitlog = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:9000")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        var loginService: LoginService = retrofitlog.create(LoginService::class.java)
        binding.withdrawalBtn.setOnClickListener{
            loginService.requestDeleteUsers(UserIndex.userIdx).enqueue(object :Callback<DeleteUserRes>{
                override fun onResponse(
                    call: Call<DeleteUserRes>,
                    response: Response<DeleteUserRes>
                ) {
                    val result = response.body()
                    Log.d("deleterorr", result.toString())
                    val intent = Intent(activity,LoginActivity::class.java)
                    startActivity(intent)
                }

                override fun onFailure(call: Call<DeleteUserRes>, t: Throwable) {
                    Log.d("실패","실패")
                }
            })
        }

        binding.addPostBtn.setOnClickListener{
            val intent = Intent(getActivity(), AddPostActivity::class.java)
            startActivity(intent)
        }
        return binding.root
    }

}