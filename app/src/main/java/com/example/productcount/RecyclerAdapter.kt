/*
 * Copyright (c) 2019 Razeware LLC
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.example.productcount

import android.content.Intent
import android.opengl.Visibility
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.recyclerview_item_row.view.*
import org.jetbrains.anko.toast

class RecyclerAdapter(private val products: ArrayList<Product>) : RecyclerView.Adapter<RecyclerAdapter.PhotoHolder>() {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapter.PhotoHolder {
    val inflatedView = parent.inflate(R.layout.recyclerview_item_row, false)
    return PhotoHolder(inflatedView)
  }

  override fun getItemCount(): Int = products.size

  override fun onBindViewHolder(holder: RecyclerAdapter.PhotoHolder, position: Int) {
    val itemPhoto = products[position]
    holder.bindPhoto(itemPhoto)
  }

  //1
  class PhotoHolder(private val view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
    //2
    private var product: Product? = null

    //3
    init {
      view.setOnClickListener(this)
    }

    fun bindPhoto(product: Product) {
      this.product = product
      val zeros = "00000000"
      //Picasso.with(view.context).load(product.url).into(view.itemImage)
      view.itemDate.text = product.designation
      val code = zeros.subSequence(0, 5 - product.count.toString().length ).toString() + product.count.toString()
      view.itemDescription.text = product.date_code + product.code_produit + code
      view.add_count.setOnClickListener {
        product.count =  view.number.text.toString().toInt() + product.count.toString().toInt()
        val code = zeros.subSequence(0, 5 - product.count.toString().length ).toString() + product.count.toString()
        view.itemDescription.text = product.date_code + product.code_produit + code
        updateCount(product, view.number.text.toString().toLong())
        view.number.setText("")
        view.add_number_layout.visibility = View.GONE}
    }

    //4
    override fun onClick(v: View) {
      val context = itemView.context
      val showPhotoIntent = Intent(context, PhotoActivity::class.java)
     // showPhotoIntent.putExtra(PHOTO_KEY, product)
//      context.startActivity(showPhotoIntent)
      Log.d("RecyclerView", "CLICK!")
      if(v.add_number_layout.visibility == View.GONE)
        v.add_number_layout.visibility = View.VISIBLE

      else{
        view.number.setText("")
        v.add_number_layout.visibility = View.GONE
    }}

    companion object {
      //5
      private val PHOTO_KEY = "PHOTO"
    }
  }

}