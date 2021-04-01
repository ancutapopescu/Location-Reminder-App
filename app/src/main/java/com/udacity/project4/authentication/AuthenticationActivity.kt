package com.udacity.project4.authentication

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth

import com.udacity.project4.R
import com.udacity.project4.authentication.AuthenticationActivity.Companion.SIGN_IN_REQUEST_CODE
import com.udacity.project4.databinding.ActivityAuthenticationBinding
import com.udacity.project4.locationreminders.RemindersActivity
import kotlin.random.Random


/**
 * This class should be the starting point of the app, It asks the users to sign in / register, and redirects the
 * signed in users to the RemindersActivity.
 */
class AuthenticationActivity : AppCompatActivity() {

    companion object {
        const val TAG = "AuthenticationActivity"
        const val SIGN_IN_REQUEST_CODE = 1001
    }

    private lateinit var binding: ActivityAuthenticationBinding

    private val viewModel by viewModels<AuthenticationViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        binding = DataBindingUtil.setContentView(this, R.layout.activity_authentication)

        observeAuthenticationState()

//         TODO: Implement the create account and sign in using FirebaseUI, use sign in using email and sign in using Google

        binding.loginButton.setOnClickListener {
                launchSignInFlow()
            }
//          TODO: If the user was authenticated, send him to RemindersActivity

//          TODO: a bonus is to customize the sign in flow to look nice using :
        //https://github.com/firebase/FirebaseUI-Android/blob/master/auth/README.md#custom-layout

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // TODO Listen to the result of the sign in process by filter for when
        //  SIGN_IN_REQUEST_CODE is passed back. Start by having log statements to know
        //  whether the user has signed in successfully
        if (requestCode == SIGN_IN_REQUEST_CODE) {
            val response = IdpResponse.fromResultIntent(data)
            if (resultCode == Activity.RESULT_OK) {
                // User successfully signed in
                Log.i(TAG, "Successfully signed in user ${FirebaseAuth.getInstance().currentUser?.displayName}!")
            } else {
                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.
                Log.i(TAG, "Sign in unsuccessful ${response?.error?.errorCode}")
            }
        }
    }

    /**
     * Observes the authentication state and changes the UI accordingly.
     * If there is a logged in user: (1) show a logout button and (2) display their name.
     * If there is no logged in user: show a login button
     */
    private fun observeAuthenticationState() {

        // TODO Use the authenticationState variable from LoginViewModel to update the UI
        //  accordingly.
        //
        //  TODO If there is a logged-in user, authButton should display Logout. If the
        //   user is logged in, you can customize the welcome message by utilizing
        //   getFactWithPersonalition(). I

        // TODO If there is no logged in user, authButton should display Login and launch the sign
        //  in screen when clicked. There should also be no personalization of the message
        //  displayed.

        viewModel.authenticationState.observe(this, Observer { authenticationState ->
            when (authenticationState) {
                AuthenticationViewModel.AuthenticationState.AUTHENTICATED -> {
                    startActivity(Intent(this, RemindersActivity::class.java))
                    finish()
            }
            else -> {
            /*TODO 3. Lastly, if there is no logged-in user,
                    login_button should display Login and
                    launch the sign in screen when clicked.*/
            binding.loginButton.text = getString(R.string.login_button)
            binding.loginButton.setOnClickListener { launchSignInFlow() }
        }
    }
})
}


    private fun launchSignInFlow() {
        // TODO Complete this function by allowing users to register and sign in with
        //  either their email address or Google account.
        // Give users the option to sign in / register with their email or Google account.
        // If users choose to register with their email,
        // they will need to create a password as well.
        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build(), AuthUI.IdpConfig.GoogleBuilder().build()

            // This is where you can provide more ways for users to register and
            // sign in.
        )

        // Create and launch sign-in intent.
        // We listen to the response of this activity with the
        // SIGN_IN_REQUEST_CODE
        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .build(),
            SIGN_IN_REQUEST_CODE
        )
    }

}
