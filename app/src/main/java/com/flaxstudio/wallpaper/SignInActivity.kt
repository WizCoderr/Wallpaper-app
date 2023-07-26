package com.flaxstudio.wallpaper

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.flaxstudio.wallpaper.source.database.LikedWallpaper
import com.flaxstudio.wallpaper.utils.FirebaseLikedWallpaperDao
import com.flaxstudio.wallpaper.utils.UserDao
import com.flaxstudio.wallpaper.utils.Users
import com.flaxstudio.wallpaper.viewmodel.MainActivityViewModel
import com.flaxstudio.wallpaper.viewmodel.MainActivityViewModelFactory
import com.flaxstudio.wallpaperapp.R
import com.flaxstudio.wallpaperapp.databinding.ActivitySignInBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val TAG = "SignInActivity"
class SignInActivity : AppCompatActivity() {

    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInLauncher: ActivityResultLauncher<Intent>
    private lateinit var binding: ActivitySignInBinding


    private val mainActivityViewModel: MainActivityViewModel by viewModels {
        MainActivityViewModelFactory(
            (application as ProjectApplication).wallpaperRepository,
            (application as ProjectApplication).categoryRepository,
            (application as ProjectApplication).likedWallpaperRepository
        )
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configure Google Sign In

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)
        auth = Firebase.auth


        binding.buttonGoogle.setOnClickListener {
            val signInIntent = googleSignInClient.signInIntent
            googleSignInLauncher.launch(signInIntent)
        }


        googleSignInLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                val data: Intent? = result.data
                // Pass the result data to the Firebase Auth method for handling the sign-in
                val task = GoogleSignIn.getSignedInAccountFromIntent(data)

                Log.i(TAG, "Inside the Sign In Intent")

                try {
                    // Google sign-in was successful, authenticate with Firebase

                    val account = task.getResult(ApiException::class.java)
                    firebaseAuthWithGoogle(account?.idToken)
                } catch (e: ApiException) {
                    // Google sign-in failed, handle the error
                    Log.e(TAG, "Google sign-in failed", e)
                    // Show an error message or handle the failed sign-in
                }

            } else {
                // Google sign-in was canceled or failed, handle accordingly
                Log.e(TAG, "Google sign-in Intent failed")
            }
        }

    }

    override fun onStart() {
        super.onStart()
//        val currUser = auth.currentUser
//        updateUI(currUser)
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
    private fun firebaseAuthWithGoogle(idToken: String?) {


        when {
            idToken != null -> {
                // Got an ID token from Google. Use it to authenticate
                // with Firebase.
                val firebaseCredential = GoogleAuthProvider.getCredential(idToken, null)
                auth.signInWithCredential(firebaseCredential)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success")
                            val user = auth.currentUser
                            lifecycleScope.launch(Dispatchers.IO){
                                val users = Users(
                                    user?.displayName.toString(),
                                    user?.photoUrl.toString(),
                                    user!!.uid,
                                    false,
                                    0L,
                                    0L
                                )
                                val userDao = UserDao()
                                userDao.addUser(users)
                            }
                            getAllLikedWallpaper(user!!.uid)
                            updateUI(user)
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.exception)
                            updateUI(null)
                        }
                    }
            }

            else -> {
                // Shouldn't happen.
                Log.d(TAG, "No ID token!")
            }
        }
    }

    private fun getAllLikedWallpaper(uid: String) {

       val dao = FirebaseLikedWallpaperDao()
       val allLikedWallpaper : List<LikedWallpaper> =  dao.getAllWallpaper(uid)

        mainActivityViewModel.clearTable()
        mainActivityViewModel.addAllLikedWallpaper(allLikedWallpaper)

    }

    private fun updateUI(firebaseUser: FirebaseUser?) {
        if(firebaseUser != null) {
//            lifecycleScope.launch(Dispatchers.IO) {
//                val user = Users(
//                    firebaseUser.uid,
//                    firebaseUser.displayName!!,
//                    firebaseUser.photoUrl.toString()
//                )
//                val userDao = UserDao()
//                userDao.addUser(user)
//            }
            val mainActivityIntent = Intent(this, MainActivity::class.java)
            startActivity(mainActivityIntent)
            finish()

        }
        else{
//            signInButton.visibility = View.VISIBLE
//            progressBar.visibility = View.GONE
        }

    }

}