package com.imran.gshop.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.imran.gshop.databinding.AllOrderDetailItemBinding
import com.imran.gshop.model.AllOrderModel


class AllOrderAdapter(val list : ArrayList<AllOrderModel>, val context : Context)
    : RecyclerView.Adapter<AllOrderAdapter.AllOrderViewHolder>() {

    inner class AllOrderViewHolder(val binding: AllOrderDetailItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllOrderViewHolder {
        return AllOrderViewHolder(
            AllOrderDetailItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: AllOrderViewHolder, position: Int) {
        holder.binding.productName.text = list[position].name
        holder.binding.productPrice.text = list[position].price


        when (list[position].status) {
            "Ordered" -> {
                holder.binding.productStatus.text = "Ordered"
            }

            "Dispatched" -> {
                holder.binding.productStatus.text = "Dispatched"
            }
            "Delivered" -> {
                holder.binding.productStatus.text = "Delivered"
            }
            "Canceled" -> {
                holder.binding.productStatus.text = "canceled"
            }
        }

    }
}