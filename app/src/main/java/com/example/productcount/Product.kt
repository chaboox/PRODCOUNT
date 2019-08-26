package com.example.productcount

data class Product(
    val designation: String? = null,
    val code_produit: String? = null,
    val date_code: String? = null,
    val last_modification: String? = null,
    val creation_time: String? = null,
    val tag: String? = null,
    var count: Int? = 1,
    val key :String? = null
){
    fun getCode(): String{ val zeros = "00000000"
     return   date_code + code_produit + zeros.subSequence(0, 5 - count.toString().length ).toString() + count }

}



