package com.charan.expensetracker

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private val expenses = ArrayList<Expense>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val tvDate = findViewById<TextView>(R.id.tvDate)
        val etTitle = findViewById<EditText>(R.id.etTitle)
        val etAmount = findViewById<EditText>(R.id.etAmount)
        val btnAdd = findViewById<Button>(R.id.btnAdd)
        val btnClear = findViewById<Button>(R.id.btnClear)
        val tvTotal = findViewById<TextView>(R.id.tvTotal)
        val tvExpenses = findViewById<TextView>(R.id.tvExpenses)

        // Today's Date
        val formatter =
            SimpleDateFormat("dd MMM yyyy", Locale.getDefault())

        val today =
            formatter.format(Date())

        tvDate.text = today

        btnAdd.setOnClickListener {

            val title =
                etTitle.text.toString().trim()

            val amountText =
                etAmount.text.toString().trim()

            if (title.isEmpty() || amountText.isEmpty()) {
                return@setOnClickListener
            }

            val amount =
                amountText.toDouble()

            expenses.add(
                Expense(
                    title,
                    amount,
                    today
                )
            )

            var total = 0.0
            var expenseHistory = ""

            for (expense in expenses) {

                total += expense.amount

                expenseHistory +=
                    "📅 ${expense.date}\n" +
                            "• ${expense.title} - ₹${expense.amount}\n\n"
            }

            tvTotal.text =
                "💰 This Month: ₹$total"

            tvExpenses.text =
                expenseHistory

            etTitle.text.clear()
            etAmount.text.clear()
        }

        btnClear.setOnClickListener {

            expenses.clear()

            tvExpenses.text = ""

            tvTotal.text =
                "💰 This Month: ₹0"

            etTitle.text.clear()
            etAmount.text.clear()
        }

        ViewCompat.setOnApplyWindowInsetsListener(
            findViewById(R.id.main)
        ) { v, insets ->

            val systemBars =
                insets.getInsets(
                    WindowInsetsCompat.Type.systemBars()
                )

            v.setPadding(
                systemBars.left,
                systemBars.top,
                systemBars.right,
                systemBars.bottom
            )

            insets
        }
    }
}