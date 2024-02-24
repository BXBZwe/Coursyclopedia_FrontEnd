//package com.example.courscyclopedia.ui.fragments
//
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import com.example.courscyclopedia.R
//import com.example.courscyclopedia.viewmodel.FacultyViewModel
//
//class FacultyFragment : Fragment() {
//
//    private lateinit var viewModel: FacultyViewModel
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_faculty, container, false)
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        viewModel = ViewModelProvider(this).get(FacultyViewModel::class.java)
//
//        viewModel.faculties.observe(viewLifecycleOwner, Observer { faculties ->
//            // Update the UI with the list of faculties
//        })
//
//        // Fetch the faculties when the fragment starts
//        viewModel.fetchFaculties()
//    }
//}
