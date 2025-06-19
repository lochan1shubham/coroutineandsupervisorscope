package com.example.coroutineandsupervisorscope

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.coroutineandsupervisorscope.ui.theme.CoroutineAndSupervisorScopeTheme
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CoroutineAndSupervisorScopeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android", modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
//        TODO: Coroutine behaviour with Exception
        GlobalScope.launch {
//            supervisorScopeM()
            coroutineScopeM()
        }
    }
}

// TODO: Stop other child coroutines after exception
// TODO: If want to stop API calling if any API fail then we should use coroutineScope
suspend fun coroutineScopeM() {
    coroutineScope {
        launch {
            Log.d("TAG", "coroutineScope1")
        }
        launch {
            throw ExceptionInInitializerError("IllegalStateException")
            Log.d("TAG", "coroutineScope2")
        }
        launch {
            Log.d("TAG", "coroutineScope3")

        }
        launch {
            Log.d("TAG", "coroutineScope4")
        }
    }.join()
}

// TODO: Except exception child continue to other children coroutines after exception
// TODO: If want to call multiple API even an API fail then we should follow supervisorScope
suspend fun supervisorScopeM() {
    supervisorScope {
        launch {
            Log.d("TAG", "supervisorScope1")
        }
        launch {
            throw ExceptionInInitializerError("IllegalStateException")
            Log.d("TAG", "supervisorScope2")
        }
        launch {
            Log.d("TAG", "supervisorScope3")

        }
        launch {
            Log.d("TAG", "supervisorScope4")
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!", modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CoroutineAndSupervisorScopeTheme {
        Greeting("Android")
    }
}
