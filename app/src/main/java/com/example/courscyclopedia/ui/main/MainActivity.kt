package com.example.courscyclopedia.ui.main

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.example.courscyclopedia.R
import com.example.courscyclopedia.ui.users.fragments.SubjectDetailFragment
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var mAuth: FirebaseAuth
    val fragment = SubjectDetailFragment()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val role = intent.getStringExtra("ROLE")
        Log.d("MainActivity", "Received role: $role")
        mAuth = FirebaseAuth.getInstance()

        Handler(Looper.getMainLooper()).post {
            handleIntent()
        }
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        val textView = findViewById<TextView>(R.id.name)

        val auth = Firebase.auth
        val user = auth.currentUser

        if (user != null) {
            val userName = user.displayName
            textView.text = "Welcome, " + userName
        } else {
        }


        val sign_out_button = findViewById<Button>(R.id.logout_button)
        sign_out_button.setOnClickListener {
            signOutAndStartSignInActivity()
        }
    }
    private fun signOutAndStartSignInActivity() {
        mAuth.signOut()

        mGoogleSignInClient.signOut().addOnCompleteListener(this) {
            // Optional: Update UI or show a message to the user
            val intent = Intent(this@MainActivity, SignInActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun handleIntent() {
        when (intent.getStringExtra("ROLE")) {
            "STUDENT" -> navigateToStudentHomePage()
            "PROFESSOR" -> navigateToProfessorHomePage()
        }
        // Clear the ROLE from the intent after handling it
        intent.removeExtra("ROLE")
    }


    private fun navigateToStudentHomePage() {
        Log.d("MainActivity", "About to navigate to student home page")
        findNavController(R.id.nav_host_fragment).navigate(R.id.facultyFragment)
    }

    private fun navigateToProfessorHomePage() {
        Log.d("MainActivity", "About to navigate to professor home page")
        findNavController(R.id.nav_host_fragment).navigate(R.id.professorFragment)
    }
}