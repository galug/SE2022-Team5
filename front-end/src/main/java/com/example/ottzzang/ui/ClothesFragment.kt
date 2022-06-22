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
    var season:Int =0
    var bigCategory:Int=0
    var smallCategory: Int=0

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
                        intent.putExtra("clothesIdx",clothesList[position].clothesIdx)
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

        binding.springBtn1.setOnClickListener{
            season = 0
            binding.springLayout.visibility = View.VISIBLE
            binding.defaultLayout.visibility = View.GONE
            binding.summerLayout.visibility = View.GONE
            binding.fallLayout.visibility = View.GONE
            binding.winterLayout.visibility = View.GONE
            //그리드뷰
            binding.topBtn1.setOnClickListener{
                bigCategory = 0
                binding.springLayout.visibility = View.GONE
                binding.topLayout.visibility = View.VISIBLE
                adapter.clearAdapter()
                getClothesListReq.bigCategory =bigCategory
                getClothesListReq.season= season
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
                                intent.putExtra("clothesIdx",clothesList[position].clothesIdx)
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

                binding.topBtn5.setOnClickListener{
                    binding.topLayout.visibility = View.GONE
                    binding.springLayout.visibility = View.VISIBLE
                }
            }
            binding.pantsBtn1.setOnClickListener{
                bigCategory = 1
                binding.springLayout.visibility = View.GONE
                binding.pantsLayout.visibility = View.VISIBLE
                adapter.clearAdapter()
                getClothesListReq.bigCategory =bigCategory
                getClothesListReq.season= season
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
                                intent.putExtra("clothesIdx",clothesList[position].clothesIdx)
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
                binding.pantsBtn5.setOnClickListener{
                    binding.pantsLayout.visibility = View.GONE
                    binding.springLayout.visibility = View.VISIBLE
                    //grid
                }
            }
            binding.outerBtn1.setOnClickListener{
                bigCategory = 2
                binding.springLayout.visibility = View.GONE
                binding.outerLayout.visibility = View.VISIBLE
                adapter.clearAdapter()
                getClothesListReq.bigCategory =bigCategory
                getClothesListReq.season= season
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
                                intent.putExtra("clothesIdx",clothesList[position].clothesIdx)
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
                binding.outerBtn5.setOnClickListener{
                    binding.outerLayout.visibility = View.GONE
                    binding.springLayout.visibility = View.VISIBLE
                    //grid
                }
            }
            binding.etcBtn1.setOnClickListener{
                bigCategory = 3
                binding.springLayout.visibility = View.GONE
                binding.etcLayout.visibility = View.VISIBLE
                adapter.clearAdapter()
                getClothesListReq.bigCategory =bigCategory
                getClothesListReq.season= season
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
                                intent.putExtra("clothesIdx",clothesList[position].clothesIdx)
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
                binding.etcBtn5.setOnClickListener{
                    binding.etcLayout.visibility = View.GONE
                    binding.springLayout.visibility = View.VISIBLE
                    //grid
                }
            }
        }
        binding.summerBtn1.setOnClickListener{
            season = 1
            binding.summerLayout.visibility = View.VISIBLE
            binding.springLayout.visibility = View.GONE
            binding.defaultLayout.visibility = View.GONE
            binding.fallLayout.visibility = View.GONE
            binding.winterLayout.visibility = View.GONE
            //grid view
            binding.topBtn2.setOnClickListener{
                bigCategory = 0
                binding.summerLayout.visibility = View.GONE
                binding.topLayout.visibility = View.VISIBLE
                adapter.clearAdapter()
                getClothesListReq.bigCategory =bigCategory
                getClothesListReq.season= season
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
                                intent.putExtra("clothesIdx",clothesList[position].clothesIdx)
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
                binding.topBtn5.setOnClickListener{
                    binding.topLayout.visibility = View.GONE
                    binding.summerLayout.visibility = View.VISIBLE
                    //grid
                }
            }
            binding.pantsBtn2.setOnClickListener{
                bigCategory = 1
                binding.summerLayout.visibility = View.GONE
                binding.pantsLayout.visibility = View.VISIBLE
                adapter.clearAdapter()
                getClothesListReq.bigCategory =bigCategory
                getClothesListReq.season= season
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
                                intent.putExtra("clothesIdx",clothesList[position].clothesIdx)
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
                binding.pantsBtn5.setOnClickListener{
                    binding.pantsLayout.visibility = View.GONE
                    binding.summerLayout.visibility = View.VISIBLE
                    //grid
                }
            }
            binding.outerBtn2.setOnClickListener{
                bigCategory = 2
                binding.summerLayout.visibility = View.GONE
                binding.outerLayout.visibility = View.VISIBLE
                adapter.clearAdapter()
                getClothesListReq.bigCategory =bigCategory
                getClothesListReq.season= season
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
                                intent.putExtra("clothesIdx",clothesList[position].clothesIdx)
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
                binding.outerBtn5.setOnClickListener{
                    binding.outerLayout.visibility = View.GONE
                    binding.summerLayout.visibility = View.VISIBLE
                    //grid
                }
            }
            binding.etcBtn2.setOnClickListener{
                bigCategory = 3
                binding.summerLayout.visibility = View.GONE
                binding.etcLayout.visibility = View.VISIBLE
                adapter.clearAdapter()
                getClothesListReq.bigCategory =bigCategory
                getClothesListReq.season= season
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
                                intent.putExtra("clothesIdx",clothesList[position].clothesIdx)
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
                binding.etcBtn5.setOnClickListener{
                    binding.etcLayout.visibility = View.GONE
                    binding.summerLayout.visibility = View.VISIBLE
                    //grid
                }
            }
        }
        binding.fallBtn1.setOnClickListener{
            season = 2
            binding.summerLayout.visibility = View.GONE
            binding.springLayout.visibility = View.GONE
            binding.defaultLayout.visibility = View.GONE
            binding.fallLayout.visibility = View.VISIBLE
            binding.winterLayout.visibility = View.GONE
            //grid
            binding.topBtn3.setOnClickListener{
                bigCategory = 0
                binding.fallLayout.visibility = View.GONE
                binding.topLayout.visibility = View.VISIBLE
                adapter.clearAdapter()
                getClothesListReq.bigCategory =bigCategory
                getClothesListReq.season= season
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
                                intent.putExtra("clothesIdx",clothesList[position].clothesIdx)
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
                binding.topBtn5.setOnClickListener{
                    binding.topLayout.visibility = View.GONE
                    binding.fallLayout.visibility = View.VISIBLE
                    //grid
                }
            }
            binding.pantsBtn3.setOnClickListener{
                bigCategory = 1
                binding.fallLayout.visibility = View.GONE
                binding.pantsLayout.visibility = View.VISIBLE
                adapter.clearAdapter()
                getClothesListReq.bigCategory =bigCategory
                getClothesListReq.season= season
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
                                intent.putExtra("clothesIdx",clothesList[position].clothesIdx)
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
                binding.pantsBtn5.setOnClickListener{
                    binding.pantsLayout.visibility = View.GONE
                    binding.fallLayout.visibility = View.VISIBLE
                    //grid
                }
            }
            binding.outerBtn3.setOnClickListener{
                bigCategory = 2
                binding.fallLayout.visibility = View.GONE
                binding.outerLayout.visibility = View.VISIBLE
                adapter.clearAdapter()
                getClothesListReq.bigCategory =bigCategory
                getClothesListReq.season= season
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
                                intent.putExtra("clothesIdx",clothesList[position].clothesIdx)
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
                binding.outerBtn5.setOnClickListener{
                    binding.outerLayout.visibility = View.GONE
                    binding.fallLayout.visibility = View.VISIBLE
                    //grid
                }
            }
            binding.etcBtn3.setOnClickListener{
                bigCategory = 3
                binding.fallLayout.visibility = View.GONE
                binding.etcLayout.visibility = View.VISIBLE
                adapter.clearAdapter()
                getClothesListReq.bigCategory =bigCategory
                getClothesListReq.season= season
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
                                intent.putExtra("clothesIdx",clothesList[position].clothesIdx)
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
                binding.etcBtn5.setOnClickListener{
                    binding.etcLayout.visibility = View.GONE
                    binding.fallLayout.visibility = View.VISIBLE
                    //grid
                }
            }
        }
        binding.winterBtn1.setOnClickListener{
            season = 3
            binding.summerLayout.visibility = View.GONE
            binding.springLayout.visibility = View.GONE
            binding.defaultLayout.visibility = View.GONE
            binding.fallLayout.visibility = View.GONE
            binding.winterLayout.visibility = View.VISIBLE

            binding.topBtn4.setOnClickListener{
                bigCategory = 0
                binding.winterLayout.visibility = View.GONE
                binding.topLayout.visibility = View.VISIBLE
                adapter.clearAdapter()
                getClothesListReq.bigCategory =bigCategory
                getClothesListReq.season= season
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
                                intent.putExtra("clothesIdx",clothesList[position].clothesIdx)
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
                binding.topBtn5.setOnClickListener{
                    binding.topLayout.visibility = View.GONE
                    binding.winterLayout.visibility = View.VISIBLE
                    //grid
                }
            }
            binding.pantsBtn4.setOnClickListener{
                bigCategory = 1
                binding.winterLayout.visibility = View.GONE
                binding.pantsLayout.visibility = View.VISIBLE
                adapter.clearAdapter()
                getClothesListReq.bigCategory =bigCategory
                getClothesListReq.season= season
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
                                intent.putExtra("clothesIdx",clothesList[position].clothesIdx)
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
                binding.pantsBtn5.setOnClickListener{
                    binding.pantsLayout.visibility = View.GONE
                    binding.winterLayout.visibility = View.VISIBLE
                    //grid
                }
            }
            binding.outerBtn4.setOnClickListener{
                bigCategory = 2
                binding.winterLayout.visibility = View.GONE
                binding.outerLayout.visibility = View.VISIBLE
                adapter.clearAdapter()
                getClothesListReq.bigCategory =bigCategory
                getClothesListReq.season= season
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
                                intent.putExtra("clothesIdx",clothesList[position].clothesIdx)
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
                binding.outerBtn5.setOnClickListener{
                    binding.outerLayout.visibility = View.GONE
                    binding.winterLayout.visibility = View.VISIBLE
                    //grid
                }
            }
            binding.etcBtn4.setOnClickListener{
                bigCategory = 3
                binding.summerLayout.visibility = View.GONE
                binding.etcLayout.visibility = View.VISIBLE
                adapter.clearAdapter()
                getClothesListReq.bigCategory =bigCategory
                getClothesListReq.season= season
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
                                intent.putExtra("clothesIdx",clothesList[position].clothesIdx)
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
                binding.etcBtn5.setOnClickListener{
                    binding.etcLayout.visibility = View.GONE
                    binding.summerLayout.visibility = View.VISIBLE
                    //grid
                }
            }
        }
        binding.springBtn2.setOnClickListener{
            binding.defaultLayout.visibility = View.VISIBLE
            binding.springLayout.visibility = View.GONE
            binding.summerLayout.visibility = View.GONE
            binding.fallLayout.visibility = View.GONE
            binding.winterLayout.visibility = View.GONE
            adapter.clearAdapter()
            getClothesListReq.bigCategory = 10
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
                            intent.putExtra("clothesIdx",clothesList[position].clothesIdx)
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
        }
        binding.summerBtn2.setOnClickListener{
            binding.defaultLayout.visibility = View.VISIBLE
            binding.springLayout.visibility = View.GONE
            binding.summerLayout.visibility = View.GONE
            binding.fallLayout.visibility = View.GONE
            binding.winterLayout.visibility = View.GONE
            adapter.clearAdapter()
            getClothesListReq.bigCategory = 10
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
                            intent.putExtra("clothesIdx",clothesList[position].clothesIdx)
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
        }
        binding.fallBtn2.setOnClickListener{
            binding.defaultLayout.visibility = View.VISIBLE
            binding.springLayout.visibility = View.GONE
            binding.summerLayout.visibility = View.GONE
            binding.fallLayout.visibility = View.GONE
            binding.winterLayout.visibility = View.GONE
            adapter.clearAdapter()
            getClothesListReq.bigCategory = 10
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
                            intent.putExtra("clothesIdx",clothesList[position].clothesIdx)
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
        }
        binding.winterBtn2.setOnClickListener{
            binding.defaultLayout.visibility = View.VISIBLE
            binding.springLayout.visibility = View.GONE
            binding.summerLayout.visibility = View.GONE
            binding.fallLayout.visibility = View.GONE
            binding.winterLayout.visibility = View.GONE
            adapter.clearAdapter()
            getClothesListReq.bigCategory = 10
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
                            intent.putExtra("clothesIdx",clothesList[position].clothesIdx)
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
        }
        return binding.root
    }
}