package com.bettercalltolya.kevintask.ui.exchange.balance

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bettercalltolya.domain.model.Balance
import com.bettercalltolya.kevintask.databinding.ViewBalancesListItemBinding

class BalancesAdapter : ListAdapter<Balance, BalancesAdapter.ViewHolder>(diffCallback) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder = ViewHolder(
        ViewBalancesListItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.binding.balance.text = String.format("%.2f %s", item.amount, item.currency)
    }

    inner class ViewHolder(val binding: ViewBalancesListItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<Balance>() {
            override fun areItemsTheSame(oldItem: Balance, newItem: Balance): Boolean =
                oldItem.currency == newItem.currency

            override fun areContentsTheSame(oldItem: Balance, newItem: Balance): Boolean =
                oldItem.amount == newItem.amount
        }
    }
}
