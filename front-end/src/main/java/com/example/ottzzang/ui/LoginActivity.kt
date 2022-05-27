package com.example.ottzzang.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isInvisible
import com.example.ottzzang.databinding.ActivityLoginBinding
import com.example.ottzzang.model.LoginReq
import com.example.ottzzang.model.LoginRes
import com.example.ottzzang.model.LoginService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var signUpIntent: Intent = Intent(this, SignupActivity::class.java)
        var homeIntent: Intent = Intent(this, HomeActivity::class.java)

        binding.signUpBtn.setOnClickListener {
            startActivity(signUpIntent)
        }

        binding.loginBtn.setOnClickListener{
            startActivity(homeIntent)
        }
        var retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:9000")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        var loginService: LoginService = retrofit.create(LoginService::class.java)


        binding.loginBtn.setOnClickListener{
            var loginId = binding.loginId.text.toString()
            var loginPwd = binding.loginPwd.text.toString()
            var loginReq = LoginReq(loginId,loginPwd)
            loginService.requestLogin(loginReq).enqueue(object: Callback<LoginRes> {
                override fun onFailure(call: Call<LoginRes>, t: Throwable) {
                    t.message?.let { it1 -> Log.e("LOGIN", it1) }
                    var dialog = AlertDialog.Builder(this@LoginActivity)
                    dialog.setTitle("에러")
                    dialog.setMessage("호출실패했습니다.")
                    dialog.show()
                }

                override fun onResponse(call: Call<LoginRes>, response: Response<LoginRes>) {
                    var login = response.body()
                    Log.d("LOGIN","" +login)
                    if(login?.isSuccess==true)
                    {
                        startActivity(homeIntent)
                    }
                    else{
                        var dialog = AlertDialog.Builder(this@LoginActivity)
                        dialog.setTitle("" + login?.code)
                        dialog.setMessage("없는 아이디거나 비밀번호 입니다.")
                        dialog.show()
                    }
                }
            })
        }
    }
}