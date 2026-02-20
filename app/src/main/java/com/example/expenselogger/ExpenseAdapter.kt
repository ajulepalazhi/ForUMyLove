package com.example.expenselogger

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ExpenseAdapter : RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder>() {
    private val items = mutableListOf<Expense>()

    fun submitList(expenses: List<Expense>) {
        items.clear()
        items.addAll(expenses)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_expense, parent, false)
        return ExpenseViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExpenseViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    class ExpenseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val amountText: TextView = itemView.findViewById(R.id.amountText)
        private val categoryText: TextView = itemView.findViewById(R.id.categoryText)
        private val dateText: TextView = itemView.findViewById(R.id.dateText)
        private val paymentMethodText: TextView = itemView.findViewById(R.id.paymentMethodText)

        fun bind(expense: Expense) {
            amountText.text = "₹${expense.amount}"
            categoryText.text = "Category: ${expense.category}"
            dateText.text = "Date: ${expense.date}"
            paymentMethodText.text = "Payment: ${expense.paymentMethod}"
        }
    }
}
