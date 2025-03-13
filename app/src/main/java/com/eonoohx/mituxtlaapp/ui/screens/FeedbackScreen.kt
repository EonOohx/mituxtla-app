package com.eonoohx.mituxtlaapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.eonoohx.mituxtlaapp.ui.theme.MiTuxtlaAppTheme

@Composable
fun FeedbackScreen(modifier: Modifier = Modifier) {
    var textInput by rememberSaveable {
        mutableStateOf(value = "")
    }
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Text(text = "Share your feedback", style = MaterialTheme.typography.headlineLarge)
        Text(text = "text about sharing feedback")
        TextField(
            value = textInput,
            onValueChange = { textInput = it },
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
        )
        Button(
            onClick = {},
            modifier = Modifier
                .width(160.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            Text(text = "Submit")
        }
    }
}

@Composable
@Preview(showBackground = true)
fun FeedbackScreenPreview() {
    MiTuxtlaAppTheme {
        FeedbackScreen(modifier = Modifier.fillMaxSize())
    }
}