//package com.example.courscyclopedia.ui.users.fragments
//
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.fragment.app.Fragment
//import androidx.lifecycle.ViewModelProvider
//import com.example.courscyclopedia.MyApp
//import com.example.courscyclopedia.R
//import com.example.courscyclopedia.ui.users.viewmodels.FacultyDetailsViewModel
//import com.example.courscyclopedia.ui.users.viewmodels.FacultyDetailsViewModelFactory
//
//class FacultyDetailsFragment : Fragment() {
//
//    private lateinit var viewModel: FacultyDetailsViewModel
//
//    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//        return inflater.inflate(R.layout.fragment_faculty_details, container, false)
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        val application = requireActivity().application as MyApp
//        val factory = FacultyDetailsViewModelFactory(application.majorRepository)
//        viewModel = ViewModelProvider(this, factory).get(FacultyDetailsViewModel::class.java)
//
//        arguments?.let {
//            val facultyName = FacultyDetailsFragmentArgs.fromBundle(it).facultyName
//            // You'll need to fetch the faculty ID instead of name to pass to fetchMajorsForFaculty
//            // viewModel.fetchMajorsForFaculty(facultyId)
//        }
//    }
//}
