package com.example.ottzzang.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import com.example.ottzzang.R
import com.example.ottzzang.databinding.ActivitySignupBinding
import com.example.ottzzang.model.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SignupActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:9000")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        var loginService: LoginService = retrofit.create(LoginService::class.java)


        binding.signUpBtn.setOnClickListener{
            val loginId = binding.loginId.text.toString()
            val loginPwd = binding.loginPwd.text.toString()
            val emailId = binding.emailId.text.toString()
            val name = binding.name.text.toString()
            var signupReq = SignupReq(name, loginId,loginPwd, emailId)
            loginService.requestSignup(signupReq).enqueue(object: Callback<SignupRes> {
                override fun onFailure(call: Call<SignupRes>, t: Throwable) {
                    t.message?.let { it1 -> Log.e("LOGIN", it1) }
                    var dialog = AlertDialog.Builder(this@SignupActivity)
                    dialog.setTitle("에러")
                    dialog.setMessage("호출실패했습니다.")
                    dialog.show()
                }

                override fun onResponse(call: Call<SignupRes>, response: Response<SignupRes>) {
                    var login = response.body()
                    Log.d("LOGIN","" +login)
                    if(login?.isSuccess==true)
                    {
                        finish()
                    }
                    else{
                        var dialog = AlertDialog.Builder(this@SignupActivity)
                        dialog.setTitle("" + login?.code)
                        dialog.setMessage("회원 가입 형식이 잘못 되었습니다.")
                        dialog.show()
                    }
                }
            })
        }
    }
}