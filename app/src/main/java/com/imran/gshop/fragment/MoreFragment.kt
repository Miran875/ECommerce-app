package com.imran.gshop.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.imran.gshop.R
import com.imran.gshop.activities.AddressActivity
//import com.imran.gshop.activities.AllOrdersActivity
import com.imran.gshop.adapter.AllOrderAdapter
import com.imran.gshop.databinding.FragmentMoreBinding
import com.imran.gshop.model.AllOrderModel

class MoreFragment : Fragment() {

private lateinit var binding : FragmentMoreBinding
private lateinit var list : ArrayList<AllOrderModel>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentMoreBinding.inflate(layoutInflater)
        list = ArrayList()

        val preferences = requireContext().getSharedPreferences("user", AppCompatActivity.MODE_PRIVATE)

        Firebase.firestore.collection("allOrders").whereEqualTo("userId",
            preferences.getString("number", "")!!)
            .get().addOnSuccessListener {
            list.clear()
            for (doc in it){
                val data = doc.toObject(AllOrderModel::class.java)
                list.add(data)
            }
            binding.recyclerViewMore.adapter = AllOrderAdapter(list,requireContext())
        }

        return binding.root
    }

}