package com.me.weather.presentation.ui.components

import androidx.compose.material3.AssistChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.me.weather.domain.model.Record
import com.me.weather.presentation.ui.preview.SingleRecordPreviewProvider
import com.me.weather.presentation.ui.preview.ThemePreview
import com.me.weather.presentation.ui.theme.WeatherTheme

@Composable
fun ChipElement(
    modifier: Modifier = Modifier,
    record: Record,
    onClick: (Record) -> Unit = {},
) {
    AssistChip(
        modifier = modifier,
        onClick = { onClick(record) },
        label = {
            Text(
                text = buildAnnotatedString {
                    append(record.name)
                    withStyle(
                        style = SpanStyle(
                            fontWeight = FontWeight.Normal,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            fontSize = MaterialTheme.typography.labelMedium.fontSize,
                        )
                    ) {
                        append(" (${record.country})")
                    }
                },
                style = MaterialTheme.typography.labelSmall,
            )
        },
    )
}

@ThemePreview
@Composable
private fun Preview(
    @PreviewParameter(SingleRecordPreviewProvider::class) record: Record,
) {
    WeatherTheme {
        Surface {
            ChipElement(record = record)
        }
    }
}
