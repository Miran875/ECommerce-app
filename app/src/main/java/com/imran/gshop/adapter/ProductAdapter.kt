package com.imran.gshop.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.imran.gshop.activities.ProductDetailActivity
import com.imran.gshop.databinding.LayoutProductItemBinding
import com.imran.gshop.model.AddProductModel


class ProductAdapter(val context: Context, val list: ArrayList<AddProductModel>)

    : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>(){

    inner class ProductViewHolder(val binding : LayoutProductItemBinding)
        : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = LayoutProductItemBinding.inflate(LayoutInflater.from(context),parent,false)
        return ProductViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val data = list[position]

        Glide.with(context).load(data.productCoverImg).into(holder.binding.imageView)
        holder.binding.tv1.text = data.productName
        holder.binding.tv2.text = data.productCategory
        holder.binding.tv3.text = data.productMrp
        holder.binding.btn1.text = data.productSp

        holder.itemView.setOnClickListener {
            val intent = Intent(context, ProductDetailActivity::class.java)
            intent.putExtra("id", list[position].productId)
            context.startActivity(intent)

        }
    }
    override fun getItemCount(): Int {
        return list.size
    }

}