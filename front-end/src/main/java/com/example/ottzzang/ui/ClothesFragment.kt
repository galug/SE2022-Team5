package com.example.ottzzang.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.ottzzang.R
import com.example.ottzzang.databinding.FragmentClothesBinding
import com.example.ottzzang.databinding.FragmentPostBinding


class ClothesFragment : Fragment() {

    private lateinit var binding: FragmentClothesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentClothesBinding.inflate(inflater, container, false)



        binding.addClothesBtn.setOnClickListener{
            val intent = Intent(getActivity(), AddClothesActivity::class.java)
            startActivity(intent)
        }
        return binding.root
    }
}