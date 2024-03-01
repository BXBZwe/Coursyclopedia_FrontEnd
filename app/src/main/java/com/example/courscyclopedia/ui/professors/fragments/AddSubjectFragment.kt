package com.example.courscyclopedia.ui.professors.fragments

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.courscyclopedia.databinding.FragmentAddSubjectBinding
import com.example.courscyclopedia.model.Major
import com.example.courscyclopedia.model.NewSubjectRequest
import com.example.courscyclopedia.network.RetrofitClient
import com.example.courscyclopedia.repository.SubjectsRepository
import com.example.courscyclopedia.ui.users.viewmodels.SubjectsViewModel
import com.example.courscyclopedia.ui.users.viewmodels.SubjectsViewModelFactory

class AddSubjectFragment : Fragment() {

    // Using view binding to access the views
    private var _binding: FragmentAddSubjectBinding? = null
    private val binding get() = _binding!!
    val apiService = RetrofitClient.apiService
    private var majorsList: List<Major> = emptyList()


    // Instantiate the ViewModel
    private val subjectsViewModel: SubjectsViewModel by viewModels {
        SubjectsViewModelFactory(SubjectsRepository(apiService)) // Provide your apiService instance here
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddSubjectBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subjectsViewModel.fetchMajors()

        subjectsViewModel.majors.observe(viewLifecycleOwner) { majors ->
            this.majorsList = majors
            val majorNames = majors.map { it.majorName }


            // Create an ArrayAdapter using the string array and a default spinner layout
            val adapter = ArrayAdapter(requireContext(), R.layout.simple_spinner_item, majorNames)
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            binding.spinnerMajor.adapter = adapter
        }



        binding.buttonSubmit.setOnClickListener {
            // Gather the input data from the form fields
            val subjectCode = binding.editTextSubjectCode.text.toString()
            val subjectName = binding.editTextSubjectName.text.toString()
            val subjectDescription = binding.editTextSubjectDescription.text.toString()
            val campus = binding.editTextCampus.text.toString()
            val credit = binding.editTextCredit.text.toString().toIntOrNull() ?: 0
            // Add other fields as necessary

            // Assuming your spinner has been populated with Major objects that have an id property
            val selectedMajorPosition = binding.spinnerMajor.selectedItemPosition
            if (majorsList.isNotEmpty() && selectedMajorPosition >= 0 && selectedMajorPosition < majorsList.size) {
                val majorId = majorsList[selectedMajorPosition].id

                val newSubjectRequest = NewSubjectRequest(
                    majorId = majorId,
                    subjectCode = subjectCode,
                    name = subjectName,
                    subjectDescription = subjectDescription,
                    campus = campus,
                    credit = credit,
                    // Add other fields as necessary
                )

                // Call the ViewModel to create the new subject
                subjectsViewModel.createSubject(newSubjectRequest)
            } else {
                // Handle the case where the selection is invalid
                Toast.makeText(context, "Invalid major selection", Toast.LENGTH_LONG).show()
            }
        }

        // Observe the ViewModel for results
        subjectsViewModel.createSubjectResult.observe(viewLifecycleOwner) { result ->
            if (result != null) {
                Toast.makeText(context, "Subject created successfully!", Toast.LENGTH_LONG).show()
                // Navigate back or clear the form
            } else {
                Toast.makeText(context, "Failed to create subject", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
