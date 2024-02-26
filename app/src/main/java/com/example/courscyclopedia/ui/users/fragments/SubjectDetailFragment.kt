package com.example.courscyclopedia.ui.users.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.courscyclopedia.databinding.FragmentSubjectDetailBinding
import com.example.courscyclopedia.network.RetrofitClient
import com.example.courscyclopedia.repository.SubjectsRepository
import com.example.courscyclopedia.ui.users.viewmodels.SubjectDetailViewModel
import com.example.courscyclopedia.ui.users.viewmodels.SubjectDetailViewModelFactory

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
        // Initialize your ViewModel here (consider using ViewModelProvider)
        val factory = SubjectDetailViewModelFactory(SubjectsRepository(RetrofitClient.apiService))
        viewModel = ViewModelProvider(this, factory).get(SubjectDetailViewModel::class.java)

        // Fetch subject details
        viewModel.fetchSubjectDetails(args.subjectId)

        // Observe the LiveData object in ViewModel for subject details
        viewModel.subjectDetails.observe(viewLifecycleOwner) { subject ->
            // Now use the subject details to update the UI
            if (subject != null) {
                binding.subjectNameTextView.text = subject.subjectname
                binding.subjectCodeTextView.text = subject.subjectCode
                binding.subjectDescriptionTextView.text = subject.subjectDescription
                binding.campusTextView.text = subject.campus
                binding.creditTextView.text = subject.credit.toString()
                binding.likesTextView.text = subject.likes.toString()
                binding.availableDurationTextView.text = subject.availableDuration.toString()            }
            // Update other UI elements with subject details...
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

