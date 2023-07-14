package com.flaxstudio.wallpaperapp.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.flaxstudio.wallpaperapp.R
import com.flaxstudio.wallpaperapp.SubscriptionActivity
import com.flaxstudio.wallpaperapp.databinding.FragmentAccountBinding

class AccountFragment : Fragment() {
   private lateinit var binding: FragmentAccountBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentAccountBinding.inflate(inflater , container , false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.getPremium.setOnClickListener {
           val intent = Intent(activity, SubscriptionActivity::class.java)
            startActivity(intent)
        }
    }



}