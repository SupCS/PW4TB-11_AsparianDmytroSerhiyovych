package ua.asparian.practice4

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.sqrt

class Example7_2Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.example7_2_activity)

        // Поля для введення значень
        val inputUsn: EditText = findViewById(R.id.input_usn) // Номінальна напруга в кВ
        val inputSk: EditText = findViewById(R.id.input_sk) // Потужність КЗ в МВА

        val buttonCalculate: Button = findViewById(R.id.button_calculate)
        val resultText: TextView = findViewById(R.id.result_text)
        val buttonBack: Button = findViewById(R.id.button_back)

        buttonCalculate.setOnClickListener {
            val Usn = inputUsn.text.toString().toDoubleOrNull() ?: 0.0
            val Sk = inputSk.text.toString().toDoubleOrNull() ?: 0.0

            // Константи для розрахунків
            val UkPercentage = 10.5 // Відсоток напруги короткого замикання
            val Sn = 6.3

            if (Usn > 0 && Sk > 0) {
                // Розрахунок опору елементів
                val Xc = (Usn * Usn) / Sk
                Log.d("Calculation", "Опір Xc: $Xc Ом")

                val Xt = (UkPercentage / 100) * ((Usn * Usn) / Sn)
                Log.d("Calculation", "Опір Xt: $Xt Ом")

                val Xsum = Xc + Xt
                Log.d("Calculation", "Сумарний опір Xсум: $Xsum Ом")

                // Розрахунок початкового струму КЗ
                val Ip0 = (Usn * 1000) / (sqrt(3.0) * Xsum)
                Log.d("Calculation", "Початкове діюче значення струму трифазного КЗ (Ip0): $Ip0 А")

                resultText.text = """
                    Опір Xc: ${"%.2f".format(Xc)} Ом
                    Опір Xt: ${"%.2f".format(Xt)} Ом
                    Сумарний опір Xсум: ${"%.2f".format(Xsum)} Ом
                    Початкове діюче значення струму трифазного КЗ (Ip0): ${"%.2f".format(Ip0)} А
                """.trimIndent()
            } else {
                resultText.text = "Будь ласка, введіть коректні значення"
            }
        }

        buttonBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}
