package com.example.corutineapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.corutineapp.ui.theme.CorutineappTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
       // val viewModel : MainViewModel by viewModels()
        val viewModel : ItemsViewModel by viewModels()
        setContent {
            CorutineappTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                   // Content(viewModel)
                    ItemsViews(viewModel)
                }
            }
        }
    }
}

@Composable
fun Content(viewModel: MainViewModel){
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ButtonColor()
        if (viewModel.isLoading){
            CircularProgressIndicator()
        }else{
           Text(viewModel.resultState)
        }
        Button(
            onClick = { viewModel.fetchData()}
        ) {
            Text("Llamar API")
        }
    }
}

@Composable
fun ButtonColor(){
    var color by remember { mutableStateOf(false) }
    Button(
        onClick = { color = !color },
        colors = ButtonDefaults.buttonColors(
            containerColor = if (color) Color.Red else Color.Green
        )
    ) {
        Text("Cambiar color")
    }
}

@Composable
fun ItemsViews(viewModel: ItemsViewModel){
    val itemsList = viewModel.itemsList

    // forma de declarar una variable de tipo flow
    val list by  viewModel.list.collectAsState()

    // efecto para cargar los al iniciar para corutinas
    LaunchedEffect(Unit){
        viewModel.fetchData()
    }

    Column{
        if(viewModel.isLoading){
            CircularProgressIndicator()
        }else{
            LazyColumn{
                items(list){
                    item ->
                    Text(item.name)
                }
            }
        }
    }

}