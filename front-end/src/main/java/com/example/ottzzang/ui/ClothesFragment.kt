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
import android.widget.Adapter
import android.widget.AdapterView
import android.widget.Toast
import com.example.ottzzang.adapter.ClothesItem
import com.example.ottzzang.adapter.GridViewAdapter
import com.example.ottzzang.databinding.FragmentClothesBinding
import com.example.ottzzang.model.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class ClothesFragment : Fragment() {

    private lateinit var binding: FragmentClothesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentClothesBinding.inflate(inflater, container, false)

        val gridView = binding.gridview
        val adapter = GridViewAdapter()

        var retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:9000")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        var clothesService: ClothesService = retrofit.create(ClothesService::class.java)
        val getClothesListReq = GetClothesListReq(UserIndex.userIdx,10,3,3)

        clothesService.requestGetClothes(getClothesListReq).enqueue(object: Callback<GetClothesListRes>{
            override fun onResponse(
                call: Call<GetClothesListRes>,
                response: Response<GetClothesListRes>
            ) {
                var getClothesListRes =response.body()
                Log.d("확인",getClothesListRes?.message+getClothesListRes?.code+getClothesListRes?.isSuccess)
                Log.d("확인2",response.body().toString())
                if(getClothesListRes?.isSuccess==true){
                    val clothesList= getClothesListRes.result
                    for(item in clothesList){
                        val encodeByte = Base64.decode(item.file,Base64.DEFAULT)
                        val bitmap =BitmapFactory.decodeByteArray(encodeByte,0,
                        encodeByte.size)
                        Log.d("byte",""+encodeByte)
                        adapter.addItem(ClothesItem(bitmap,item.season,item.bigCategory,item.smallCategory,item.color))
                    }
                    gridView.adapter = adapter
                    gridView.setOnItemClickListener(AdapterView.OnItemClickListener
                    { parent, view, position, id ->
                        val intent = Intent(activity,ClothesInfoActivity::class.java)
                        intent.putExtra("bc",clothesList[position].bigCategory)
                        intent.putExtra("sc",clothesList[position].smallCategory)
                        intent.putExtra("color",clothesList[position].color)
                        intent.putExtra("img",clothesList[position].file)
                        startActivity(intent)
                    })
                }else{
                    Log.d("fail","응답 x ")
                }
            }

            override fun onFailure(call: Call<GetClothesListRes>, t: Throwable) {
                Log.d("실패","실패")
            }
        })


        binding.addClothesBtn.setOnClickListener{
            val intent = Intent(getActivity(), AddClothesActivity::class.java)
            startActivity(intent)
        }
        return binding.root
    }
}