package ua.asparian.practice4

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import kotlin.math.sqrt

class Example7_1Activity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Example7_1Screen()
        }
    }
}

@Composable
fun Example7_1Screen() {
    var sm by remember { mutableStateOf("") }
    var ik by remember { mutableStateOf("") }
    var tf by remember { mutableStateOf("") }
    var unom by remember { mutableStateOf("") }
    var tm by remember { mutableStateOf("") }
    var resultText by remember { mutableStateOf("Результати з'являться тут") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = sm,
            onValueChange = { sm = it },
            label = { Text("Розрахункове навантаження Sм (кВА)") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = ik,
            onValueChange = { ik = it },
            label = { Text("Струм короткого замикання Iк (А)") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = tf,
            onValueChange = { tf = it },
            label = { Text("Час дії струму КЗ tф (с)") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = unom,
            onValueChange = { unom = it },
            label = { Text("Номінальна напруга Uном (кВ)") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = tm,
            onValueChange = { tm = it },
            label = { Text("Розрахункова тривалість роботи Тм (год)") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            val Sm = sm.toDoubleOrNull() ?: 0.0
            val Ik = ik.toDoubleOrNull() ?: 0.0
            val Tf = tf.toDoubleOrNull() ?: 0.0
            val Unom = unom.toDoubleOrNull() ?: 0.0
            val Tm = tm.toDoubleOrNull() ?: 0.0

            val Jek = 1.4 // Густина струму
            val Ct = 92.0 // Константа для термічної стійкості

            resultText = if (Sm > 0 && Ik > 0 && Tf > 0 && Unom > 0 && Tm > 0) {
                val Im = Sm / (2 * sqrt(3.0) * Unom)
                val ImPa = 2 * Im
                val Sek = Im / Jek
                val Smin = (Ik * sqrt(Tf)) / Ct

                """
                    Розрахунковий струм для нормального режиму: ${"%.2f".format(Im)} A
                    Розрахунковий струм для післяаварійного режиму: ${"%.2f".format(ImPa)} A
                    Економічний переріз: ${"%.2f".format(Sek)} мм²
                    Мінімальний переріз для термічної стійкості: ${"%.2f".format(Smin)} мм²
                """.trimIndent()
            } else {
                "Будь ласка, введіть коректні значення"
            }
        }) {
            Text("Розрахувати")
        }
        Spacer(modifier = Modifier.height(16.dp))

        Text(resultText)
    }
}
