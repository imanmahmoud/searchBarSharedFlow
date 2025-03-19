package com.example.searchdemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text

import androidx.compose.ui.tooling.preview.Preview
import com.example.searchdemo.ui.theme.SearchDemoTheme

import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SearchScreen()
        }
    }

    @Composable
    fun SearchScreen() {
        val names = listOf("mohamed", "ahmed", "ali", "mohamed ali", "ahmed ali", "Ahmed","iman")
        val searchQuery = remember { MutableSharedFlow<String>() }
        val coroutineScope = rememberCoroutineScope()

        var searchText by remember { mutableStateOf("") }
        var result by remember { mutableStateOf("") }

        LaunchedEffect(Unit) {
            coroutineScope.launch {
                searchQuery.collect { query ->
                    result = names.filter { it.startsWith(query, ignoreCase = true) }
                        .joinToString("\n")
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            TextField(
                value = searchText,
                onValueChange = {
                    searchText = it
                    coroutineScope.launch {
                        searchQuery.emit(it)
                    }
                },
                label = {Text("Search")},
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                if (result.isNotEmpty()) result else "No results found"
            )
        }
    }
}