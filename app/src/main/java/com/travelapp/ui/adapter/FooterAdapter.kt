package com.travelapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.travelapp.databinding.ItemAdapterListBinding
import com.travelapp.databinding.ItemLoaderBinding

class FooterAdapter : LoadStateAdapter<FooterAdapter.ViewHolder>()  {
    class ViewHolder(val bindig : ItemLoaderBinding) : RecyclerView.ViewHolder(bindig.root) {

    }

    override fun onBindViewHolder(holder: FooterAdapter.ViewHolder, loadState: LoadState) {
        if (loadState == LoadState.Loading) {
           holder.bindig.progress.visibility=View.VISIBLE
        } else {
        //h
            holder.bindig.progress.visibility=View.GONE
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): FooterAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemLoaderBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }
}