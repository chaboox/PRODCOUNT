package com.example.productcount

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.View
import kotlinx.android.synthetic.main.activity_add_product.*
import org.jetbrains.anko.toast
import java.text.SimpleDateFormat
import java.util.*

class AddProductActivity : AppCompatActivity() {
    private var handler: Handler? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_product)
        handler = AddProductHandler()
        //TODO: Add if ref dont existe
        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        val zeros = "00000000"
        val currentDate = sdf.format(Date())
        validation_product.setOnClickListener {
            add_progress.setVisibility(View.VISIBLE)
            val matricul =  code_product.text.toString() + date_product.text

            val product = Product(designation.text.toString(), code_product.text.toString(), date_product.text.toString(),
                currentDate, currentDate, tag.text.toString(), 1, matricul)
            val code = zeros.subSequence(0, 5 - product.count.toString().length ).toString() + product.count.toString()

            addProduct(product, handler as AddProductHandler)
            //addProduct(designation.text.toString(), code_product.text.toString(), date_product.text.toString(), tag.text.toString(),
            //handler as AddProductHandler)
        }
    }

    inner class AddProductHandler : Handler() {
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                PRODUCT_ADDED-> {
                    toast("Produit ajouté")
                    finish()}

                PRODUCT_ADD_ERROR-> { add_progress.setVisibility(View.VISIBLE)
                    toast("Error!")}

            }
        }
    }
}
