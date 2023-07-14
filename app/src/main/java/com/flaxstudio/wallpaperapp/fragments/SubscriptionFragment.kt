package com.flaxstudio.wallpaperapp.fragments

import android.os.Binder
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.flaxstudio.wallpaperapp.databinding.FragmentSubscriptionBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class SubscriptionFragment : Fragment() {

    private lateinit var binding : FragmentSubscriptionBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSubscriptionBinding.inflate(layoutInflater , container , false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val db = FirebaseFirestore.getInstance()
        val userCollection = db.collection("users")
        val auth = Firebase.auth
        val currUser = auth.currentUser!!.uid

        binding.btnPurchase.setOnClickListener {

           userCollection.document(currUser).update("startDate" , System.currentTimeMillis())
            Log.i("TAG" , "currUser : $")
            Toast.makeText(requireContext() , "Subscription Added" , Toast.LENGTH_SHORT).show()

        }

    }


}