package com.imran.gshop.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.imran.gshop.databinding.ActivityCheckoutBinding
import com.imran.gshop.roomdb.AppDatabase
import com.imran.gshop.roomdb.ProductModel
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject

class CheckoutActivity : AppCompatActivity(),PaymentResultListener {
    private lateinit var binding: ActivityCheckoutBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityCheckoutBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val checkout = Checkout()
        checkout.setKeyID("rzp_test_6CPvOZ0r8NCrCV")

        val price = intent.getStringExtra("totalCost")!!

        try {
            val options = JSONObject()
            options.put("name","GShop")
            options.put("description","Best Ecommerce app")
            //You can omit the image option to fetch the image from the dashboard
//            options.put("image","")
            options.put("image","https://s3.amazonaws.com/rzp-mobile/images/rzp.jpg")
            options.put("theme.color", "#A066E8");
            options.put("currency","INR");
            options.put("amount",(price!!.toInt()*100))
//            options.put("order_id", "order_DBJOWzybf0sJbb");
//            options.put("amount","50000")//pass amount in currency subunits
            options.put( "prefill.email","ialam875@gmail.com")
            options.put( "prefill.contact","7278534705")
            checkout.open(this@CheckoutActivity,options)
        }catch (e: Exception){
            Toast.makeText(this,"Error in payment: ", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onPaymentSuccess(p0: String?) {
        Toast.makeText(this,"Payment Success : ", Toast.LENGTH_SHORT).show()
        uploadData()

    }

    private fun uploadData() {
        val id = intent.getStringArrayListExtra("productIds")
        for(currentId in id!!){
            fetchData(currentId)
        }
    }

    private fun fetchData(productId: String?) {

        val dao = AppDatabase.getInstance(this).productDao()

        Firebase.firestore.collection("products")
            .document(productId!!).get().addOnSuccessListener {

                lifecycleScope.launch(Dispatchers.IO){

                dao.deleteProduct(ProductModel(productId))
                }



                saveData(it.getString("productName"),
                    it.getString("productSp"),
                productId)
            }
    }

    override fun onPaymentError(p0: Int, p1: String?) {
        Toast.makeText(this, "Payment Failed", Toast.LENGTH_SHORT).show()
    }

    private fun saveData(name: String?, price: String?, productId: String) {
        val preferences = this.getSharedPreferences("user", MODE_PRIVATE)
        val data = hashMapOf<String, Any>()
        data["name"] = name!!
        data["price"] = price!!
        data["productId"] = productId
        data["status"] = "Ordered"
        data["userId"] = preferences.getString("number","")!!

        val firestore = Firebase.firestore.collection("allOrders")
        val key = firestore.document().id
        data["orderId"] = key

        firestore.document(key).set(data).addOnSuccessListener {
            Toast.makeText(this, "Ordered placed", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }.addOnFailureListener {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
        }
    }

}
