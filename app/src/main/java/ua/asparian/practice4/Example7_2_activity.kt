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

class Example7_2Activity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Example7_2Screen()
        }
    }
}

@Composable
fun Example7_2Screen() {
    var usn by remember { mutableStateOf("") }
    var sk by remember { mutableStateOf("") }
    var resultText by remember { mutableStateOf("Результати з'являться тут") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = usn,
            onValueChange = { usn = it },
            label = { Text("Номінальна напруга Uс.н (кВ)") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = sk,
            onValueChange = { sk = it },
            label = { Text("Потужність КЗ Sк (МВА)") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            val Usn = usn.toDoubleOrNull() ?: 0.0
            val Sk = sk.toDoubleOrNull() ?: 0.0

            val UkPercentage = 10.5 // Відсоток напруги короткого замикання
            val Sn = 6.3

            resultText = if (Usn > 0 && Sk > 0) {
                val Xc = (Usn * Usn) / Sk
                val Xt = (UkPercentage / 100) * ((Usn * Usn) / Sn)
                val Xsum = Xc + Xt
                val Ip0 = (Usn * 1000) / (sqrt(3.0) * Xsum)

                """
                    Опір Xc: ${"%.2f".format(Xc)} Ом
                    Опір Xt: ${"%.2f".format(Xt)} Ом
                    Сумарний опір Xсум: ${"%.2f".format(Xsum)} Ом
                    Початкове діюче значення струму трифазного КЗ (Ip0): ${"%.2f".format(Ip0)} А
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
