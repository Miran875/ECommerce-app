//package com.imran.gshop.activities
//
//import android.content.SharedPreferences
//import androidx.appcompat.app.AppCompatActivity
//import android.os.Bundle
//import androidx.core.content.ContentProviderCompat.requireContext
//import com.google.firebase.firestore.ktx.firestore
//import com.google.firebase.ktx.Firebase
//import com.imran.gshop.adapter.AllOrderAdapter
//import com.imran.gshop.databinding.ActivityAllOrdersBinding
//
//import com.imran.gshop.model.AllOrderModel
//
//class AllOrdersActivity : AppCompatActivity() {
//    private lateinit var binding: ActivityAllOrdersBinding
//    private lateinit var list : ArrayList<AllOrderModel>
//    private lateinit var preferences : SharedPreferences
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivityAllOrdersBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        list = ArrayList()
//
//        preferences = this.getSharedPreferences("user", MODE_PRIVATE)
//        Firebase.firestore.collection("allOrders").whereEqualTo(
//            "userId",
//            preferences.getString("number", "")!!
//        )
//            .get().addOnSuccessListener {
//                list.clear()
//                for (doc in it) {
//                    val data = doc.toObject(AllOrderModel::class.java)
//                    list.add(data)
//
//                    binding.recyclerViewMore.adapter = AllOrderAdapter(list, this)
//                }
//            }
//    }
//}