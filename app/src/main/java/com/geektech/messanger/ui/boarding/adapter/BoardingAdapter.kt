package com.geektech.messanger.ui.boarding.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.geektech.messanger.databinding.ItemViewPagerBinding
import com.geektech.messanger.model.Board

class BoardingAdapter:RecyclerView.Adapter<BoardingAdapter.BoardingViewHolder>() {
    private val boardList = arrayListOf(
        Board("","",""),
        Board("","",""),
        Board("","","")
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BoardingViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: BoardingViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int = boardList.size
    inner class BoardingViewHolder(private val binding:ItemViewPagerBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(board : Board){

        }
    }
}