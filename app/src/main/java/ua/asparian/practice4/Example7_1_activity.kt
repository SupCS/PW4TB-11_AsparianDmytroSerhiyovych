package ua.asparian.practice4

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.sqrt

class Example7_1Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.example7_1_activity)

        // Поля для введення значень
        val inputSm: EditText = findViewById(R.id.input_sm) // Розрахункове навантаження в кВА
        val inputIk: EditText = findViewById(R.id.input_ik) // Струм короткого замикання в А
        val inputTf: EditText = findViewById(R.id.input_tf) // Час дії струму КЗ в секундах
        val inputUnom: EditText = findViewById(R.id.input_unom) // Номінальна напруга в кВ
        val inputTm: EditText = findViewById(R.id.input_tm) // Розрахункова тривалість роботи в годинах

        val buttonCalculate: Button = findViewById(R.id.button_calculate)
        val resultText: TextView = findViewById(R.id.result_text)
        val buttonBack: Button = findViewById(R.id.button_back)

        buttonCalculate.setOnClickListener {
            val Sm = inputSm.text.toString().toDoubleOrNull() ?: 0.0
            val Ik = inputIk.text.toString().toDoubleOrNull() ?: 0.0
            val tf = inputTf.text.toString().toDoubleOrNull() ?: 0.0
            val Unom = inputUnom.text.toString().toDoubleOrNull() ?: 0.0
            val Tm = inputTm.text.toString().toDoubleOrNull() ?: 0.0

            // Стандартні значення для розрахунків
            val Jek = 1.4 // Густина струму
            val Ct = 92.0 // Константа для термічної стійкості

            if (Sm > 0 && Ik > 0 && tf > 0 && Unom > 0 && Tm > 0) {
                // Розрахунок струму для нормального режиму
                val Im = Sm / (2 * sqrt(3.0) * Unom)
                Log.d("Calculation", "Розрахунковий струм для нормального режиму (Im): $Im")

                val ImPa = 2 * Im // Післяаварійний струм
                Log.d("Calculation", "Розрахунковий струм для післяаварійного режиму (ImPa): $ImPa")

                // Розрахунок економічного перерізу
                val Sek = Im / Jek
                Log.d("Calculation", "Економічний переріз (Sek): $Sek")

                // Розрахунок мінімального перерізу для термічної стійкості
                val Smin = (Ik * sqrt(tf)) / Ct
                Log.d("Calculation", "Мінімальний переріз для термічної стійкості (Smin): $Smin")

                resultText.text = """
                    Розрахунковий струм для нормального режиму: ${"%.2f".format(Im)} A
                    Розрахунковий струм для післяаварійного режиму: ${"%.2f".format(ImPa)} A
                    Економічний переріз: ${"%.2f".format(Sek)} мм²
                    Мінімальний переріз для термічної стійкості: ${"%.2f".format(Smin)} мм²
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
