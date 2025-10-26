package com.me.weather.presentation.ui.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.me.weather.domain.model.Record

class SingleRecordPreviewProvider : PreviewParameterProvider<Record> {
    override val values = sequenceOf(
        Record(
            id = 2,
            name = "Medellín",
            country = "CO",
            lat = 6.2442,
            lon = -75.5812,
        ),
    )
}
