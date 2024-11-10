package ua.asparian.practice4

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonExample7_1: Button = findViewById(R.id.button_example_7_1)
        val buttonExample7_2: Button = findViewById(R.id.button_example_7_2)
        val buttonExample7_3: Button = findViewById(R.id.button_example_7_3)

        buttonExample7_1.setOnClickListener {
            val intent = Intent(this, Example7_1Activity::class.java)
            startActivity(intent)
        }

        buttonExample7_2.setOnClickListener {
            val intent = Intent(this, Example7_2Activity::class.java)
            startActivity(intent)
        }

        buttonExample7_3.setOnClickListener {
            val intent = Intent(this, Example7_3Activity::class.java)
            startActivity(intent)
        }
    }
}
