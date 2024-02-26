package com.example.courscyclopedia.ui.users.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.courscyclopedia.databinding.FragmentFacultyBinding
import com.example.courscyclopedia.network.RetrofitClient
import com.example.courscyclopedia.repository.FacultyRepository
import com.example.courscyclopedia.ui.users.adapter.FacultyAdapter
import com.example.courscyclopedia.ui.users.viewmodels.FacultyViewModel
import com.example.courscyclopedia.ui.users.viewmodels.FacultyViewModelFactory

class FacultyFragment : Fragment() {
    private var _binding: FragmentFacultyBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: FacultyViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentFacultyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Initialize the ViewModel
        val apiService = RetrofitClient.apiService
        val facultyRepository = FacultyRepository(apiService)
        val viewModelFactory = FacultyViewModelFactory(facultyRepository)
        viewModel = ViewModelProvider(this, viewModelFactory)[FacultyViewModel::class.java]

        // Observe the LiveData from the ViewModel
        viewModel.faculties.observe(viewLifecycleOwner) { faculties ->
            if (faculties.isNotEmpty()) {
                // Pass the click listener to the adapter
                val adapter = FacultyAdapter(faculties) { faculty ->
                    // When a faculty item is clicked, navigate to the SubjectsFragment with the faculty ID
                    val action = FacultyFragmentDirections.actionFacultyFragmentToSubjectsFragment(faculty.id)
                    findNavController().navigate(action)
                }
                binding.rvFaculties.adapter = adapter
                binding.rvFaculties.layoutManager = LinearLayoutManager(context)
            } else {
                // Handle empty state
                Log.d("FacultyFragment", "No faculties received")
            }
        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Clear the binding when the view is destroyed
    }
}
