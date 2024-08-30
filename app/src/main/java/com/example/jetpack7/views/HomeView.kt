package com.example.jetpack7.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.jetpack7.datastores.StoreDarkMode
import com.example.jetpack7.datastores.StoreUserEmail
import kotlinx.coroutines.launch

@Composable
fun HomeView(darkModeStore: StoreDarkMode,darkMode:State<Boolean>){
    Surface(modifier = Modifier.fillMaxSize(),color = MaterialTheme.colorScheme.background) {
        ContentHomeView(darkModeStore,darkMode)
    }
}

@Composable
fun ContentHomeView(darkModeStore: StoreDarkMode,darkMode:State<Boolean>){
    Column(horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center, modifier = Modifier.fillMaxSize()) {
        val context = LocalContext.current
        val scope = rememberCoroutineScope()
        val dataStore = StoreUserEmail(context)
        var email by rememberSaveable { mutableStateOf("")}
        val userEmail = dataStore.getEmail.collectAsState(initial = "")
        TextField(value = email, onValueChange = {email = it},
            keyboardOptions = KeyboardOptions().copy(keyboardType = KeyboardType.Email), label = { Text(
                text = "Coloque el Email : ")})
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            scope.launch {
                dataStore.saveEmail(email)
            }
        },colors = ButtonDefaults.buttonColors(containerColor = Color.Magenta, contentColor = Color.Blue)) {
            Text(text = "Guardar Email")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = userEmail.value)
        Button(onClick = {
            scope.launch {
                if(darkMode.value){
                    darkModeStore.saveDarkMode(false)
                }else{
                    darkModeStore.saveDarkMode(true)
                }
            }
        }) {
           if(!darkMode.value){
               Text(text = "Cambiar a DarkMode.")
           }else{
               Text(text = "Cambiar a LightMode.")
           }
        }
        Switch(checked = darkMode.value, onCheckedChange = {isChecked ->
            scope.launch {
                darkModeStore.saveDarkMode(isChecked)
            }
        })
    }
}