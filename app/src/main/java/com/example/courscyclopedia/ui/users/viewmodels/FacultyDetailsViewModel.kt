//package com.example.courscyclopedia.ui.users.viewmodels
//
//import androidx.lifecycle.MutableLiveData
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import com.example.courscyclopedia.model.Major
//import com.example.courscyclopedia.model.Subject
//import com.example.courscyclopedia.repository.MajorRepository
//import kotlinx.coroutines.launch
//
//class FacultyDetailsViewModel(private val majorRepository: MajorRepository) : ViewModel() {
//
//    val majors = MutableLiveData<List<Major>>()
//    val subjects = MutableLiveData<List<Subject>>()
//
//    fun fetchMajorsForFaculty(facultyId: String) {
//        viewModelScope.launch {
//            val fetchedMajors = majorRepository.getMajorsForFaculty(facultyId)
//            majors.postValue(fetchedMajors)
//        }
//    }
//
//    fun fetchSubjectsForMajors(majorIds: List<String>) {
//        viewModelScope.launch {
//            val fetchedSubjects = majorIds.flatMap { majorId ->
//                majorRepository.getSubjectsByMajor(majorId)
//            }
//            subjects.postValue(fetchedSubjects)
//        }
//    }
//}
//
