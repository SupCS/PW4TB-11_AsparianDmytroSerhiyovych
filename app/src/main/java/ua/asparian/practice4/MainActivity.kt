package ua.asparian.practice4

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen(
                onExample1Click = {
                    val intent = Intent(this, Example7_1Activity::class.java)
                    startActivity(intent)
                },
                onExample2Click = {
                    val intent = Intent(this, Example7_2Activity::class.java)
                    startActivity(intent)
                },
                onExample3Click = {
                    val intent = Intent(this, Example7_3Activity::class.java)
                    startActivity(intent)
                }
            )
        }
    }
}

@Composable
fun MainScreen(
    onExample1Click: () -> Unit,
    onExample2Click: () -> Unit,
    onExample3Click: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(onClick = onExample1Click, modifier = Modifier.fillMaxWidth()) {
            Text("Приклад 7.1")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onExample2Click, modifier = Modifier.fillMaxWidth()) {
            Text("Приклад 7.2")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onExample3Click, modifier = Modifier.fillMaxWidth()) {
            Text("Приклад 7.3")
        }
    }
}
