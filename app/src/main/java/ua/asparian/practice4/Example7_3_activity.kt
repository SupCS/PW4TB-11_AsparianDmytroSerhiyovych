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
import kotlin.math.hypot
import kotlin.math.pow
import kotlin.math.sqrt

class Example7_3Activity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Example7_3Screen()
        }
    }
}

@Composable
fun Example7_3Screen() {
    var uxMax by remember { mutableStateOf("") }
    var snomT by remember { mutableStateOf("") }
    var rcN by remember { mutableStateOf("") }
    var xcN by remember { mutableStateOf("") }
    var rcMin by remember { mutableStateOf("") }
    var xcMin by remember { mutableStateOf("") }
    var ubN by remember { mutableStateOf("") }
    var unn by remember { mutableStateOf("") }
    var resultText by remember { mutableStateOf("Результати з'являться тут") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = uxMax,
            onValueChange = { uxMax = it },
            label = { Text("Максимальна напруга Ux_max") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = snomT,
            onValueChange = { snomT = it },
            label = { Text("Номінальна потужність Snom_t") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = rcN,
            onValueChange = { rcN = it },
            label = { Text("Опір Rc_n в нормальному режимі") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = xcN,
            onValueChange = { xcN = it },
            label = { Text("Реактивний опір Xc_n в нормальному режимі") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = rcMin,
            onValueChange = { rcMin = it },
            label = { Text("Опір Rc_min в мінімальному режимі") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = xcMin,
            onValueChange = { xcMin = it },
            label = { Text("Реактивний опір Xc_min в мінімальному режимі") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = ubN,
            onValueChange = { ubN = it },
            label = { Text("Базова напруга Ub_n") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = unn,
            onValueChange = { unn = it },
            label = { Text("Реактивний опір трансформатора Unn") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            val Ux_max = uxMax.toDoubleOrNull() ?: 0.0
            val Snom_t = snomT.toDoubleOrNull() ?: 0.0
            val Rc_n = rcN.toDoubleOrNull() ?: 0.0
            val Xc_n = xcN.toDoubleOrNull() ?: 0.0
            val Rc_min = rcMin.toDoubleOrNull() ?: 0.0
            val Xc_min = xcMin.toDoubleOrNull() ?: 0.0
            val Ub_n = ubN.toDoubleOrNull() ?: 0.0
            val Unn = unn.toDoubleOrNull() ?: 0.0

            resultText = if (Ux_max > 0 && Snom_t > 0 && Rc_n > 0 && Xc_n > 0 && Rc_min > 0 && Xc_min > 0 && Ub_n > 0 && Unn > 0) {
                val Xt = (Ux_max * Ub_n.pow(2)) / (100 * Snom_t)
                val Xsh_n = Xc_n + Xt
                val Zsh_n = hypot(Rc_n, Xsh_n)
                val Ip3_n = (Ub_n * 1000) / (sqrt(3.0) * Zsh_n)
                val Ip2_n = Ip3_n * sqrt(3.0) / 2

                """
                    Xt: ${"%.2f".format(Xt)} Ом
                    Xш в нормальному режимі: ${"%.2f".format(Xsh_n)} Ом
                    Zш в нормальному режимі: ${"%.2f".format(Zsh_n)} Ом
                    Струм трифазного КЗ (Ip3): ${"%.2f".format(Ip3_n)} А
                    Струм двофазного КЗ (Ip2): ${"%.2f".format(Ip2_n)} А
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
