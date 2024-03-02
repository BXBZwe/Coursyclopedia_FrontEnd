package com.example.courscyclopedia.ui.main



import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.courscyclopedia.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class SignInActivity : AppCompatActivity() {
    companion object {
        private const val RC_SIGN_IN = 9001
    }
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        auth = FirebaseAuth.getInstance()

        val currentUser = auth.currentUser

        if (currentUser != null) {
            // The user is already signed in, navigate to MainActivity
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish() // finish the current activity to prevent the user from coming back to the SignInActivity using the back button
        }

        val signInButton = findViewById<Button>(R.id.signInButton)
        signInButton.setOnClickListener {
            signIn()
        }
    }

    private fun signIn() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        val googleSignInClient = GoogleSignIn.getClient(this, gso)
        googleSignInClient.signOut().addOnCompleteListener {
            val signInIntent = googleSignInClient.signInIntent
            startActivityForResult(signInIntent, RC_SIGN_IN)
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
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign-in is successful
                    val user = auth.currentUser
                    user?.email?.let { email ->
                        if (email.endsWith("au.edu")) {
                            if (email.matches(Regex("^u\\d{7}@au.edu$"))) {
                                // Identified as a student
                                Toast.makeText(this, "Welcome student ${user.displayName}", Toast.LENGTH_SHORT).show()
                                navigateToStudentHomePage()
                            } else {
                                // Identified as a professor
                                Toast.makeText(this, "Welcome professor ${user.displayName}", Toast.LENGTH_SHORT).show()
                                navigateToProfessorHomePage()
                            }
                        } else {
                            // Email does not end with "au.edu"
                            Toast.makeText(this, "Please sign in with your academic email", Toast.LENGTH_LONG).show()
                            user?.delete()?.addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    Log.d("SignInActivity", "Non-academic user deleted.")
                                }
                            }

                            // Sign out from Google and Firebase
                            signOutFromGoogle()
                            // Sign out the user from Google and revoke access
                            signOutFromGoogle()                       }
                    }
                } else {
                    // Authentication failed
                    Log.e("SignInActivity", "Authentication failed: ${task.exception?.message}")
                    Toast.makeText(this, "Authentication failed", Toast.LENGTH_SHORT).show()
                }
            }
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


}

