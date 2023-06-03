package com.imran.gshop.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.imran.gshop.activities.ProductDetailActivity
import com.imran.gshop.databinding.ItemCategoryProductBinding
import com.imran.gshop.model.AddProductModel

class CategoryProductAdapter (val context: Context, val list: ArrayList<AddProductModel>)
    : RecyclerView.Adapter<CategoryProductAdapter.CategoryProductViewHolder>(){

        inner class CategoryProductViewHolder(val binding : ItemCategoryProductBinding)
            : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryProductViewHolder {
       val binding = ItemCategoryProductBinding.inflate(LayoutInflater.from(context),parent,false)
        return  CategoryProductViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: CategoryProductViewHolder, position: Int) {
        Glide.with(context).load(list[position].productCoverImg).into(holder.binding.imageView1)

        holder.binding.textView1.text = list[position].productName
        holder.binding.textView2.text = list[position].productSp

        holder.itemView.setOnClickListener{
            val intent = Intent(context,ProductDetailActivity::class.java)
            intent.putExtra("id", list[position].productId)
            context.startActivity(intent)
        }
    }
}