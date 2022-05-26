package com.example.ottzzang

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.ottzzang.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}