package com.example.ottzzang.ui

import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import com.example.ottzzang.R
import com.example.ottzzang.databinding.ActivityClothesInfoBinding

class ClothesInfoActivity : AppCompatActivity() {
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
        binding.iv.setImageBitmap(bitmap)
        binding.bctxt.setText(bc.toString())
        binding.sctxt.setText(sc.toString())
        binding.color.setText(color)
    }
}