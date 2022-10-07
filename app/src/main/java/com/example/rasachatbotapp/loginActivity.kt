package com.example.rasachatbotapp

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.AuthFailureError
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.rasachatbotapp.R
import com.example.rasachatbotapp.resources.Functions
import org.json.JSONObject

class loginActivity : AppCompatActivity() {

    private var email : EditText? = null
    private var password : EditText? = null
    private var vistaPrincipal : View? = null
    private var queue : RequestQueue? = null
    private var pDialog : ProgressDialog? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)

        // CASTING FROM VIEWS TO KOTLIN OBJECTS
        email = findViewById(R.id.loginEmail) as EditText
        password = findViewById(R.id.loginPassword) as EditText
        vistaPrincipal = findViewById(R.id.drawer_layout_login) as View

        var btLogin : Button = findViewById(R.id.btLogin) as Button
        var btnLinkToRegisterScreen : Button = findViewById(R.id.btnLinkToRegisterScreen) as Button

        // We create the queue for the HTTP Requests of the Volley Library
        queue = Volley.newRequestQueue(this)

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
                    attemptToLogin(e, p)
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
}
