package com.example.rasachatbotapp

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rasachatbotapp.network.Message
import com.example.rasachatbotapp.network.rasaApiService
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import java.util.*
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.firestore.MetadataChanges
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ServerTimestamp
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.Source
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.firestoreSettings
import com.google.firebase.firestore.ktx.toObject

class MainActivityViewModel : ViewModel() {
    private val message_list: MutableList<Message> = mutableStateListOf()
    val messages: List<Message> = message_list

    private val connectivityState = mutableStateOf(true)
    val _connectivityState = connectivityState

    val username = "Chamodi@gmail.com"
    var messagesLoaded = "false"

    fun addMessage(message: Message) {
        message_list.add(0, message)
    }

    fun sendMessagetoRasa(message: Message) {

        if(messagesLoaded == "false"){
            Log.d("RASA", "Message list status$messagesLoaded")

            // [START get_firestore_data]

            // [END get_firestore_data]

            messagesLoaded = "true"
        }


        // [START get_firestore_instance]
        val db = Firebase.firestore
        // [END get_firestore_instance]

        // [START set_firestore_settings]
        val settings = firestoreSettings {
            isPersistenceEnabled = true
        }
        db.firestoreSettings = settings
        // [END set_firestore_settings]
        db.collection(username)
            .add(message)
            .addOnSuccessListener { documentReference ->
                Log.d("RASA", "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w("RASA", "Error adding document", e)
            }
        addMessage(message)
        Log.d("RASA", "Sending message to RASA$message");
        viewModelScope.launch {
            val response = rasaApiService.sendMessage(message)
            Log.e("Message", response.toString())
            if (response.code() == 200 && response.body() != null) {
                response.body()!!.forEach {
                    it.time = Calendar.getInstance().time
                    Log.d("RASA", "Sending message to RASA$it");
                    db.collection(username)
                        .add(it)
                        .addOnSuccessListener { documentReference ->
                            Log.d("RASA", "DocumentSnapshot added with ID: ${documentReference.id}")
                        }
                        .addOnFailureListener { e ->
                            Log.w("RASA", "Error adding document", e)
                        }
                    addMessage(it)
                }
            } else {
                addMessage(
                    Message(
                        "${response.code()} error occured",
                        "bot",
                        Calendar.getInstance().time
                    )
                )
            }
        }
    }


}