package com.example.courscyclopedia.ui.users.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.courscyclopedia.R
import com.example.courscyclopedia.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth
import androidx.navigation.fragment.findNavController

//
//class ProfileFragment : Fragment() {
//    private var _binding: FragmentProfileBinding? = null
//    private val binding get() = _binding!!
//
//    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
//        _binding = FragmentProfileBinding.inflate(inflater, container, false)
//        return binding.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        displayProfileInfo()
//    }
//
//    private fun displayProfileInfo() {
//        val currentUser = FirebaseAuth.getInstance().currentUser
//        binding.profileNameTextView.text = currentUser?.displayName ?: "No Name"
//        binding.profileEmailTextView.text = currentUser?.email ?: "No Email"
//    }
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
//}


class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null // Assuming you're using View Binding
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        displayProfileInfo()

        // Set click listener for Wishlist TextView
//        binding.profileWishlistTextView.setOnClickListener {
//            navigateToWishlist()
//        }
    }

//    private fun navigateToWishlist() {
//        findNavController().navigate(R.id.userWishlistFragment)
//    }

    private fun displayProfileInfo() {
        val currentUser = FirebaseAuth.getInstance().currentUser
        binding.profileNameTextView.text = currentUser?.displayName ?: "No Name"
        binding.profileEmailTextView.text = currentUser?.email ?: "No Email"
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
