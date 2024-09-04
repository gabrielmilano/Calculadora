package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    BasicCalculator()
                }
            }
        }
    }
}

@Composable
fun BasicCalculator() {
    var display by remember { mutableStateOf("0") }
    var operand1 by remember { mutableStateOf("") }
    var operand2 by remember { mutableStateOf("") }
    var operator by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Display
        Text(
            text = display,
            fontSize = 36.sp,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        // Number Buttons and Operators
        val buttons = listOf(
            listOf("7", "8", "9", "/"),
            listOf("4", "5", "6", "*"),
            listOf("1", "2", "3", "-"),
            listOf("0", "C", "=", "+")
        )

        buttons.forEach { row ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                row.forEach { label ->
                    Button(
                        onClick = {
                            when (label) {
                                "C" -> {
                                    display = "0"
                                    operand1 = ""
                                    operand2 = ""
                                    operator = ""
                                }
                                "=" -> {
                                    if (operand1.isNotEmpty() && operand2.isNotEmpty() && operator.isNotEmpty()) {
                                        val result = calculate(operand1.toDouble(), operand2.toDouble(), operator)
                                        display = result.toString()
                                        operand1 = result.toString()
                                        operand2 = ""
                                        operator = ""
                                    }
                                }
                                in listOf("+", "-", "*", "/") -> {
                                    if (operand1.isNotEmpty()) {
                                        operator = label
                                    }
                                }
                                else -> {
                                    if (operator.isEmpty()) {
                                        operand1 += label
                                        display = operand1
                                    } else {
                                        operand2 += label
                                        display = operand2
                                    }
                                }
                            }
                        },
                        modifier = Modifier
                            .weight(1f)
                            .padding(4.dp)
                    ) {
                        Text(text = label, fontSize = 24.sp)
                    }
                }
            }
        }
    }
}

fun calculate(num1: Double, num2: Double, operator: String): Double {
    return when (operator) {
        "+" -> num1 + num2
        "-" -> num1 - num2
        "*" -> num1 * num2
        "/" -> num1 / num2
        else -> 0.0
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyApplicationTheme {
        BasicCalculator()
    }
}
