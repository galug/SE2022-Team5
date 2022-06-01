package com.example.ottzzang.ui


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.ottzzang.R
import com.example.ottzzang.databinding.FragmentMyPageBinding


class MyPageFragment : Fragment() {
    private lateinit var binding : FragmentMyPageBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyPageBinding.inflate(inflater,container,false)

        binding.addPostBtn.setOnClickListener{
            val intent = Intent(getActivity(), AddPostActivity::class.java)
            startActivity(intent)
        }
        return binding.root
    }

}