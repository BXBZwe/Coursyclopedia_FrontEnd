package com.example.courscyclopedia.ui.users.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.courscyclopedia.databinding.FragmentSubjectDetailBinding
import com.example.courscyclopedia.model.Subject
import com.example.courscyclopedia.model.SubjectUpdateRequest
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
    private var isEditMode = false
    private var currentSubject: Subject? = null // Add this line



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentSubjectDetailBinding.inflate(inflater, container, false)
        setupViewModel()
        return binding.root
    }

    private fun setupViewModel() {
        val subjectsRepository = SubjectsRepository(RetrofitClient.apiService)
        val userRepository = UserRepository(RetrofitClient.apiService)
        val factory = SubjectDetailViewModelFactory(subjectsRepository, userRepository)
        viewModel = ViewModelProvider(this, factory)[SubjectDetailViewModel::class.java]
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setClickListeners()

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

        viewModel.subjectDetails.observe(viewLifecycleOwner) { subject ->
            subject?.let {
                currentSubject = it // Update currentSubject with the fetched subject
                populateSubjectDetails(subject)
            }
        }


    }

    private fun setClickListeners() {
        binding.buttonEdit.setOnClickListener {
            toggleEditMode()
        }
        binding.wishlistIcon.setOnClickListener {
            addToWishlist()
        }
        binding.likeicon.setOnClickListener {
            addLikeToSubject()
        }
    }

    private fun toggleEditMode() {
        isEditMode = !isEditMode
        setEditingMode(isEditMode)
    }

    private fun setEditingMode(isEditing: Boolean) {
        binding.apply {
            editTextSubjectName.visibility = if (isEditing) View.VISIBLE else View.GONE
            editTextSubjectcode.visibility = if (isEditing) View.VISIBLE else View.GONE
            editsubjectDescription.visibility = if (isEditing) View.VISIBLE else View.GONE
            editcampus.visibility = if (isEditing) View.VISIBLE else View.GONE
            editTextCredit.visibility = if (isEditing) View.VISIBLE else View.GONE

            // Set visibility for other EditTexts accordingly

            subjectNameTextView.visibility = if (isEditing) View.GONE else View.VISIBLE
            subjectCodeTextView.visibility = if (isEditing) View.GONE else View.VISIBLE
            subjectDescriptionTextView.visibility = if (isEditing) View.GONE else View.VISIBLE
            campusTextView.visibility = if (isEditing) View.GONE else View.VISIBLE
            creditTextView.visibility = if (isEditing) View.GONE else View.VISIBLE
            likesTextView.visibility = if (isEditing) View.GONE else View.VISIBLE
            likeicon.visibility = if (isEditing) View.GONE else View.VISIBLE
            wishlistIcon.visibility = if (isEditing) View.GONE else View.VISIBLE

            buttonEdit.text = if (isEditing) "Save" else "Edit"
        }
        if (!isEditing) {
            tryToUpdateSubject()
        }
    }

    private fun addLikeToSubject() {
        val currentUser = FirebaseAuth.getInstance().currentUser
        val userEmail = currentUser?.email ?: "No Email"
        if (userEmail.isNotEmpty()) {
            // Assuming you have a method in your ViewModel to handle likes
            viewModel.addLikeToSubject(args.subjectId, userEmail)
            Toast.makeText(context, "Liked", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "User not found", Toast.LENGTH_SHORT).show()
        }
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


    private fun tryToUpdateSubject() {
        currentSubject?.let { subj ->
            val updateRequest = SubjectUpdateRequest(
                name = binding.editTextSubjectName.text.toString().takeIf { it.isNotBlank() } ?: subj.subjectname,
                subjectCode = binding.editTextSubjectcode.text.toString().takeIf { it.isNotBlank() } ?: subj.subjectCode,
                subjectDescription = binding.editsubjectDescription.text.toString().takeIf { it.isNotBlank() } ?: subj.subjectDescription,
                campus = binding.editcampus.text.toString().takeIf { it.isNotBlank() } ?: subj.campus,
//                credit = binding.editTextCredit.text.toString().toIntOrNull() ?: subj.credit
            )
            viewModel.updateSubject(args.subjectId, updateRequest)
        }
    }

    private fun populateSubjectDetails(subject: Subject) {
        binding.apply {
            subjectNameTextView.text = subject.subjectname
            subjectCodeTextView.text = subject.subjectCode
            subjectDescriptionTextView.text = subject.subjectDescription
            campusTextView.text = subject.campus
            creditTextView.text = subject.credit?.toString() ?: "N/A" // Handle potential null
            likesTextView.text = subject.likes?.toString() ?: "0" // Example for handling null likes

            // Populate EditTexts for editing mode
            editTextSubjectName.setText(subject.subjectname)
            editTextSubjectcode.setText(subject.subjectCode) // Ensure correct ID
            editsubjectDescription.setText(subject.subjectDescription) // Ensure correct ID
            editcampus.setText(subject.campus) // Ensure correct ID
            editTextCredit.setText(subject.credit?.toString()) // Ensure this is the actual credit value, handle null

            // Update currentSubject reference
        }
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

