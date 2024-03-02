package com.example.courscyclopedia.ui.main

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.courscyclopedia.R
import com.example.courscyclopedia.ui.util.SharedPreferencesUtils
import com.example.courscyclopedia.repository.UserRepository
import com.example.courscyclopedia.network.RetrofitClient
import com.example.courscyclopedia.ui.professors.fragments.ProfessorFragment
import com.example.courscyclopedia.ui.users.fragments.FacultyFragment
import com.example.courscyclopedia.ui.util.Result
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.*
import android.util.Log

class MainActivity : AppCompatActivity() {

    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var auth: FirebaseAuth
    private val userRepository by lazy { UserRepository(RetrofitClient.apiService) }
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()
        configureGoogleSignIn()

        checkUserAuthentication()

        displayUserName()
        setupLogoutButton()
    }

    private fun configureGoogleSignIn() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)
    }

    private fun displayUserName() {
        val textView = findViewById<TextView>(R.id.name)
        auth.currentUser?.displayName?.let { userName ->
            textView.text = getString(R.string.welcome_user, userName)
        }
    }

    private fun setupLogoutButton() {
        findViewById<ImageButton>(R.id.logout_icon).setOnClickListener {
            signOutAndStartSignInActivity()
        }
    }

    private fun checkUserAuthentication() {
        auth.currentUser?.let { user ->
            determineUserRoleAndNavigate(user.email ?: "")
        } ?: run {
            navigateToSignIn()
        }
    }

    private fun determineUserRoleAndNavigate(email: String) {
        if (email.startsWith("u") && email.contains("@au.edu")) {
            navigateToFacultyFragment()
        } else {
            navigateToProfessorFragment()
        }
    }

    private fun navigateToFacultyFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, FacultyFragment())
            .commit()
    }

    private fun navigateToProfessorFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, ProfessorFragment())
            .commit()
    }

    private fun signOutAndStartSignInActivity() {
        auth.signOut()
        SharedPreferencesUtils.clearUserEmail(this)
        googleSignInClient.signOut().addOnCompleteListener {
            navigateToSignIn()
        }
    }

    private fun navigateToSignIn() {
        startActivity(Intent(this, SignInActivity::class.java))
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        coroutineScope.cancel()
    }
}



//package com.example.courscyclopedia.ui.main
//
//import android.content.Intent
//import android.os.Bundle
//import android.widget.ImageButton
//import android.widget.TextView
//import androidx.appcompat.app.AppCompatActivity
//import com.example.courscyclopedia.R
//import com.example.courscyclopedia.ui.util.SharedPreferencesUtils
//import com.example.courscyclopedia.repository.UserRepository
//import com.example.courscyclopedia.network.RetrofitClient
//import com.example.courscyclopedia.ui.professors.fragments.ProfessorFragment
//import com.example.courscyclopedia.ui.users.fragments.FacultyFragment
//import com.example.courscyclopedia.ui.util.Result
//import com.google.android.gms.auth.api.signin.GoogleSignIn
//import com.google.android.gms.auth.api.signin.GoogleSignInClient
//import com.google.android.gms.auth.api.signin.GoogleSignInOptions
//import com.google.firebase.auth.FirebaseAuth
//import kotlinx.coroutines.*
//import android.util.Log
//
//class MainActivity : AppCompatActivity() {
//
//    private lateinit var googleSignInClient: GoogleSignInClient
//    private lateinit var auth: FirebaseAuth
//    private val userRepository by lazy { UserRepository(RetrofitClient.apiService) }
//    private val coroutineScope = CoroutineScope(Dispatchers.Main)
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//
//        // Initialize Firebase Auth
//        auth = FirebaseAuth.getInstance()
//
//        // Configure Google Sign In
//        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//            .requestIdToken(getString(R.string.default_web_client_id))
//            .requestEmail()
//            .build()
//        googleSignInClient = GoogleSignIn.getClient(this, gso)
//
//        checkUserAuthentication()
//
//        // Display the user's name if available
//        val textView = findViewById<TextView>(R.id.name)
//        val user = auth.currentUser
//        user?.let {
//            val userName = it.displayName
//            textView.text = getString(R.string.welcome_user, userName)
//        }
//
//        // Set up the logout button
//        val logoutIcon = findViewById<ImageButton>(R.id.logout_icon)
//        logoutIcon.setOnClickListener {
//            signOutAndStartSignInActivity()
//        }
//    }
//
//    private fun checkUserAuthentication() {
//        val currentUser = auth.currentUser
//        if (currentUser == null) {
//            // User is not logged in, redirect to SignInActivity
//            navigateToSignIn()
//        } else {
//            // User is logged in, fetch and check their role
//            fetchUserRole(currentUser.email ?: "")
//        }
//    }
//
//    private fun fetchUserRole(email: String) {
//        coroutineScope.launch {
//            val result = userRepository.fetchUserDetailsByEmail(email)
//            Log.d("MainActivity", "Fetch result: $email") // Log the result here
//
//            when (result) {
//                is Result.Success -> {
//                    val userRole = result.data.roles?.firstOrNull()
//                    Log.d("MainActivity", "Fetched user role: $userRole") // Log the role here
//                    userRole?.let { navigateBasedOnRole(it) }
//                }
//                is Result.Error -> {
//                    Log.e("MainActivity", "Error fetching user role: ${result.exception.message}") // Log the error here
//                }
//                else -> {
//                    Log.d("MainActivity", "Unexpected result when fetching user role")
//                }
//            }
//        }
//    }
//
//    private fun navigateBasedOnRole(role: String) {
//        val fragment = when (role) {
//            "student" -> FacultyFragment() // Instance of FacultyFragment
//            "admin" -> ProfessorFragment() // Instance of ProfessorFragment
//            else -> { null } // Handle unknown role or direct to a default fragment
//        }
//
//        // Perform the fragment transaction
//        fragment?.let {
//            supportFragmentManager.beginTransaction()
//                .replace(R.id.fragment_container, it) // Ensure you have a container in your layout
//                .commit()
//        }
//    }
//
//
//
//    private fun signOutAndStartSignInActivity() {
//        // Sign out from Firebase and Google, then navigate to SignInActivity
//        auth.signOut()
//        SharedPreferencesUtils.clearUserEmail(this)
//        googleSignInClient.signOut().addOnCompleteListener(this) {
//            navigateToSignIn()
//        }
//    }
//
//    private fun navigateToSignIn() {
//        val intent = Intent(this, SignInActivity::class.java)
//        startActivity(intent)
//        finish()
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//        coroutineScope.cancel()
//    }
//}
//


//package com.example.courscyclopedia.ui.main
//
//
//import android.content.Intent
//import android.os.Bundle
//import android.os.Handler
//import android.os.Looper
//import android.util.Log
//import android.widget.Button
//import android.widget.ImageButton
//import android.widget.TextView
//import androidx.appcompat.app.AppCompatActivity
//import androidx.navigation.findNavController
//import com.example.courscyclopedia.R
//import com.example.courscyclopedia.ui.users.fragments.SubjectDetailFragment
//import com.example.courscyclopedia.ui.util.SharedPreferencesUtils
//import com.google.android.gms.auth.api.signin.GoogleSignIn
//import com.google.android.gms.auth.api.signin.GoogleSignInClient
//import com.google.android.gms.auth.api.signin.GoogleSignInOptions
//import com.google.firebase.auth.FirebaseAuth
//import com.google.firebase.auth.ktx.auth
//import com.google.firebase.ktx.Firebase
//
//class MainActivity : AppCompatActivity() {
//
//    private lateinit var mGoogleSignInClient: GoogleSignInClient
//    private lateinit var mAuth: FirebaseAuth
//    val fragment = SubjectDetailFragment()
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//        val role = intent.getStringExtra("ROLE")
//        Log.d("MainActivity", "Received role: $role")
//        mAuth = FirebaseAuth.getInstance()
////
//        Handler(Looper.getMainLooper()).post {
//            handleIntent()
//        }
//        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//            .requestIdToken(getString(R.string.default_web_client_id))
//            .requestEmail()
//            .build()
//        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
//
//        val textView = findViewById<TextView>(R.id.name)
//
//        val auth = Firebase.auth
//        val user = auth.currentUser
//
//        if (user != null) {
//            val userName = user.displayName
//            textView.text = "Welcome, " + userName
//        } else {
//        }
//
//
//        val logoutIcon = findViewById<ImageButton>(R.id.logout_icon)
//        logoutIcon.setOnClickListener {
//            signOutAndStartSignInActivity()
//        }
//
//    }
//    private fun signOutAndStartSignInActivity() {
//        mAuth.signOut()
//
//        SharedPreferencesUtils.clearUserEmail(this)
//
//        mGoogleSignInClient.signOut().addOnCompleteListener(this) {
//            // Optional: Update UI or show a message to the user
//            val intent = Intent(this@MainActivity, SignInActivity::class.java)
//            startActivity(intent)
//            finish()
//        }
//    }
//
//    private fun handleIntent() {
//        when (intent.getStringExtra("ROLE")) {
//            "STUDENT" -> navigateToStudentHomePage()
//            "PROFESSOR" -> navigateToProfessorHomePage()
//        }
//        // Clear the ROLE from the intent after handling it
//        intent.removeExtra("ROLE")
//    }
//
//
//    private fun navigateToStudentHomePage() {
//        Log.d("MainActivity", "About to navigate to student home page")
//        findNavController(R.id.nav_host_fragment).navigate(R.id.facultyFragment)
//    }
//
//    private fun navigateToProfessorHomePage() {
//        Log.d("MainActivity", "About to navigate to professor home page")
//        findNavController(R.id.nav_host_fragment).navigate(R.id.professorFragment)
//    }
//
//
//
//
//}