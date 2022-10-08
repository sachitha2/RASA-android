package com.example.rasachatbotapp

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.rasachatbotapp.network.Message
import com.example.rasachatbotapp.ui.theme.RasaChatbotAppTheme
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.firestoreSettings
import com.google.firebase.ktx.Firebase
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import java.security.Timestamp
import java.util.*

val viewModel = MainActivityViewModel()

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)

        setContent {
            RasaChatbotAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    DestinationsNavHost(navGraph = NavGraphs.root)
                }
            }
        }

        Log.d("chata","chata");
        val username = "Chamodi@gmail.com"
        // [START] load chat history from here
        // [START get_firestore_instance]
        val db = Firebase.firestore
        // [END get_firestore_instance]

        // [START set_firestore_settings]
        val settings = firestoreSettings {
            isPersistenceEnabled = true
        }
        db.firestoreSettings = settings
        // [END set_firestore_settings]

        // [START get_firestore_data]
        db.collection(username)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d("RASA", "${document.id} => ${document.data}")

//                    val message = document.toObject<Message>();



                    val dd = document.getDate("time")
                    viewModel.addMessage(

                        Message(
                            text = document.get("text") as String?,
                            recipient_id = document.get("recipient_id") as String,
                            time = dd as Date,
                            isOut = document.getBoolean("out") as Boolean,
                            image = document.get("image") as String?
                        )
                    )
                }
            }
            .addOnFailureListener { exception ->
                Log.w("RASA", "Error getting documents.", exception)
            }
        // [END get_firestore_data]
        // [END] load chat history end from here
    }
}

@Destination(start = true)
@Composable
fun MainScreen(navigator: DestinationsNavigator) {
    Log.d("RASA","sam is well")
    val context = LocalContext.current
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetwork = connectivityManager.activeNetworkInfo
    viewModel._connectivityState.value = activeNetwork != null && activeNetwork.isConnected
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        TopBarSection(
            username = "SleepBot",
            profile = painterResource(id = R.drawable.sleep),
            isOnline = viewModel._connectivityState.value,
            navigator = navigator
        )
        ChatSection(Modifier.weight(1f), viewModel, navigator = navigator)
        MessageSection(viewModel)
    }


}

@Composable
fun MessageSection(
    viewModel: MainActivityViewModel,
) {
    val context = LocalContext.current
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        backgroundColor = Color.White,
        elevation = 10.dp
    ) {
        OutlinedTextField(
            placeholder = {
                Text("Message..")
            },
            value = message.value,
            onValueChange = {
                message.value = it
            },
            shape = RoundedCornerShape(25.dp),
            trailingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_send),
                    contentDescription = null,
                    tint = MaterialTheme.colors.primary,
                    modifier = Modifier.clickable {
                        if (viewModel._connectivityState.value) {
                            viewModel.sendMessagetoRasa(
                                Message(
                                    text = message.value,
                                    recipient_id = viewModel.username,
                                    time = Calendar.getInstance().time,
                                    isOut = true
                                )
                            )
                        } else {
                            Toast.makeText(
                                context,
                                "Please connect to internet",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        message.value = ""
                    }
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
        )
    }
}
