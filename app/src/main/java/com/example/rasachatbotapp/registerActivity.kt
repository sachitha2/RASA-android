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
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import com.example.rasachatbotapp.R
import com.example.rasachatbotapp.resources.Functions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.ktx.Firebase

class registerActivity : AppCompatActivity() {

    private var name : EditText? = null
    private var email : EditText? = null
    private var password : EditText? = null
    private var queue : RequestQueue? = null
    private var pDialog : ProgressDialog? = null
    private var vistaPrincipal : View? = null
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register_activity)

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance();

        // CASTING FROM VIEWS TO KOTLIN OBJECTS
        name = findViewById(R.id.register_name) as EditText
        email = findViewById(R.id.register_email) as EditText
        password = findViewById(R.id.register_password) as EditText
        vistaPrincipal = findViewById(R.id.drawer_layout_register) as View

        var btnRegister : Button = findViewById(R.id.btRegistrarse) as Button
        var btnLinkToLogin : Button = findViewById(R.id.btnLinkToLogIn) as Button

        // We create the queue for the HTTP Requests of the Volley Library
        queue = Volley.newRequestQueue(this)

        // Progress Dialog
        pDialog = ProgressDialog(this)
        pDialog?.setCancelable(false)

        // Add the button register event when its clicked
        btnRegister.setOnClickListener {

            // Hide the virtual keyboard
            Functions.hideKeyboard(this.currentFocus, this)

            // Collect the user data
            val n : String = name?.text.toString()
            val e : String = email?.text.toString()
            val p : String = password?.text.toString()

            // Check if data its OK
            if(!n.isEmpty() && !e.isEmpty() && !p.isEmpty())
            {
                try {

                    // Try to register
                    attemptToRegister(n, e, p)
                }
                catch (excp : Exception)
                {
                    excp.printStackTrace()
                }
            }
            else
            {
                // Show error message, we need all the user information filled
//                Functions.showSnackbar(vistaPrincipal!!, getString(R.string.errorFaltanDatos))
            }

        }

        // Add the link to login activity
        btnLinkToLogin.setOnClickListener {
            var intent : Intent = Intent(this, loginActivity::class.java)
            startActivity(intent)
        }
    }

    /**
     * Method to register the user
     * @param name : Username
     * @param email : User email
     * @param password : User password
     */
    private fun attemptToRegister(name : String, email : String, password : String)
    {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success")
                    Toast.makeText(baseContext, "createUserWithEmail:success",
                        Toast.LENGTH_SHORT).show()
                    val user = auth.currentUser
//                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
//                    updateUI(null)
                }
            }

    }



    private fun getDescriptionError(codError : String): String
    {
        var description : String = ""

        when(codError) {
            "UnknownErrorRegistration" -> description = getString(R.string.UnknownErrorRegistration)
            "ErrorPostParamsMissing" -> description = getString(R.string.ErrorPostParamsMissing)
            "UserAlreadyExist" -> description = getString(R.string.UserAlreadyExist)
        }

        return description
    }

    /**
     * Method to clean the user inputs
     */
    private fun cleanInputs()
    {
        this.name?.setText("")
        this.email?.setText("")
        this.password?.setText("")
    }

    // Method executed when the Volley request fails
    private fun requestError() : Response.ErrorListener
    {
        return Response.ErrorListener { error ->

            // Show error message

            // ! -- Remember if we get this error in Android com.android.volley.NoConnectionError: java.net.SocketException: socket failed: EACCES (Permission denied)
            // its because we need to add an allowing permision to our app to access to Internet or local network in the manifest
            // <uses-permission android:name="android.permission.INTERNET" />

            Log.d("Volley", error.toString())
            pDialog?.hide()
//            Functions.showSnackbar(vistaPrincipal!!, getString(R.string.noHayConexionServidor))
        }
    }

    companion object {
        private const val TAG = "EmailPassword"
    }
}
