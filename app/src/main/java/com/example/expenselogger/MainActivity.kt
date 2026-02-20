package com.example.expenselogger

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var amountInput: TextInputEditText
    private lateinit var categoryInput: TextInputEditText
    private lateinit var dateInput: TextInputEditText
    private lateinit var paymentMethodInput: TextInputEditText
    private lateinit var saveButton: MaterialButton
    private lateinit var expenseAdapter: ExpenseAdapter

    private val viewModel: ExpenseViewModel by viewModels {
        ExpenseViewModelFactory(AppDatabase.getDatabase(this).expenseDao())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        amountInput = findViewById(R.id.amountInput)
        categoryInput = findViewById(R.id.categoryInput)
        dateInput = findViewById(R.id.dateInput)
        paymentMethodInput = findViewById(R.id.paymentMethodInput)
        saveButton = findViewById(R.id.saveButton)

        expenseAdapter = ExpenseAdapter()
        findViewById<RecyclerView>(R.id.expenseRecyclerView).adapter = expenseAdapter

        saveButton.setOnClickListener {
            saveExpense()
        }

        lifecycleScope.launch {
            viewModel.expenses.collect { expenses ->
                expenseAdapter.submitList(expenses)
            }
        }
    }

    private fun saveExpense() {
        val amountValue = amountInput.text?.toString()?.trim().orEmpty()
        val categoryValue = categoryInput.text?.toString()?.trim().orEmpty()
        val dateValue = dateInput.text?.toString()?.trim().orEmpty()
        val paymentMethodValue = paymentMethodInput.text?.toString()?.trim().orEmpty()

        val amount = amountValue.toDoubleOrNull()

        if (amount == null || categoryValue.isBlank() || dateValue.isBlank() || paymentMethodValue.isBlank()) {
            Toast.makeText(this, "Please fill all fields with valid values.", Toast.LENGTH_SHORT).show()
            return
        }

        viewModel.addExpense(amount, categoryValue, dateValue, paymentMethodValue)
        amountInput.text?.clear()
        categoryInput.text?.clear()
        dateInput.text?.clear()
        paymentMethodInput.text?.clear()
        Toast.makeText(this, "Expense saved", Toast.LENGTH_SHORT).show()
    }
}
