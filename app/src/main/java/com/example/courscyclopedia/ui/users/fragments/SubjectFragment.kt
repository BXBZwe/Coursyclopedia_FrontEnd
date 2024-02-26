package com.example.courscyclopedia.ui.users.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.courscyclopedia.R
import com.example.courscyclopedia.databinding.FragmentSubjectsBinding
import com.example.courscyclopedia.network.RetrofitClient
import com.example.courscyclopedia.repository.SubjectsRepository
import com.example.courscyclopedia.ui.users.adapter.SubjectsAdapter
import com.example.courscyclopedia.ui.users.viewmodels.SubjectsViewModel
import com.example.courscyclopedia.ui.users.viewmodels.SubjectsViewModelFactory

class SubjectsFragment : Fragment(R.layout.fragment_subjects) {  // Assuming fragment_subjects is your XML layout

    private lateinit var viewModel: SubjectsViewModel
    private var _binding: FragmentSubjectsBinding? = null
    private val binding get() = _binding!!
    private lateinit var subjectsAdapter: SubjectsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSubjectsBinding.bind(view)

        // Initialize the adapter
        subjectsAdapter = SubjectsAdapter()
        binding.recyclerViewSubjects.adapter = subjectsAdapter
        binding.recyclerViewSubjects.layoutManager = LinearLayoutManager(context)

        // ViewModel setup
        val repository = SubjectsRepository(RetrofitClient.apiService)
        val factory = SubjectsViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[SubjectsViewModel::class.java]

        // Rest of the code...
        val facultyId = arguments?.let { SubjectsFragmentArgs.fromBundle(it).facultyId }
            ?: throw IllegalArgumentException("Must pass facultyId")


        // Fetch the majors for the faculty
        viewModel.fetchMajorsForFaculty(facultyId)

        // Observe the LiveData for majors
        viewModel.majors.observe(viewLifecycleOwner) { majors ->
            // Handle multiple majors here
            // For simplicity, fetching subjects for the first major
            if (majors.isNotEmpty()) {
                viewModel.fetchSubjectsForMajor(majors.first().id)
            }
        }

        // Observe the LiveData for subjects
        viewModel.subjects.observe(viewLifecycleOwner) { subjects ->
            // Update the adapter with the list of subjects
            subjectsAdapter.submitList(subjects)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
