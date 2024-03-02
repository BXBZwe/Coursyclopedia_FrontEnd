package com.example.courscyclopedia.ui.main

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.example.courscyclopedia.R
import com.example.courscyclopedia.ui.util.SharedPreferencesUtils
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var auth: FirebaseAuth

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
            navigateBasedOnUserRole(user.email ?: "")
        } ?: run {
            navigateToSignIn()
        }
    }

    private fun navigateBasedOnUserRole(email: String) {
        val navController = findNavController(R.id.nav_host_fragment)
        if (email.startsWith("u") && email.contains("@au.edu")) {
            // Navigate to FacultyFragment
            navController.navigate(R.id.facultyFragment)
        } else {
            // Navigate to ProfessorFragment
            navController.navigate(R.id.professorFragment)
        }
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
}
