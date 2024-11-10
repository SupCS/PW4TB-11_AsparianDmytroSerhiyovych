package ua.asparian.practice4

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.sqrt
import kotlin.math.hypot
import kotlin.math.pow

class Example7_3Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.example7_3_activity)

        // Поля для введення значень
        val inputUxMax: EditText = findViewById(R.id.input_ux_max)
        val inputSnomT: EditText = findViewById(R.id.input_snom_t)
        val inputRcN: EditText = findViewById(R.id.input_rc_n)
        val inputXcN: EditText = findViewById(R.id.input_xc_n)
        val inputRcMin: EditText = findViewById(R.id.input_rc_min)
        val inputXcMin: EditText = findViewById(R.id.input_xc_min)
        val inputUbN: EditText = findViewById(R.id.input_ub_n)
        val inputUnn: EditText = findViewById(R.id.input_unn)

        val buttonCalculate: Button = findViewById(R.id.button_calculate)
        val resultText: TextView = findViewById(R.id.result_text)
        val buttonBack: Button = findViewById(R.id.button_back)

        buttonCalculate.setOnClickListener {
            val Ux_max = inputUxMax.text.toString().toDoubleOrNull() ?: 0.0
            val Snom_t = inputSnomT.text.toString().toDoubleOrNull() ?: 0.0
            val Rc_n = inputRcN.text.toString().toDoubleOrNull() ?: 0.0
            val Xc_n = inputXcN.text.toString().toDoubleOrNull() ?: 0.0
            val Rc_min = inputRcMin.text.toString().toDoubleOrNull() ?: 0.0
            val Xc_min = inputXcMin.text.toString().toDoubleOrNull() ?: 0.0
            val Ub_n = inputUbN.text.toString().toDoubleOrNull() ?: 0.0
            val Unn = inputUnn.text.toString().toDoubleOrNull() ?: 0.0

            if (Ux_max <= 0 || Snom_t <= 0 || Rc_n <= 0 || Xc_n <= 0 || Rc_min <= 0 || Xc_min <= 0 || Ub_n <= 0 || Unn <= 0) {
                resultText.text = "Будь ласка, введіть коректні значення для всіх полів."
                return@setOnClickListener
            }

            val Xt = (Ux_max * Ub_n.pow(2)) / (100 * Snom_t) // Розрахунок Xt

            // Розрахунок для нормального режиму
            val Xsh_n = Xc_n + Xt
            val Zsh_n = hypot(Rc_n, Xsh_n)
            val Ip3_n = (Ub_n * 1000) / (sqrt(3.0) * Zsh_n)
            val Ip2_n = Ip3_n * sqrt(3.0) / 2

            // Розрахунок для мінімального режиму
            val Xsh_min = Xc_min + Xt
            val Zsh_min = hypot(Rc_min, Xsh_min)
            val Ip3_min = (Ub_n * 1000) / (sqrt(3.0) * Zsh_min)
            val Ip2_min = Ip3_min * sqrt(3.0) / 2

            // Коефіцієнт приведення та опори
            val kpr = (Unn.pow(2)) / (Ub_n.pow(2))
            val Rsh_n_adj = Rc_n * kpr
            val Xsh_n_adj = Xsh_n * kpr
            val Zsh_n_adj = hypot(Rsh_n_adj, Xsh_n_adj)

            val Rsh_min_adj = Rc_min * kpr
            val Xsh_min_adj = Xsh_min * kpr
            val Zsh_min_adj = hypot(Rsh_min_adj, Xsh_min_adj)

            // Додавання розрахунків дійсних струмів
            val I3_sh_n = (Unn * 1000) / (sqrt(3.0) * Zsh_n_adj)
            val I2_sh_n = I3_sh_n * sqrt(3.0) / 2
            val I3_sh_min = (Unn * 1000) / (sqrt(3.0) * Zsh_min_adj)
            val I2_sh_min = I3_sh_min * sqrt(3.0) / 2

            // Додаткові розрахунки довжини і опорів
            val Ln = 0.2 + 0.35 + 0.2 + 0.6 + 2 + 2.55 + 3.37 + 3.1 // Загальна довжина
            val R0 = 0.64 // Питомий опір проводу
            val X0 = 0.363 // Питомий реактивний опір проводу

            val Rn = Ln * R0 // Розрахунок резистивного опору
            val Xn = Ln * X0 // Розрахунок реактивного опору

            // Розрахунки опорів в точці 10
            val Rx_n = Rn + Rsh_n_adj
            val Xx_n = Xn + Xsh_n_adj
            val Zx_n = sqrt(Rx_n.pow(2) + Xx_n.pow(2))

            val Rx_min = Rn + Rsh_min_adj
            val Xx_min = Xn + Xsh_min_adj
            val Zx_min = sqrt(Rx_min.pow(2) + Xx_min.pow(2))

            // Розрахунки струмів трифазного і двофазного КЗ в точці 10
            val I3_t10_n = (Unn * 1000) / (sqrt(3.0) * Zx_n)
            val I2_t10_n = I3_t10_n * sqrt(3.0) / 2
            val I3_t10_min = (Unn * 1000) / (sqrt(3.0) * Zx_min)
            val I2_t10_min = I3_t10_min * sqrt(3.0) / 2


            resultText.text = """
                Xt: ${"%.2f".format(Xt)} Ом
                Xш в нормальному режимі: ${"%.2f".format(Xsh_n)} Ом
                Zш в нормальному режимі: ${"%.2f".format(Zsh_n)} Ом
                Струм трифазного КЗ (Ip3) в нормальному режимі: ${"%.2f".format(Ip3_n)} А
                Струм двофазного КЗ (Ip2) в нормальному режимі: ${"%.2f".format(Ip2_n)} А
                Xш в мінімальному режимі: ${"%.2f".format(Xsh_min)} Ом
                Zш в мінімальному режимі: ${"%.2f".format(Zsh_min)} Ом
                Струм трифазного КЗ (Ip3) в мінімальному режимі: ${"%.2f".format(Ip3_min)} А
                Струм двофазного КЗ (Ip2) в мінімальному режимі: ${"%.2f".format(Ip2_min)} А
                Коефіцієнт приведення (kpr): ${"%.3f".format(kpr)}
                Rш в нормальному режимі, приведений: ${"%.2f".format(Rsh_n_adj)} Ом
                Xш в нормальному режимі, приведений: ${"%.2f".format(Xsh_n_adj)} Ом
                Zш в нормальному режимі, приведений: ${"%.2f".format(Zsh_n_adj)} Ом
                Rш в мінімальному режимі, приведений: ${"%.2f".format(Rsh_min_adj)} Ом
                Xш в мінімальному режимі, приведений: ${"%.2f".format(Xsh_min_adj)} Ом
                Zш в мінімальному режимі, приведений: ${"%.2f".format(Zsh_min_adj)} Ом
                Дійсний струм трифазного КЗ (I3) в нормальному режимі: ${"%.2f".format(I3_sh_n)} А
                Дійсний струм двофазного КЗ (I2) в нормальному режимі: ${"%.2f".format(I2_sh_n)} А
                Дійсний струм трифазного КЗ (I3) в мінімальному режимі: ${"%.2f".format(I3_sh_min)} А
                Дійсний струм двофазного КЗ (I2) в мінімальному режимі: ${"%.2f".format(I2_sh_min)} А
                Довжина проводу (Ln): ${"%.2f".format(Ln)} км
                Резистивний опір (Rn): ${"%.2f".format(Rn)} Ом
                Реактивний опір (Xn): ${"%.2f".format(Xn)} Ом
                Rx в нормальному режимі: ${"%.2f".format(Rx_n)} Ом
                Xx в нормальному режимі: ${"%.2f".format(Xx_n)} Ом
                Zx в нормальному режимі: ${"%.2f".format(Zx_n)} Ом
                Rx в мінімальному режимі: ${"%.2f".format(Rx_min)} Ом
                Xx в мінімальному режимі: ${"%.2f".format(Xx_min)} Ом
                Zx в мінімальному режимі: ${"%.2f".format(Zx_min)} Ом
                Струм трифазного КЗ в точці 10 (I3) в нормальному режимі: ${"%.2f".format(I3_t10_n)} А
                Струм двофазного КЗ в точці 10 (I2) в нормальному режимі: ${"%.2f".format(I2_t10_n)} А
                Струм трифазного КЗ в точці 10 (I3) в мінімальному режимі: ${"%.2f".format(I3_t10_min)} А
                Струм двофазного КЗ в точці 10 (I2) в мінімальному режимі: ${"%.2f".format(I2_t10_min)} А
            """.trimIndent()
        }

        buttonBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}
