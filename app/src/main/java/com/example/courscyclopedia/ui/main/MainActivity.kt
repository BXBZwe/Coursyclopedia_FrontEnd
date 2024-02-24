package com.example.courscyclopedia.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.courscyclopedia.databinding.ActivityMainBinding
import com.example.courscyclopedia.viewmodel.FacultyViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var facultyViewModel: FacultyViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize ViewModel
        facultyViewModel = ViewModelProvider(this)[FacultyViewModel::class.java]

        // Observe the LiveData from ViewModel
        facultyViewModel.faculties.observe(this) { faculties ->
            // Update UI using View Binding
            binding.tvFacultyData.text = faculties.joinToString(separator = "\n") { it.facultyName }
        }

        // Fetch the faculties
        facultyViewModel.fetchFaculties()
    }
}
