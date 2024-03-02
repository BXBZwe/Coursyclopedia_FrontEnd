package com.example.courscyclopedia.ui.users.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.courscyclopedia.databinding.FragmentSubjectDetailBinding
import com.example.courscyclopedia.network.RetrofitClient
import com.example.courscyclopedia.repository.SubjectsRepository
import com.example.courscyclopedia.repository.UserRepository
import com.example.courscyclopedia.ui.users.viewmodels.SubjectDetailViewModel
import com.example.courscyclopedia.ui.users.viewmodels.SubjectDetailViewModelFactory
import com.google.firebase.auth.FirebaseAuth

class SubjectDetailFragment : Fragment() {
    private var _binding: FragmentSubjectDetailBinding? = null
    private val binding get() = _binding!!
    private val args: SubjectDetailFragmentArgs by navArgs()
    private lateinit var viewModel: SubjectDetailViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentSubjectDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val subjectsRepository = SubjectsRepository(RetrofitClient.apiService)
        val userRepository = UserRepository(RetrofitClient.apiService)  // Initialize UserRepository
        val factory = SubjectDetailViewModelFactory(subjectsRepository, userRepository)

        viewModel = ViewModelProvider(this, factory).get(SubjectDetailViewModel::class.java)

        viewModel.fetchSubjectDetails(args.subjectId)

        viewModel.subjectDetails.observe(viewLifecycleOwner) { subject ->
            subject?.let {
                binding.subjectNameTextView.text = subject.subjectname
                binding.subjectCodeTextView.text = subject.subjectCode
                binding.subjectDescriptionTextView.text = subject.subjectDescription
                binding.campusTextView.text = subject.campus
                binding.creditTextView.text = subject.credit.toString()
                binding.likesTextView.text = subject.likes.toString()
                binding.availableDurationTextView.text = subject.availableDuration.toString()
            }
        }

        binding.wishlistIcon.setOnClickListener {
            addToWishlist()
        }
// Retrieve logged-in email from Firebase Authentication and set it to the binding variable
        val currentUser = FirebaseAuth.getInstance().currentUser
        val userEmail = currentUser?.email ?: "No Email"
        binding.loggedEmail.text = userEmail

    }

    private fun addToWishlist() {
        val currentUser = FirebaseAuth.getInstance().currentUser
        val userEmail = currentUser?.email ?: "No Email"
        if (userEmail != null && userEmail.isNotEmpty()) {
            viewModel.addToWishlist(args.subjectId, userEmail)
            Toast.makeText(context, "Added to wishlist", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "User not found", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


//=-------------------------------------------
//package com.example.courscyclopedia.ui.users.fragments
//
//import android.content.Context
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.Toast
//import androidx.fragment.app.Fragment
//import androidx.lifecycle.ViewModelProvider
//import androidx.navigation.fragment.navArgs
//import com.example.courscyclopedia.databinding.FragmentSubjectDetailBinding
//import com.example.courscyclopedia.network.RetrofitClient
//import com.example.courscyclopedia.repository.SubjectsRepository
//import com.example.courscyclopedia.repository.UserRepository
//import com.example.courscyclopedia.ui.users.viewmodels.SubjectDetailViewModel
//import com.example.courscyclopedia.ui.users.viewmodels.SubjectDetailViewModelFactory
//
//class SubjectDetailFragment : Fragment() {
//    private var _binding: FragmentSubjectDetailBinding? = null
//    private val binding get() = _binding!!
//    private val args: SubjectDetailFragmentArgs by navArgs()
//    private lateinit var viewModel: SubjectDetailViewModel
//
//    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
//        _binding = FragmentSubjectDetailBinding.inflate(inflater, container, false)
//        return binding.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        val subjectsRepository = SubjectsRepository(RetrofitClient.apiService)
//        val userRepository = UserRepository(RetrofitClient.apiService)  // Initialize UserRepository
//        val factory = SubjectDetailViewModelFactory(subjectsRepository, userRepository)
//
//        viewModel = ViewModelProvider(this, factory).get(SubjectDetailViewModel::class.java)
//
//        viewModel.fetchSubjectDetails(args.subjectId)
//
//        viewModel.subjectDetails.observe(viewLifecycleOwner) { subject ->
//            subject?.let {
//                binding.subjectNameTextView.text = subject.subjectname
//                binding.subjectCodeTextView.text = subject.subjectCode
//                binding.subjectDescriptionTextView.text = subject.subjectDescription
//                binding.campusTextView.text = subject.campus
//                binding.creditTextView.text = subject.credit.toString()
//                binding.likesTextView.text = subject.likes.toString()
//                binding.availableDurationTextView.text = subject.availableDuration.toString()
//            }
//        }
//
//        binding.wishlistIcon.setOnClickListener {
//            addToWishlist()
//        }
//    }
//
//    private fun addToWishlist() {
//        val sharedPreferences = requireActivity().getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
//        val userEmail = sharedPreferences.getString("userEmail", "")  // Assume "userEmail" is saved in SharedPreferences
//
//        if (userEmail != null && userEmail.isNotEmpty()) {
//            viewModel.addToWishlist(args.subjectId, userEmail)
//            Toast.makeText(context, "Added to wishlist", Toast.LENGTH_SHORT).show()
//        } else {
//            Toast.makeText(context, "User not found", Toast.LENGTH_SHORT).show()
//        }
//    }
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
//}
//

//package com.example.courscyclopedia.ui.users.fragments
//
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.fragment.app.Fragment
//import androidx.lifecycle.ViewModelProvider
//import androidx.navigation.fragment.navArgs
//import com.example.courscyclopedia.databinding.FragmentSubjectDetailBinding
//import com.example.courscyclopedia.network.RetrofitClient
//import com.example.courscyclopedia.repository.SubjectsRepository
//import com.example.courscyclopedia.ui.users.viewmodels.SubjectDetailViewModel
//import com.example.courscyclopedia.ui.users.viewmodels.SubjectDetailViewModelFactory
//
//class SubjectDetailFragment : Fragment() {
//    private var _binding: FragmentSubjectDetailBinding? = null
//    private val binding get() = _binding!!
//    private val args: SubjectDetailFragmentArgs by navArgs()
//    private lateinit var viewModel: SubjectDetailViewModel
//
//    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
//        _binding = FragmentSubjectDetailBinding.inflate(inflater, container, false)
//        return binding.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        // Initialize your ViewModel here (consider using ViewModelProvider)
//        val factory = SubjectDetailViewModelFactory(SubjectsRepository(RetrofitClient.apiService))
//        viewModel = ViewModelProvider(this, factory).get(SubjectDetailViewModel::class.java)
//
//        // Fetch subject details
//        viewModel.fetchSubjectDetails(args.subjectId)
//
//        // Observe the LiveData object in ViewModel for subject details
//        viewModel.subjectDetails.observe(viewLifecycleOwner) { subject ->
//            // Now use the subject details to update the UI
//            if (subject != null) {
//                binding.subjectNameTextView.text = subject.subjectname
//                binding.subjectCodeTextView.text = subject.subjectCode
//                binding.subjectDescriptionTextView.text = subject.subjectDescription
//                binding.campusTextView.text = subject.campus
//                binding.creditTextView.text = subject.credit.toString()
//                binding.likesTextView.text = subject.likes.toString()
//                binding.availableDurationTextView.text = subject.availableDuration.toString()            }
//            // Update other UI elements with subject details...
//        }
//    }
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
//}
//
