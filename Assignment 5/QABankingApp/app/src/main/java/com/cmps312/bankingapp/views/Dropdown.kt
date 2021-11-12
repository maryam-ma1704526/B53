package com.cmps312.bankingapp.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier

@Composable
fun Dropdown(
    label: String, options: List<String>,
    selectedOption: String,
    onSelectionChange: (String) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }

    val trailingIcon = if (expanded)
        Icons.Filled.ArrowDropUp
    else
        Icons.Filled.ArrowDropDown

    Column {
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = !expanded },
            value = selectedOption,
            onValueChange = onSelectionChange,
            label = { Text(text = label) },
            trailingIcon = {
                Icon(trailingIcon, "ArrowIcon",
                    Modifier.clickable { expanded = !expanded })
            },
            readOnly = true
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            options.forEach { option ->
                DropdownMenuItem(onClick = {
                    onSelectionChange(option)
                    expanded = false
                }) {
                    Text(text = option)
                }
            }
        }
    }
}