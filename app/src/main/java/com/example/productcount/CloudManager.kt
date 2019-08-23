package com.example.productcount

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*

fun initializeCloud() = FirebaseFirestore.getInstance()

fun addProduct( designation: String, codeProduit: String, dateCode: String, tag: String){
    val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
    val currentDate = sdf.format(Date())
    System.out.println(" C DATE is  "+currentDate)
    val user = hashMapOf(
        "designation" to designation,
        "code_produit" to codeProduit,
        "date_code" to dateCode,
        "tag" to tag,
        "count" to 1,
        "last_modification" to currentDate,
        "creation_time" to currentDate
    )

    val db = FirebaseFirestore.getInstance()
// Add a new document with a generated ID
    db.collection("products")
        .add(user)
        .addOnSuccessListener { documentReference ->
            Log.d("CLOUDMANAGER<<", "DocumentSnapshot added with ID: ${documentReference.id}")
        }
        .addOnFailureListener { e ->
            Log.w("CLOUDMANAGER<<", "Error adding document", e)
        }
}