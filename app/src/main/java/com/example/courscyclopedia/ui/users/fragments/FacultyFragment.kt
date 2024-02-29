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
import com.example.courscyclopedia.repository.SubjectsRepository
import com.example.courscyclopedia.ui.users.adapter.FacultyAdapter
import com.example.courscyclopedia.ui.users.viewmodels.FacultyViewModel
import com.example.courscyclopedia.ui.users.viewmodels.FacultyViewModelFactory
import com.example.courscyclopedia.ui.users.viewmodels.SubjectsViewModel
import com.example.courscyclopedia.ui.users.viewmodels.SubjectsViewModelFactory

class FacultyFragment : Fragment() {
    private var _binding: FragmentFacultyBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: FacultyViewModel
    private lateinit var subjectsViewModel: SubjectsViewModel


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentFacultyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Initialize the ViewModel
        val apiService = RetrofitClient.apiService
        val facultyRepository = FacultyRepository(apiService)
        val subjectsRepository = SubjectsRepository(apiService) // This line was missing
        val viewModelFactory = FacultyViewModelFactory(facultyRepository)
        val subjectsViewModelFactory = SubjectsViewModelFactory(subjectsRepository) // Now you have the repository for this factory

        viewModel = ViewModelProvider(this, viewModelFactory)[FacultyViewModel::class.java]
        subjectsViewModel = ViewModelProvider(this, subjectsViewModelFactory)[SubjectsViewModel::class.java]


        viewModel.faculties.observe(viewLifecycleOwner) { faculties ->
            if (faculties.isNotEmpty()) {
                val adapter = FacultyAdapter(faculties) { faculty ->
                    subjectsViewModel.loadSubjectsForSelectedFaculty(faculty.id)

                    val action = FacultyFragmentDirections.actionFacultyFragmentToSubjectsFragment(faculty.id)
                    findNavController().navigate(action)
                }
                binding.rvFaculties.adapter = adapter
                binding.rvFaculties.layoutManager = LinearLayoutManager(context)
            } else {
                Log.d("FacultyFragment", "No faculties received")
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Clear the binding when the view is destroyed
    }
}
