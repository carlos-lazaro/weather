package com.me.weather.presentation.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.me.weather.R
import com.me.weather.presentation.ui.preview.SearchTextFieldPreviewData
import com.me.weather.presentation.ui.preview.SearchTextFieldPreviewProvider
import com.me.weather.presentation.ui.preview.ThemePreview
import com.me.weather.presentation.ui.theme.WeatherTheme
import com.me.weather.presentation.utils.RequestState

@Composable
fun SearchTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = stringResource(R.string.search),
    requestState: RequestState<Any>,
    onSearch: () -> Unit = {},
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    OutlinedTextField(
        value = value,
        enabled = requestState !is RequestState.Loading,
        onValueChange = onValueChange,
        placeholder = { Text(placeholder.replaceFirstChar { it.uppercase() }) },
        leadingIcon = {
            value.takeIf { it.isNotEmpty() }?.let {
                IconButton(
                    onClick = {
                        onValueChange("")
                        keyboardController?.hide()
                    },
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = stringResource(R.string.close),
                    )
                }
            }
        },
        trailingIcon = {
            if (requestState is RequestState.Loading) {
                CircularProgressIndicator()
            } else {
                IconButton(
                    onClick = {
                        onSearch()
                        keyboardController?.hide()
                    },
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = stringResource(R.string.search),
                    )
                }
            }
        },
        singleLine = true,
        shape = RoundedCornerShape(50),
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(
            onSearch = {
                onSearch()
                keyboardController?.hide()
            }
        ),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.outline,
            focusedLabelColor = MaterialTheme.colorScheme.primary,
            cursorColor = MaterialTheme.colorScheme.primary,
        ),
        modifier = modifier
            .fillMaxWidth()
    )
}

@ThemePreview
@Composable
private fun Preview(
    @PreviewParameter(SearchTextFieldPreviewProvider::class) data: SearchTextFieldPreviewData,
) {
    WeatherTheme {
        Surface {
            SearchTextField(
                value = data.value,
                onValueChange = {},
                requestState = data.requestState,
                onSearch = {},
            )
        }
    }
}
