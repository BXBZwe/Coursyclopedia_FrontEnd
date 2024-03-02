//package com.example.courscyclopedia.ui.main
//
//
//
//import android.content.Intent
//import android.os.Bundle
//import android.util.Log
//import android.widget.Button
//import android.widget.Toast
//import androidx.appcompat.app.AppCompatActivity
//import com.example.courscyclopedia.R
//import com.google.android.gms.auth.api.signin.GoogleSignIn
//import com.google.android.gms.auth.api.signin.GoogleSignInOptions
//import com.google.android.gms.common.api.ApiException
//import com.google.firebase.auth.FirebaseAuth
//import com.google.firebase.auth.GoogleAuthProvider
//
//class SignInActivity : AppCompatActivity() {
//    companion object {
//        private const val RC_SIGN_IN = 9001
//    }
//    private lateinit var auth: FirebaseAuth
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_sign_in)
//
//        auth = FirebaseAuth.getInstance()
//
//        val currentUser = auth.currentUser
//
//        if (currentUser != null) {
//            // The user is already signed in, navigate to MainActivity
//            val intent = Intent(this, MainActivity::class.java)
//            startActivity(intent)
//            finish() // finish the current activity to prevent the user from coming back to the SignInActivity using the back button
//        }
//
//        val signInButton = findViewById<Button>(R.id.signInButton)
//        signInButton.setOnClickListener {
//            signIn()
//        }
//    }
//
//    private fun signIn() {
//        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//            .requestIdToken(getString(R.string.default_web_client_id))
//            .requestEmail()
//            .build()
//
//        val googleSignInClient = GoogleSignIn.getClient(this, gso)
//        googleSignInClient.signOut().addOnCompleteListener {
//            val signInIntent = googleSignInClient.signInIntent
//            startActivityForResult(signInIntent, RC_SIGN_IN)
//        }
//    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//
//        if (requestCode == RC_SIGN_IN) {
//            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
//            try {
//                val account = task.getResult(ApiException::class.java)
//                firebaseAuthWithGoogle(account.idToken!!)
//            } catch (e: ApiException) {
//                Log.e("SignInActivity", "Google sign in failed", e)
//                Toast.makeText(this, "Google sign in failed: ${e.message}", Toast.LENGTH_SHORT).show()
//            }
//        }
//    }
//    private fun firebaseAuthWithGoogle(idToken: String) {
//        val credential = GoogleAuthProvider.getCredential(idToken, null)
//        auth.signInWithCredential(credential)
//            .addOnCompleteListener(this) { task ->
//                if (task.isSuccessful) {
//                    // Sign-in is successful
//                    val user = auth.currentUser
//                    user?.email?.let { email ->
//                        if (email.endsWith("au.edu")) {
//                            if (email.matches(Regex("^u\\d{7}@au.edu$"))) {
//                                // Identified as a student
//                                Toast.makeText(this, "Welcome student ${user.displayName}", Toast.LENGTH_SHORT).show()
//                                navigateToStudentHomePage()
//                            } else {
//                                // Identified as a professor
//                                Toast.makeText(this, "Welcome professor ${user.displayName}", Toast.LENGTH_SHORT).show()
//                                navigateToProfessorHomePage()
//                            }
//                        } else {
//                            // Email does not end with "au.edu"
//                            Toast.makeText(this, "Please sign in with your academic email", Toast.LENGTH_LONG).show()
//                            user?.delete()?.addOnCompleteListener { task ->
//                                if (task.isSuccessful) {
//                                    Log.d("SignInActivity", "Non-academic user deleted.")
//                                }
//                            }
//
//                            // Sign out from Google and Firebase
//                            signOutFromGoogle()
//                            // Sign out the user from Google and revoke access
//                            signOutFromGoogle()                       }
//                    }
//                } else {
//                    // Authentication failed
//                    Log.e("SignInActivity", "Authentication failed: ${task.exception?.message}")
//                    Toast.makeText(this, "Authentication failed", Toast.LENGTH_SHORT).show()
//                }
//            }
//    }
//
//    private fun navigateToStudentHomePage() {
//        val intent = Intent(this, MainActivity::class.java).apply {
//            putExtra("ROLE", "STUDENT")
//        }
//        startActivity(intent)
//        finish()
//    }
//
//    private fun navigateToProfessorHomePage() {
//        val intent = Intent(this, MainActivity::class.java).apply {
//            putExtra("ROLE", "PROFESSOR")
//        }
//        startActivity(intent)
//        finish()
//    }
//    private fun signOutFromGoogle() {
//        // Configure Google Sign In
//        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//            .requestIdToken(getString(R.string.default_web_client_id))
//            .requestEmail()
//            .build()
//
//        // Build a GoogleSignInClient with the options specified by gso
//        val googleSignInClient = GoogleSignIn.getClient(this, gso)
//
//        // Sign out from Google
//        googleSignInClient.signOut().addOnCompleteListener(this) {
//            googleSignInClient.revokeAccess().addOnCompleteListener(this) {
//                googleSignInClient.signOut()  // Clear the default account
//            }
//        }
//    }
//
//
//}
//
//


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
import com.example.courscyclopedia.ui.util.Result
import com.example.courscyclopedia.ui.util.SharedPreferencesUtils
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
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

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        findViewById<Button>(R.id.signInButton).setOnClickListener { signIn() }

        checkCurrentUser()
    }

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    private fun checkCurrentUser() {
        val currentUser = auth.currentUser
        currentUser?.let {
            navigateToNextActivity()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                Log.e("SignInActivity", "Google sign in failed", e)
                Toast.makeText(this, "Google sign in failed: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                val firebaseUser = auth.currentUser
                firebaseUser?.let { user ->
                    SharedPreferencesUtils.saveUserEmail(this, user.email ?: "")
                    val savedEmail = SharedPreferencesUtils.getUserEmail(this)
                    Log.d("SignInActivity", "Saved email: $savedEmail")
                        coroutineScope.launch {
                            checkUserInDatabase(user.email ?: "", user.displayName ?: "")
                        }
                }
            } else {
                Log.e("SignInActivity", "Authentication failed: ${task.exception?.message}")
                Toast.makeText(this, "Authentication failed", Toast.LENGTH_SHORT).show()
            }
        }
    }

//    private fun saveUserEmail(email: String) {
//        // Get SharedPreferences instance
//        val sharedPreferences = getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)
//        // Edit and commit the email to SharedPreferences
//        sharedPreferences.edit().putString("userEmail", email).apply()
//    }

    private suspend fun checkUserInDatabase(email: String, name: String) {
        when (val result = userRepository.fetchUserByEmail(email)) {
            is Result.Success -> navigateToNextActivity() // User exists
            is Result.Error -> createUserInDatabase(email, name)
            else -> {}
        }
    }

    private suspend fun createUserInDatabase(email: String, name: String) {
        if (!email.endsWith("@gmail.com")) {
            Toast.makeText(this, "Please sign in with your academic email", Toast.LENGTH_LONG).show()
            signOutFromGoogle()
            return
        }
        val isStudentEmail = email.matches(Regex("^u\\d{7}@gmail.com$"))
        val roles = if (isStudentEmail) listOf("student") else listOf("admin")

        val newUser = UserData(
            email = email,
            profile = UserProfile(firstName = name.split(" ")[0], lastName = name.split(" ").getOrNull(1) ?: ""),
            roles = roles
        )
        Log.d("SignInActivity", "UserData: $newUser")

        when (val result = userRepository.createUser(newUser)) {
            is Result.Success -> {
                if (isStudentEmail) {
                    navigateToStudentHomePage()
                } else {
                    navigateToProfessorHomePage()
                }
            }
            is Result.Error -> Log.e("SignInActivity", "Failed to create user: ${result.exception.message}")
            else -> {}
        }
    }

    private fun signOutFromGoogle() {
        // Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        // Build a GoogleSignInClient with the options specified by gso
        val googleSignInClient = GoogleSignIn.getClient(this, gso)

        // Sign out from Google
        googleSignInClient.signOut().addOnCompleteListener(this) {
            googleSignInClient.revokeAccess().addOnCompleteListener(this) {
                googleSignInClient.signOut()  // Clear the default account
            }
        }
    }


    private fun navigateToNextActivity() {
        // Navigation logic based on the user role or other criteria
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

//    private fun signOut() {
//        auth.signOut()
//        googleSignInClient.signOut().addOnCompleteListener {
//            // Optional: Update UI
//            val signInIntent = Intent(this, SignInActivity::class.java)
//            startActivity(signInIntent)
//            finish()
//        }
//    }

    override fun onDestroy() {
        super.onDestroy()
        coroutineScope.cancel()
    }

    private fun navigateToStudentHomePage() {
        val intent = Intent(this, MainActivity::class.java).apply {
            putExtra("ROLE", "STUDENT")
        }
        startActivity(intent)
        finish()
    }

    private fun navigateToProfessorHomePage() {
        val intent = Intent(this, MainActivity::class.java).apply {
            putExtra("ROLE", "PROFESSOR")
        }
        startActivity(intent)
        finish()
    }
}
