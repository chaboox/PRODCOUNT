package com.example.productcount

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*
import android.os.Handler
import com.google.firebase.firestore.FieldValue

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
    db.collection("products").document( product.code_produit +product.date_code  ).set(product).addOnSuccessListener {
        System.out.println("C DATE is SUCCESS ")
        handler.sendEmptyMessage(PRODUCT_ADDED) }
        .addOnFailureListener {  System.out.println(" C DATE is  ERROR")
            handler.sendEmptyMessage(PRODUCT_ADD_ERROR) }
}

fun getProducts(){
    val db = initializeCloud()
    val docRef = db.collection("products")
    docRef.get().addOnSuccessListener { documentSnapshot ->

        val products = documentSnapshot.toObjects(Product::class.java)
        System.out.println("CITYYY" + products.get(0) + "lllll   " + products.size + documentSnapshot)
    }
}

fun updateCount(product: Product, number: Long){
    val db = initializeCloud()
    val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
    val zeros = "00000000"
    val currentDate = sdf.format(Date())
    val refProduct = db.collection("products").document(product.key.toString())
    val refHistory = db.collection("Histories").document()
    //ref.update(COUNT, FieldValue.increment(number))

    db.runTransaction { transaction ->


        // Note: this could be done without a transaction
        //       by updating the population using FieldValue.increment()
        transaction.update(refProduct,COUNT, FieldValue.increment(number))
        transaction.update(refProduct, LAST_MODIFICATION, currentDate)
        transaction.set(refHistory, History(CURRENT_USER, currentDate, "Ajout " + number.toString() + " au comptage", product.designation, product.getCode() ))

        // Success
        null
    }.addOnSuccessListener { Log.d(TAG, "Transaction success!") }
        .addOnFailureListener { e -> Log.w(TAG, "Transaction failure.", e) }
}