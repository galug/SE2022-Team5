package com.example.ottzzang.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import com.example.ottzzang.R
import com.example.ottzzang.databinding.ActivityAddClothesBinding
import com.example.ottzzang.databinding.ActivityCorrectionClothesBinding
import com.example.ottzzang.model.ClothesService
import com.example.ottzzang.model.DeleteClothesRes
import com.example.ottzzang.model.PostClothesRes
import com.example.ottzzang.model.UserIndex
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.text.SimpleDateFormat

class CorrectionClothesActivity : AppCompatActivity() {
    lateinit var binding: ActivityCorrectionClothesBinding
    var uri: Uri? =null
    lateinit var mediaPath:String
    var season:Int =0
    var bigCategory:Int=0
    var smallCategory: Int=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCorrectionClothesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        settingPermission()
        val intent = getIntent()
        var retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:9000")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        var clothesService: ClothesService = retrofit.create(ClothesService::class.java)
        Log.d("INFO123", "RESULT_OK")
        binding.cameraBtn.setOnClickListener{
            captureCamera()
        }
        binding.albumBtn.setOnClickListener{
            startGalleryApp()
        }
        binding.springBtn.setOnClickListener{
            season=0
        }
        binding.summerBtn.setOnClickListener{
            season=1
        }
        binding.fallBtn.setOnClickListener{
            season=2
        }
        binding.winterBtn.setOnClickListener{
            season=3
        }
        binding.top.setOnClickListener{
            bigCategory = 0
            binding.topSmallLayout.visibility = View.VISIBLE
            binding.pantSmallLayout.visibility = View.GONE
            binding.outerSmallLayout.visibility = View.GONE
            binding.shortSleeve.setOnClickListener{
                smallCategory = 0
            }
            binding.longSleeve.setOnClickListener{
                smallCategory = 1
            }
            binding.shirts.setOnClickListener{
                smallCategory = 2
            }
            binding.topEtc.setOnClickListener{
                smallCategory = 3
            }
        }
        binding.pants.setOnClickListener{
            bigCategory = 1
            binding.topSmallLayout.visibility = View.GONE
            binding.pantSmallLayout.visibility = View.VISIBLE
            binding.outerSmallLayout.visibility = View.GONE
            binding.skirt.setOnClickListener{
                smallCategory = 0
            }
            binding.shortpants.setOnClickListener{
                smallCategory = 1
            }
            binding.longpants.setOnClickListener{
                smallCategory = 2
            }
            binding.pantsEtc.setOnClickListener{
                smallCategory = 3
            }
        }
        binding.outer.setOnClickListener{
            bigCategory = 2
            binding.topSmallLayout.visibility = View.GONE
            binding.pantSmallLayout.visibility = View.GONE
            binding.outerSmallLayout.visibility = View.VISIBLE
            binding.jacket.setOnClickListener{
                smallCategory = 0
            }
            binding.padding.setOnClickListener{
                smallCategory = 1
            }
            binding.outerEtc.setOnClickListener{
                smallCategory = 2
            }
        }
        binding.etc.setOnClickListener{
            bigCategory = 3
            binding.topSmallLayout.visibility = View.GONE
            binding.pantSmallLayout.visibility = View.GONE
            binding.outerSmallLayout.visibility = View.GONE
        }
        binding.correctionBtn.setOnClickListener{
            val intent = Intent(this,ClothesFragment::class.java)
            val clothesIdx = intent.getIntExtra("clothesIdx",0)
            val userIdx = RequestBody.create(MediaType.parse("text/plain"), UserIndex.userIdx.toString())
            val season = RequestBody.create(MediaType.parse("text/plain"),season.toString())
            val bigCategory = RequestBody.create(MediaType.parse("text/plain"),bigCategory.toString())
            val smallCategory = RequestBody.create(MediaType.parse("text/plain"),smallCategory.toString())
            val color = RequestBody.create(MediaType.parse("text/plain"),binding.color.text.toString())
            val file = File(mediaPath)
            val fileBody = RequestBody.create(MediaType.parse("image/jpeg"),file)
            val filePart = MultipartBody.Part.createFormData("imgFile",uri.toString(),fileBody)
            clothesService.requestDeleteClothes(clothesIdx).enqueue(object : Callback<DeleteClothesRes>
            {
                override fun onResponse(
                    call: Call<DeleteClothesRes>,
                    response: Response<DeleteClothesRes>
                ) {
                    val result = response.body()
                    Log.d("deleterorr", result.toString())
                    finish()
                }

                override fun onFailure(call: Call<DeleteClothesRes>, t: Throwable) {
                    Log.d("fail","응답 x ")
                }
            })
            clothesService.requestAddClothes(filePart,userIdx,season,bigCategory,smallCategory,color).enqueue(object:
                Callback<PostClothesRes> {
                override fun onFailure(call: Call<PostClothesRes>, t: Throwable) {
                    t.message?.let { it1 -> Log.e("LOGIN", it1) }
                    var dialog = AlertDialog.Builder(this@CorrectionClothesActivity)
                    dialog.setTitle("에러")
                    dialog.setMessage("호출실패했습니다.")
                    dialog.show()
                }

                override fun onResponse(
                    call: Call<PostClothesRes>,
                    response: Response<PostClothesRes>
                ) {
                    var clothesRes = response.body()
                    Log.d("LOGIN","" +clothesRes)
                    if(clothesRes?.isSuccess==true)
                    {
                        startActivity(intent)
                    }
                    else{
                        var dialog = AlertDialog.Builder(this@CorrectionClothesActivity)
                        Log.d("뭐가 문젤까?","" +clothesRes?.message)
                        dialog.setTitle("" + clothesRes?.code)
                        dialog.setMessage("옷추가 실패 ")
                        dialog.show()
                    }
                }
            })
        }
    }

    fun settingPermission(){//허가를 받기 위한 함수
        var permis = object  : PermissionListener {
            //            어떠한 형식을 상속받는 익명 클래스의 객체를 생성하기 위해 다음과 같이 작성
            override fun onPermissionGranted() {
                Toast.makeText(this@CorrectionClothesActivity,"권한 허용", Toast.LENGTH_SHORT)
                    .show()
            }

            override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
                Toast.makeText(this@CorrectionClothesActivity, "권한 거부", Toast.LENGTH_SHORT)
                    .show()
                ActivityCompat.finishAffinity(this@CorrectionClothesActivity) // 권한 거부시 앱 종료
            }
        }

        TedPermission.with(this)
            .setPermissionListener(permis)
            .setRationaleMessage("카메라 사진 권한 필요")
            .setDeniedMessage("카메라 권한 요청 거부")
            .setPermissions(
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                android.Manifest.permission.CAMERA)
            .check()
    }

    private fun startGalleryApp() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent, 102)
    }
    fun captureCamera(){ //카메라로 앱을 넘어가는 인텐트
        var intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent,101)
    }
    fun saveFile(fileName: String, mimeType: String, bitmap: Bitmap): Uri?
    {
        var CV = ContentValues()
        CV.put(MediaStore.Images.Media.DISPLAY_NAME, fileName)
        CV.put(MediaStore.Images.Media.MIME_TYPE, mimeType)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            CV.put(MediaStore.Images.Media.IS_PENDING, 1)
        }

        val uri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, CV)

        if (uri != null) {
            var scriptor = contentResolver.openFileDescriptor(uri, "w")

            if (scriptor != null) {
                val fos = FileOutputStream(scriptor.fileDescriptor)
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)
                fos.close()

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    CV.clear()
                    CV.put(MediaStore.Images.Media.IS_PENDING, 0)
                    contentResolver.update(uri, CV, null, null)
                }
            }
        }

        return uri;
    }

    fun RandomFileName() : String //FIle이름이 겹치지 않게 랜덤하게 파일의 이름을 반환하는 함수
    {
        val fineName = SimpleDateFormat("yyyyMMddHHmmss").format(System.currentTimeMillis())
        return fineName
    }

    @SuppressLint("Range")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                101 -> {
                    if (data?.extras?.get("data") != null) {
                        val selectedImg = data.data

                        val img = data?.extras?.get("data") as Bitmap
//                        val byteArrayOutputStream = ByteArrayOutputStream()
//                        img?.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream)
//                        val byteArray = byteArrayOutputStream.toByteArray()
                        uri = saveFile(RandomFileName(), "image/jpeg", img)
                        mediaPath =
                            createCopyAndReturnRealPath(context = this, uri!!)!!
                        val file = File(uri.toString())
                        if(!file.exists()){
                            Log.d("failllll","file not found"+"uri"+uri.toString())
                        }
                        binding.iv.setImageURI(uri)
                    }
                }
                102 ->{
//                    val selectedImg = data?.data
//                    val img = data?.extras?.get("data") as Bitmap
                    val uri = data?.data as Uri
//                        val byteArrayOutputStream = ByteArrayOutputStream()
//                        img?.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream)
//                        val byteArray = byteArrayOutputStream.toByteArray()
//                    uri = saveFile(RandomFileName(), "image/jpeg", img)
                    mediaPath =
                        createCopyAndReturnRealPath(context = this, uri!!)!!
                    val file = File(uri.toString())
                    if(!file.exists()){
                        Log.d("failllll","file not found"+"uri"+uri.toString())
                    }
                    binding.iv.setImageURI(uri)
                }
            }
        }
    }
    @Nullable
    fun createCopyAndReturnRealPath(context: Context, uri: Uri): String? {
        val contentResolver: ContentResolver = context.getContentResolver() ?: return null


        // 파일 경로를 만듬
        val filePath: String = (context.getApplicationInfo().dataDir + File.separator
                + System.currentTimeMillis())
        val file = File(filePath)
        try {
            // 매개변수로 받은 uri 를 통해  이미지에 필요한 데이터를 불러 들인다.
            val inputStream = contentResolver.openInputStream(uri) ?: return null
            // 이미지 데이터를 다시 내보내면서 file 객체에  만들었던 경로를 이용한다.
            val outputStream: OutputStream = FileOutputStream(file)
            val buf = ByteArray(1024)
            var len: Int
            while (inputStream.read(buf).also { len = it } > 0) outputStream.write(buf, 0, len)
            outputStream.close()
            inputStream.close()
        } catch (ignore: IOException) {
            return null
        }
        return file.getAbsolutePath()
    }
}