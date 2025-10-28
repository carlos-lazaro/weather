package com.me.weather.presentation.ui.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.me.weather.presentation.utils.RequestState

data class SearchTextFieldPreviewData(
    val value: String,
    val requestState: RequestState<Any>
)

class SearchTextFieldPreviewProvider : PreviewParameterProvider<SearchTextFieldPreviewData> {
    override val values = sequenceOf(
        SearchTextFieldPreviewData(
            value = "",
            requestState = RequestState.Idle
        ),
        SearchTextFieldPreviewData(
            value = "Medellín",
            requestState = RequestState.Success(Unit)
        ),
        SearchTextFieldPreviewData(
            value = "London",
            requestState = RequestState.Loading
        ),
        SearchTextFieldPreviewData(
            value = "Tokyo",
            requestState = RequestState.Error("")
        )
    )
}
