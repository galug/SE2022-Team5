package com.example.ottzzang.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.database.Cursor
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
import com.example.ottzzang.databinding.ActivityAddClothesBinding
import com.example.ottzzang.model.*
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
import java.io.*
import java.text.SimpleDateFormat


class AddClothesActivity : AppCompatActivity() {

    lateinit var binding:ActivityAddClothesBinding
    var uri: Uri? =null
    lateinit var mediaPath:String
    var season:Int =0
    var bigCategory:Int=0
    var smallCategory: Int=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddClothesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        settingPermission()
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
        binding.saveBtn.setOnClickListener{
            val userIdx = RequestBody.create(MediaType.parse("text/plain"),UserIndex.userIdx.toString())
            val season = RequestBody.create(MediaType.parse("text/plain"),season.toString())
            val bigCategory = RequestBody.create(MediaType.parse("text/plain"),bigCategory.toString())
            val smallCategory = RequestBody.create(MediaType.parse("text/plain"),smallCategory.toString())
            val color = RequestBody.create(MediaType.parse("text/plain"),binding.color.text.toString())
            val file = File(mediaPath)
            val fileBody = RequestBody.create(MediaType.parse("image/jpeg"),file)
            val filePart = MultipartBody.Part.createFormData("imgFile",uri.toString(),fileBody)
            clothesService.requestAddClothes(filePart,userIdx,season,bigCategory,smallCategory,color).enqueue(object: Callback<PostClothesRes> {
                override fun onFailure(call: Call<PostClothesRes>, t: Throwable) {
                    t.message?.let { it1 -> Log.e("LOGIN", it1) }
                    var dialog = AlertDialog.Builder(this@AddClothesActivity)
                    dialog.setTitle("??????")
                    dialog.setMessage("????????????????????????.")
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
                        finish()
                    }
                    else{
                        var dialog = AlertDialog.Builder(this@AddClothesActivity)
                        Log.d("?????? ??????????","" +clothesRes?.message)
                        dialog.setTitle("" + clothesRes?.code)
                        dialog.setMessage("????????? ?????? ")
                        dialog.show()
                    }
                }
            })
        }
    }

    fun settingPermission(){//????????? ?????? ?????? ??????
        var permis = object  : PermissionListener {
            //            ????????? ????????? ???????????? ?????? ???????????? ????????? ???????????? ?????? ????????? ?????? ??????
            override fun onPermissionGranted() {
                Toast.makeText(this@AddClothesActivity,"?????? ??????", Toast.LENGTH_SHORT)
                    .show()
            }

            override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
                Toast.makeText(this@AddClothesActivity, "?????? ??????", Toast.LENGTH_SHORT)
                    .show()
                ActivityCompat.finishAffinity(this@AddClothesActivity) // ?????? ????????? ??? ??????
            }
        }

        TedPermission.with(this)
            .setPermissionListener(permis)
            .setRationaleMessage("????????? ?????? ?????? ??????")
            .setDeniedMessage("????????? ?????? ?????? ??????")
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
    fun captureCamera(){ //???????????? ?????? ???????????? ?????????
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

    fun RandomFileName() : String //FIle????????? ????????? ?????? ???????????? ????????? ????????? ???????????? ??????
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


        // ?????? ????????? ??????
        val filePath: String = (context.getApplicationInfo().dataDir + File.separator
                + System.currentTimeMillis())
        val file = File(filePath)
        try {
            // ??????????????? ?????? uri ??? ??????  ???????????? ????????? ???????????? ?????? ?????????.
            val inputStream = contentResolver.openInputStream(uri) ?: return null
            // ????????? ???????????? ?????? ??????????????? file ?????????  ???????????? ????????? ????????????.
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