package com.imran.gshop.activities

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.imran.gshop.R
import com.imran.gshop.databinding.ActivityAddressBinding
import com.imran.gshop.databinding.ActivityLoginBinding

class AddressActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddressBinding

    private lateinit var preferences :SharedPreferences
    private lateinit var totalCost : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddressBinding.inflate(layoutInflater)
        setContentView(binding.root)

        preferences = this.getSharedPreferences("user", MODE_PRIVATE)

        totalCost = intent.getStringExtra("totalCost")!!

        loadUserInfo()

        binding.proceed.setOnClickListener{
            validateData(
            binding.etUserNumber.text.toString(),
            binding.etUserName.text.toString(),
            binding.etVillage.text.toString(),
            binding.etCity.text.toString(),
            binding.etState.text.toString(),
            binding.etPinCode.text.toString()
            )
        }
    }

    private fun validateData(number:String, name:String, village:String,
                             city:String, state:String, pinCode:String) {
        if (number.isEmpty() || name.isEmpty() || village.isEmpty() || city.isEmpty() || state.isEmpty() || pinCode.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
        } else {
            storeData(village,city,state,pinCode)
        }
    }

    private fun storeData( village: String, city: String, state: String, pinCode: String) {
        val map = hashMapOf<String, Any>()
        map["village"] = village
        map["state"] = state
        map["city"] = city
        map["pinCode"] = pinCode

        Firebase.firestore.collection("users")
            .document(preferences.getString("number","")!!)
            .update(map).addOnSuccessListener {
                val b = Bundle()
                b.putStringArrayList("productIds",intent.getStringArrayListExtra("productIds"))
                b.putString("totalCost", totalCost)
                val intent = Intent(this, CheckoutActivity::class.java)
                intent.putExtras(b)
                startActivity(intent)
            }.addOnFailureListener   {
                Toast.makeText(this, "Something is wrong", Toast.LENGTH_SHORT).show()
            }
    }

    private fun loadUserInfo() {
        Firebase.firestore.collection("users")
            .document(preferences.getString("number","")!!)
            .get().addOnSuccessListener {
                binding.etUserName.setText(it.getString("userName"))
                binding.etUserNumber.setText(it.getString("userPhoneNumber"))
                binding.etVillage.setText(it.getString("village"))
                binding.etCity.setText(it.getString("city"))
                binding.etState.setText(it.getString("state"))
                binding.etPinCode.setText(it.getString("pinCode"))
            }
            .addOnFailureListener{

            }
    }
}
