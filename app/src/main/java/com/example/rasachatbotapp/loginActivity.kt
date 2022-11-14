package com.example.rasachatbotapp

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.rasachatbotapp.R
import com.example.rasachatbotapp.resources.Functions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.ktx.Firebase

class loginActivity : AppCompatActivity() {

    private var email : EditText? = null
    private var password : EditText? = null
    private var vistaPrincipal : View? = null
    private var pDialog : ProgressDialog? = null
    private lateinit var auth: FirebaseAuth



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance();


        // CASTING FROM VIEWS TO KOTLIN OBJECTS
        email = findViewById(R.id.loginEmail) as EditText
        password = findViewById(R.id.loginPassword) as EditText
        vistaPrincipal = findViewById(R.id.drawer_layout_login) as View

        var btLogin : Button = findViewById(R.id.btLogin) as Button
        var btnLinkToRegisterScreen : Button = findViewById(R.id.btnLinkToRegisterScreen) as Button



        // Progress dialog
        pDialog = ProgressDialog(this)
        pDialog?.setCancelable(false)

        // Add the button register event when its clicked
        btLogin.setOnClickListener {


            // Hide the Keyboard
            Functions.hideKeyboard(this.currentFocus, this)

            // Collect the user input data
            val e : String = email?.text.toString()
            val p : String = password?.text.toString()

            // Check if data its OK
            if(!e.isEmpty() && !p.isEmpty())
            {
                try
                {
                    // Try to login
                    auth.signInWithEmailAndPassword(e, p)
                        .addOnCompleteListener(this) { task ->
                            if (task.isSuccessful) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "signInWithEmail:success")
                                Toast.makeText(baseContext, "signInWithEmail:success",
                                    Toast.LENGTH_SHORT).show()
//                                var intent : Intent = Intent(this, MainActivity::class.java)
                                var intent : Intent = Intent(this, TestJava::class.java)
                                startActivity(intent)
                                val user = auth.currentUser
//                                updateUI(user)
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "signInWithEmail:failure", task.exception)
                                Toast.makeText(baseContext, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show()
//                                updateUI(null)
                            }
                        }
                }
                catch(excp : Exception)
                {
                    excp.printStackTrace()
                }
            }
            else
            {
                // Show the error to the user
                Functions.showSnackbar(vistaPrincipal!!, getString(R.string.Wrong_Credentials))
            }
        }

        // Add the link to register activity
        btnLinkToRegisterScreen.setOnClickListener {
            var intent : Intent = Intent(this, registerActivity::class.java)
            startActivity(intent)
        }

    }

    // [START on_start_check_user]
    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if(currentUser != null){
            Functions.showSnackbar(vistaPrincipal!!, "User available")
//            var intent : Intent = Intent(this, MainActivity::class.java)
            var intent : Intent = Intent(this, TestJava::class.java)
            startActivity(intent)
        }else{
            Functions.showSnackbar(vistaPrincipal!!, "User not available")
        }
    }
    // [END on_start_check_user]

    /**
     * Method to try to login the user
     * @param email : User email
     * @param password : User password
     */
    private fun attemptToLogin(email : String, password : String)
    {


    }






    private fun getDescriptionError(codError : String): String
    {
        var description : String = ""

        when(codError) {
            "Wrong_Credentials" -> description = getString(R.string.Wrong_Credentials)
            "ErrorPostParamsMissing" -> description = getString(R.string.ErrorPostParamsMissing)
        }

        return description
    }

    /**
     * Method to clean the user inputs
     */
    private fun cleanInputs()
    {
        email?.setText("")
        password?.setText("")
    }

    companion object {
        private const val TAG = "EmailPassword"
    }
}
