package com.example.courscyclopedia.ui.users.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.courscyclopedia.model.CreateSubjectResponse
import com.example.courscyclopedia.model.Major
import com.example.courscyclopedia.model.NewSubjectRequest
import com.example.courscyclopedia.model.Subject
import com.example.courscyclopedia.repository.SubjectsRepository
import kotlinx.coroutines.launch

class SubjectsViewModel(private val repository: SubjectsRepository) : ViewModel() {

    private val _majors = MutableLiveData<List<Major>>()
    val majors: LiveData<List<Major>> = _majors
    private val _createSubjectResult = MutableLiveData<CreateSubjectResponse?>()
    val createSubjectResult: LiveData<CreateSubjectResponse?> = _createSubjectResult





    private val _subjects = MutableLiveData<List<Subject>>()
    val subjects: LiveData<List<Subject>> = _subjects

    // Example function to fetch majors for a faculty
    fun fetchMajorsForFaculty(facultyId: String) {
        viewModelScope.launch {
            // Repository function to get majors by facultyId
            // This is a suspend function within your repository
            val majorsList = repository.getMajorsForFaculty(facultyId)
            _majors.value = majorsList
        }
    }

    // Example function to fetch subjects for a major
    fun fetchSubjectsForMajor(majorId: String) {
        viewModelScope.launch {
            // Repository function to get subjects by majorId
            // This is a suspend function within your repository
            val subjectsList = repository.getSubjectsForMajor(majorId)
            _subjects.value = subjectsList
        }
    }

    fun loadSubjectsForSelectedFaculty(facultyId: String) {
        viewModelScope.launch {
            val allSubjectsForFaculty = mutableListOf<Subject>()

            try {
                // Fetch majors for the selected faculty
                val majorsList = repository.getMajorsForFaculty(facultyId)
                _majors.value = majorsList

                // For each major, fetch the subjects and add them to the list
                majorsList.forEach { major ->
                    val subjectsList = repository.getSubjectsForMajor(major.id)
                    allSubjectsForFaculty.addAll(subjectsList)
                }

                // Post the combined list of all subjects to the LiveData
                _subjects.value = allSubjectsForFaculty
            } catch (e: Exception) {
                // Handle any exceptions here, such as by posting an error message to the UI
            }
        }
    }

    fun createSubject(newSubjectRequest: NewSubjectRequest) {
        viewModelScope.launch {
            try {
                val response = repository.createSubject(newSubjectRequest)
                _createSubjectResult.value = response
                // Optionally, you might want to add some logic to clear the LiveData after it's been handled
            } catch (e: Exception) {
                // Handle the error, e.g., by posting a null or a specific error object to the LiveData
                _createSubjectResult.value = null
            }
        }
    }

    fun fetchMajors() {
        viewModelScope.launch {
            try {
                val majorsList = repository.getMajors() // Replace with actual repository call
                _majors.postValue(majorsList)
            } catch (e: Exception) {
                // Handle the exception, e.g. by posting an error message
            }
        }
    }
}
