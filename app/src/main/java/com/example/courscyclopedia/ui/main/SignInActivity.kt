package com.example.courscyclopedia.ui.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.courscyclopedia.R
import com.example.courscyclopedia.model.UserData
import com.example.courscyclopedia.model.UserProfile
import com.example.courscyclopedia.network.RetrofitClient
import com.example.courscyclopedia.repository.UserRepository
import com.example.courscyclopedia.ui.professors.fragments.ProfessorFragment
import com.example.courscyclopedia.ui.users.fragments.FacultyFragment
import com.example.courscyclopedia.ui.util.Result
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.*

class SignInActivity : AppCompatActivity() {
    companion object {
        private const val RC_SIGN_IN = 9001
    }

    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    private val userRepository by lazy { UserRepository(RetrofitClient.apiService) }
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        auth = FirebaseAuth.getInstance()
        setupGoogleSignIn()
        findViewById<Button>(R.id.signInButton).setOnClickListener { signIn() }
    }

    private fun setupGoogleSignIn() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)
    }

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(task: Task<GoogleSignInAccount>) {
        try {
            val account = task.getResult(ApiException::class.java)
            firebaseAuthWithGoogle(account.idToken!!)
        } catch (e: ApiException) {
            Log.e("SignInActivity", "Google sign in failed", e)
            Toast.makeText(this, "Google sign in failed: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                val email = auth.currentUser?.email ?: ""
                checkUserInDatabase(email)
            } else {
                Log.e("SignInActivity", "Authentication failed: ${task.exception?.message}")
                Toast.makeText(this, "Authentication failed", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun checkUserInDatabase(email: String) {
        coroutineScope.launch {
            when (val result = userRepository.fetchUserByEmail(email)) {
                is Result.Success -> navigateBasedOnRole(result.data.data?.roles?.firstOrNull() ?: "default")
                is Result.Error -> createUserInDatabase(email)
                else -> Log.e("SignInActivity", "Unexpected error")
            }
        }
    }

    private suspend fun createUserInDatabase(email: String) {
        val isStudent = email.startsWith("u") && email.contains("@au.edu")
        val roles = if (isStudent) listOf("student") else listOf("admin")
        val newUser = UserData(email = email, roles = roles, profile = UserProfile(firstName = "New", lastName = "User"))

        when (val result = userRepository.createUser(newUser)) {
            is Result.Success -> navigateBasedOnRole(roles.first())
            is Result.Error -> Log.e("SignInActivity", "Failed to create user: ${result.exception.message}")
            else -> Log.e("SignInActivity", "Unexpected error")
        }
    }

    private fun navigateBasedOnRole(role: String) {
        val intent = when (role) {
            "student" -> Intent(this, FacultyFragment::class.java)
            "admin" -> Intent(this, ProfessorFragment::class.java)
            else -> Intent(this, MainActivity::class.java) // Default or unknown role
        }
        startActivity(intent)
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        coroutineScope.cancel()
    }
}
