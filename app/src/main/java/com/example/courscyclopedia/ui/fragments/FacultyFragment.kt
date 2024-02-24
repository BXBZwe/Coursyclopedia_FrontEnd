package com.example.courscyclopedia.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.courscyclopedia.databinding.FragmentFacultyBinding
import com.example.courscyclopedia.repository.FacultyRepository
import com.example.courscyclopedia.ui.adapter.FacultyAdapter
import com.example.courscyclopedia.ui.viewmodels.FacultyViewModelFactory
import com.example.courscyclopedia.ui.viewmodels.FacultyViewmodel

class FacultyFragment : Fragment() {
    private var _binding: FragmentFacultyBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: FacultyViewmodel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentFacultyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Initialize the ViewModel
        val facultyRepository = FacultyRepository()
        val viewModelFactory = FacultyViewModelFactory(facultyRepository)
        viewModel = ViewModelProvider(this, viewModelFactory)[FacultyViewmodel::class.java]

        // Observe the LiveData from the ViewModel
        viewModel.faculties.observe(viewLifecycleOwner) { faculties ->
            if (faculties.isNotEmpty()) {
                // If we have faculties, then update the adapter with the new list
                val adapter = FacultyAdapter(faculties)
                binding.rvFaculties.adapter = adapter
                binding.rvFaculties.layoutManager = LinearLayoutManager(context)
            } else {
                // If the list is empty, log the event or handle the empty state appropriately
                Log.d("FacultyFragment", "No faculties received")
                // TODO: Show an empty state message or image to the user
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Clear the binding when the view is destroyed
    }
}
