package com.example.productcount

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_add_product.*
import org.jetbrains.anko.toast

class AddProductActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_product)
        validation_product.setOnClickListener { addProduct(designation.text.toString(), code_product.text.toString(), date_product.text.toString(), tag.text.toString()) }
    }
}
