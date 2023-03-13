package com.art.paybackapp.presentation.screens.search.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.art.paybackapp.R

@Composable
fun SearchBar(
    state: MutableState<TextFieldValue>,
    onSearch: (TextFieldValue) -> Unit = {}
) {

    var isError = rememberSaveable { mutableStateOf(false) }

    fun validate() {
        isError.value = state.value.text.isEmpty()
    }

    fun search(){
        if(state.value.text.isNotEmpty()){
            onSearch(state.value)
        }
    }

    Box {
        OutlinedTextField(
            isError = isError.value,
            supportingText = {
                if(isError.value){
                    Text(
                        text = stringResource(R.string.searchBarError),
                        color = MaterialTheme.colorScheme.error
                    )
                }
            },
            value = state.value,
            onValueChange = {
                state.value = it
                validate()
            },
            placeholder = {
                Text(text = stringResource(R.string.searchBarHint))
            },
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(corner = CornerSize(16.dp)),
            textStyle = TextStyle(fontSize = 15.sp),
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    validate()
                    search()
                }
            ),
            leadingIcon  = {
                IconButton(
                    onClick = {
                        validate()
                        search()
                    }
                ){
                    Icon(imageVector = Icons.Filled.Search, contentDescription = "searchIcon")
                }
            },
            trailingIcon = {
                IconButton(
                    onClick = {
                        state.value = TextFieldValue()
                    }
                ) {
                    Icon(imageVector = Icons.Filled.Clear, contentDescription = "clearIcon")
                }
            }
        )
    }

}