//package com.example.courscyclopedia.ui.users.fragments
//
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
//        val role = activity?.intent?.getStringExtra("ROLE")
//
//        if (role != "STUDENT") {
//            // Hide or disable the like functionality for non-students
//            binding.likeImageView.visibility = View.GONE
//        } else {
//            // Setup OnClickListener for the like button as previously discussed
//            setupLikeButtonClickListener()
//        }
//        val factory = SubjectDetailViewModelFactory(SubjectsRepository(RetrofitClient.apiService))
//        viewModel = ViewModelProvider(this, factory).get(SubjectDetailViewModel::class.java)
//
//        viewModel.fetchSubjectDetails(args.subjectId)
//
//        viewModel.subjectDetails.observe(viewLifecycleOwner) { subject ->
//            if (subject != null) {
//                binding.subjectNameTextView.text = subject.subjectname
//                binding.subjectCodeTextView.text = subject.subjectCode
//                binding.subjectDescriptionTextView.text = subject.subjectDescription
//                binding.campusTextView.text = subject.campus
//                binding.creditTextView.text = subject.credit.toString()
//                binding.likesTextView.text = subject.likes.toString()
//                binding.availableDurationTextView.text = subject.availableDuration.toString()            }
//        }
//
//        binding.likeImageView.setOnClickListener {
//            // Call the updateLikes method on the viewModel, passing in the subject ID
//            viewModel.updateLikes(args.subjectId)
//        }
//
//
//        viewModel.updateLikesResult.observe(viewLifecycleOwner) { subjectDetailResponse ->
//            // Check if the response is not null
//            if (subjectDetailResponse != null) {
//                // Update the likes count on the UI by accessing the likes property from the data property
//                binding.likesTextView.text = subjectDetailResponse.data.likes.toString()
//                // Optionally, show some feedback to the user, like a Toast
//            } else {
//                // Handle the error case
//            }
//        }
//
//    }
//
//    private fun setupLikeButtonClickListener() {
//        binding.likeImageView.setOnClickListener {
//            // Perform the action to like the subject here
//            likeSubject()
//        }
//    }
//
//    private fun likeSubject() {
//        // Assuming you have a viewModel in your fragment that handles data logic
//        val subjectId = args.subjectId // Assuming you have the subject ID passed as an argument to the fragment
//        viewModel.updateLikes(subjectId).observe(viewLifecycleOwner) { result ->
//            when (result) {
//                is Result.Success -> {
//                    // Update UI to reflect the like
//                    updateLikeUI(result.data.likes)
//                    Toast.makeText(context, "Liked!", Toast.LENGTH_SHORT).show()
//                }
//                is Result.Error -> {
//                    // Handle error, maybe show a toast message
//                    Toast.makeText(context, "Error liking subject", Toast.LENGTH_SHORT).show()
//                }
//            }
//        }
//    }
//
//    private fun updateLikeUI(likes: Int) {
//        // Update your likes TextView or other UI elements as necessary
//        binding.likesTextView.text = likes.toString()
//    }
//
//
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
//}
//

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
//    private lateinit var  userViewModel: UserViewModel

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
//// Retrieve logged-in email from Firebase Authentication and set it to the binding variable
//        val currentUser = FirebaseAuth.getInstance().currentUser
//        val userEmail = currentUser?.email ?: "No Email"
//        binding.loggedEmail.text = userEmail

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


    private fun observeSubjectDetails() {
        viewModel.fetchSubjectDetails(args.subjectId)
        viewModel.subjectDetails.observe(viewLifecycleOwner) { subject ->
            subject?.let {
                with(binding) {
                    subjectNameTextView.text = subject.subjectname
                    subjectCodeTextView.text = subject.subjectCode
                    subjectDescriptionTextView.text = subject.subjectDescription
                    campusTextView.text = subject.campus
                    creditTextView.text = subject.credit.toString()
                    likesTextView.text = subject.likes.toString()
                    availableDurationTextView.text = subject.availableDuration.toString()
                }
            }
        }
    }

//    private fun setupLikeButtonClickListener() {
//        // Retrieve the user's email, possibly from SharedPreferences, a passed argument, or any other method you've implemented
//        val userEmail = getUserEmail(requireContext()) ?: return // If null, email is not set, handle accordingly
//
//        binding.likeImageView.setOnClickListener {
//            viewModel.addLike(args.subjectId, userEmail)
//        }
//
//        // Observe the LiveData for like status updates from the ViewModel
//        viewModel.likeStatus.observe(viewLifecycleOwner) { likeStatus ->
//            when (likeStatus) {
//                "Liked" -> {
//                    // Here, you might want to re-fetch or update the subject detail to reflect the new likes count
//                    // For now, just show a success message
//                    Toast.makeText(context, "Liked!", Toast.LENGTH_SHORT).show()
//                }
//                else -> {
//                    // This covers the "Error" case and any exception messages
//                    Toast.makeText(context, "Error liking subject: $likeStatus", Toast.LENGTH_SHORT).show()
//                }
//            }
//        }
//    }



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
