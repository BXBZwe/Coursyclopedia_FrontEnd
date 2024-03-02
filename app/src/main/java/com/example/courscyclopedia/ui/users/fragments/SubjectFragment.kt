package com.example.courscyclopedia.ui.users.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.courscyclopedia.R
import com.example.courscyclopedia.databinding.FragmentSubjectsBinding
import com.example.courscyclopedia.network.RetrofitClient
import com.example.courscyclopedia.repository.SubjectsRepository
import com.example.courscyclopedia.ui.users.adapter.ProfessorSubjectsAdapter
import com.example.courscyclopedia.ui.users.adapter.SubjectsAdapter
import com.example.courscyclopedia.ui.users.viewmodels.SubjectsViewModel
import com.example.courscyclopedia.ui.users.viewmodels.SubjectsViewModelFactory

class SubjectsFragment : Fragment(R.layout.fragment_subjects) {
    private var _binding: FragmentSubjectsBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: SubjectsViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSubjectsBinding.bind(view)

        // Setup ViewModel
        val apiService = RetrofitClient.apiService
        val repository = SubjectsRepository(apiService)
        val viewModelFactory = SubjectsViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory)[SubjectsViewModel::class.java]

        // Setup RecyclerView and Adapter with click listener
        val subjectsAdapter = SubjectsAdapter { subject ->
            subject.id.let {
                val action =
                    SubjectsFragmentDirections.actionSubjectsFragmentToSubjectDetailFragment(it)
                findNavController().navigate(action)
            } ?: Log.e("SubjectsFragment", "Subject ID is null")
        }


        binding.recyclerViewSubjects.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = subjectsAdapter
        }

        // Fetch subjects for the selected faculty
        val facultyId = arguments?.let { SubjectsFragmentArgs.fromBundle(it).facultyId }
            ?: throw IllegalArgumentException("Must pass facultyId")
        viewModel.loadSubjectsForSelectedFaculty(facultyId)

        // Observe subjects LiveData
        viewModel.subjects.observe(viewLifecycleOwner) { subjects ->
            subjectsAdapter.submitList(subjects)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
