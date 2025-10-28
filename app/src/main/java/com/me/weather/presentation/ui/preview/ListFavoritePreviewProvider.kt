package com.me.weather.presentation.ui.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.me.weather.domain.model.Favorite

class ListFavoritePreviewProvider : PreviewParameterProvider<List<Favorite>> {
    val base = Favorite(
        id = 1,
        name = "Medellín",
        country = "CO",
        lat = 6.2442,
        lon = -75.5812,
    )

    override val values = sequenceOf(
        emptyList<Favorite>(),
        listOf(base),
        List(4) {
            base.copy(id = base.id + it, name = "${base.name} $it")
        },
        List(15) {
            base.copy(id = base.id + it, name = "${base.name} $it")
        },
    )
}
