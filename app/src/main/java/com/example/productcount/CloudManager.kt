package com.example.productcount

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*
import android.os.Handler

fun initializeCloud() = FirebaseFirestore.getInstance()

fun addProduct( designation: String, codeProduit: String, dateCode: String, tag: String, handler: Handler){
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
            System.out.println(" C DATE is  ")
            handler.sendEmptyMessage(PRODUCT_ADDED)
        }
        .addOnFailureListener { e ->
            Log.w("CLOUDMANAGER<<", "Error adding document", e)
            System.out.println(" C DATE is  ")
            handler.sendEmptyMessage(PRODUCT_ADD_ERROR)
        }
}

fun addProduct(product: Product, handler: Handler){
    val db = initializeCloud()
    db.collection("products").add(product).addOnSuccessListener {
        System.out.println(" C DATE is SUCCESS ")
        handler.sendEmptyMessage(PRODUCT_ADDED) }
        .addOnFailureListener {  System.out.println(" C DATE is  ERROR")
            handler.sendEmptyMessage(PRODUCT_ADD_ERROR) }
}

fun getProducts(){
    val db = initializeCloud()
    db.collection("products")
        .get()
        .addOnSuccessListener { result ->
            for (document in result) {
                Log.d("CLOUDMANAGFER>>", "${document.id} => ${document.data}")
            }
        }
        .addOnFailureListener { exception ->
            Log.w("CLOUDMANAGFER>>", "Error getting documents.", exception)
        }
}