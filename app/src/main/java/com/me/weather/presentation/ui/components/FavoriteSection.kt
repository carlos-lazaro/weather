package com.me.weather.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.me.weather.R
import com.me.weather.domain.model.Favorite
import com.me.weather.presentation.ui.preview.ThemePreview
import com.me.weather.presentation.ui.theme.WeatherTheme

@Composable
fun FavoriteSection(
    modifier: Modifier = Modifier,
    favorites: List<Favorite> = emptyList(),
    onDeleteFavorite: (Favorite) -> Unit = {},
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background,
        ),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        modifier = modifier
            .fillMaxWidth()
            .heightIn(max = 400.dp)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            stickyHeader {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.background)
                ) {
                    Text(
                        stringResource(R.string.favorites).replaceFirstChar { it.uppercase() },
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.SemiBold,
                    )

                    HorizontalDivider()
                }
            }

            favorites
                .takeIf { it.isNotEmpty() }
                ?.let { favorites ->
                    itemsIndexed(favorites, key = { _, item -> item.id }) { index, item ->
                        Item(
                            item = item,
                            lastIndex = favorites.lastIndex,
                            index = index,
                            onDeleteFavorite = {
                                onDeleteFavorite(item)
                            },
                        )
                    }
                } ?: item { EmptyState() }
        }
    }
}

@Composable
private fun EmptyState(modifier: Modifier = Modifier) {
    Text(
        stringResource(R.string.empty_state_favorites_cities),
        style = MaterialTheme.typography.bodyMedium,
        textAlign = TextAlign.Center,
        modifier = modifier
            .padding(vertical = 12.dp)
            .alpha(0.5f)
    )
}

@Composable
private fun Item(
    item: Favorite,
    lastIndex: Int,
    index: Int,
    onDeleteFavorite: () -> Unit = {},
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .align(Alignment.CenterStart)
        ) {
            Icon(
                Icons.Default.Star,
                stringResource(R.string.start),
                tint = MaterialTheme.colorScheme.inversePrimary,
                modifier = Modifier
                    .alpha(0.5f)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = buildAnnotatedString {
                    append(item.name)
                    withStyle(
                        style = SpanStyle(
                            fontWeight = FontWeight.Normal,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            fontSize = MaterialTheme.typography.labelSmall.fontSize
                        )
                    ) {
                        append(" (${item.country})")
                    }
                },
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
            )
        }

        IconButton(
            onClick = {
                onDeleteFavorite()
            },
            modifier = Modifier
                .align(Alignment.CenterEnd)
        ) {
            Icon(
                Icons.Default.DeleteForever,
                stringResource(R.string.delete_favorite),
            )
        }
    }

    if (index < lastIndex) {
        HorizontalDivider(modifier = Modifier.alpha(0.4f))
    }
}

@ThemePreview
@Composable
private fun Preview() {
    WeatherTheme {
        Surface {
            FavoriteSection(
                favorites = listOf(),
            )
        }
    }
}
