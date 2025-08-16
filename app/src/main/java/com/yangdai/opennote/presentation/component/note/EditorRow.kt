package com.yangdai.opennote.presentation.component.note

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.FormatIndentDecrease
import androidx.compose.material.icons.automirrored.outlined.FormatIndentIncrease
import androidx.compose.material.icons.automirrored.outlined.Label
import androidx.compose.material.icons.automirrored.outlined.List
import androidx.compose.material.icons.automirrored.outlined.TextSnippet
import androidx.compose.material.icons.outlined.AddChart
import androidx.compose.material.icons.outlined.CheckBox
import androidx.compose.material.icons.outlined.Code
import androidx.compose.material.icons.outlined.DataArray
import androidx.compose.material.icons.outlined.DataObject
import androidx.compose.material.icons.outlined.Feedback
import androidx.compose.material.icons.outlined.FormatBold
import androidx.compose.material.icons.outlined.FormatItalic
import androidx.compose.material.icons.outlined.FormatPaint
import androidx.compose.material.icons.outlined.FormatQuote
import androidx.compose.material.icons.outlined.FormatUnderlined
import androidx.compose.material.icons.outlined.HorizontalRule
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Lightbulb
import androidx.compose.material.icons.outlined.Link
import androidx.compose.material.icons.outlined.Mic
import androidx.compose.material.icons.outlined.ReportGmailerrorred
import androidx.compose.material.icons.outlined.StrikethroughS
import androidx.compose.material.icons.outlined.TableChart
import androidx.compose.material.icons.outlined.Title
import androidx.compose.material.icons.outlined.VideoFile
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PlainTooltip
import androidx.compose.material3.Text
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.rememberTooltipState
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yangdai.opennote.R
import androidx.window.core.layout.WindowWidthSizeClass
import com.yangdai.opennote.presentation.util.Constants

@Preview(showBackground = true)
@Composable
fun RichTextEditorRowPreview() {
    RichTextEditorRow(
        onTabIClick = {},
        onTabDClick = {},
        onHeaderClick = {},
        onBoldClick = {},
        onItalicClick = {},
        onUnderlineClick = {},
        onStrikeThroughClick = {},
        onHighlightClick = {},
        onCodeClick = {},
        onBracketsClick = {},
        onBracesClick = {},
        onTemplateClick = {}
    )
}

@Preview(showBackground = true)
@Composable
fun MarkdownEditorRowPreview() {
    MarkdownEditorRow(
        canUndo = true,
        canRedo = true,
        onEdit = {},
        onTableButtonClick = {},
        onListButtonClick = {},
        onTaskButtonClick = {},
        onLinkButtonClick = {},
        onImageButtonClick = {},
        onAudioButtonClick = {},
        onVideoButtonClick = {},
        onTemplateClick = {}
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IconButtonWithTooltip(
    enabled: Boolean = true,
    imageVector: ImageVector? = null,
    painter: Int? = null,
    tint: Color = LocalContentColor.current,
    contentDescription: String,
    shortCutDescription: String? = null,
    onClick: () -> Unit
) {
    // Determine whether to show shortcut (tablets / larger width) or hide on phones (compact width)
    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
    val showShortcut = windowSizeClass.windowWidthSizeClass != WindowWidthSizeClass.COMPACT
    val tooltipText = if (shortCutDescription != null && showShortcut) {
        // Localized pattern with parentheses style handled per locale
        stringResource(
            id = R.string.tooltip_with_shortcut,
            contentDescription,
            shortCutDescription
        )
    } else contentDescription

    TooltipBox(
        positionProvider = TooltipDefaults.rememberTooltipPositionProvider(),
        tooltip = {
            PlainTooltip(
                content = { Text(tooltipText) }
            )
        },
        state = rememberTooltipState(),
        focusable = false,
        enableUserInput = true
    ) {
        IconButton(
            onClick = onClick,
            enabled = enabled,
            colors = IconButtonDefaults.iconButtonColors().copy(contentColor = tint)
        ) {
            if (imageVector != null) {
                Icon(
                    imageVector = imageVector,
                    contentDescription = contentDescription
                )
            } else {
                Icon(
                    painter = painterResource(id = painter!!),
                    contentDescription = contentDescription
                )
            }
        }
    }
}

@Composable
fun RichTextEditorRow(
    onTabIClick: () -> Unit,
    onTabDClick: () -> Unit,
    onHeaderClick: (Int) -> Unit,
    onBoldClick: () -> Unit,
    onItalicClick: () -> Unit,
    onUnderlineClick: () -> Unit,
    onStrikeThroughClick: () -> Unit,
    onHighlightClick: () -> Unit,
    onCodeClick: () -> Unit,
    onBracketsClick: () -> Unit,
    onBracesClick: () -> Unit,
    onTemplateClick: () -> Unit
) {

    var isExpanded by rememberSaveable { mutableStateOf(false) }

    Column {

        EditorDivider(
            Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.surfaceVariant,
            thickness = 4.dp
        )

        Row(
            Modifier
                .height(40.dp)
                .horizontalScroll(rememberScrollState()),
            verticalAlignment = Alignment.CenterVertically
        ) {

            IconButtonWithTooltip(
                imageVector = Icons.Outlined.Title,
                contentDescription = "Heading Level"
            ) {
                isExpanded = !isExpanded
            }

            AnimatedVisibility(visible = isExpanded) {
                Row(
                    modifier = Modifier.fillMaxHeight(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {

                    IconButtonWithTooltip(
                        painter = R.drawable.format_h1,
                        contentDescription = "H1",
                        shortCutDescription = "Ctrl + 1",
                        onClick = { onHeaderClick(1) }
                    )

                    IconButtonWithTooltip(
                        painter = R.drawable.format_h2,
                        contentDescription = "H2",
                        shortCutDescription = "Ctrl + 2",
                        onClick = { onHeaderClick(2) }
                    )

                    IconButtonWithTooltip(
                        painter = R.drawable.format_h3,
                        contentDescription = "H3",
                        shortCutDescription = "Ctrl + 3",
                        onClick = { onHeaderClick(3) }
                    )

                    IconButtonWithTooltip(
                        painter = R.drawable.format_h4,
                        contentDescription = "H4",
                        shortCutDescription = "Ctrl + 4",
                        onClick = { onHeaderClick(4) }
                    )

                    IconButtonWithTooltip(
                        painter = R.drawable.format_h5,
                        contentDescription = "H5",
                        shortCutDescription = "Ctrl + 5",
                        onClick = { onHeaderClick(5) }
                    )

                    IconButtonWithTooltip(
                        painter = R.drawable.format_h6,
                        contentDescription = "H6",
                        shortCutDescription = "Ctrl + 6",
                        onClick = { onHeaderClick(6) }
                    )
                }
            }

            IconButtonWithTooltip(
                imageVector = Icons.Outlined.FormatBold,
                contentDescription = stringResource(id = R.string.bold),
                shortCutDescription = "Ctrl + B",
                onClick = onBoldClick
            )
            IconButtonWithTooltip(
                imageVector = Icons.Outlined.FormatItalic,
                contentDescription = stringResource(id = R.string.italic),
                shortCutDescription = "Ctrl + I",
                onClick = onItalicClick
            )

            IconButtonWithTooltip(
                imageVector = Icons.Outlined.FormatUnderlined,
                contentDescription = stringResource(id = R.string.underline),
                shortCutDescription = "Ctrl + U",
                onClick = onUnderlineClick
            )

            IconButtonWithTooltip(
                imageVector = Icons.Outlined.StrikethroughS,
                contentDescription = stringResource(id = R.string.strikethrough),
                shortCutDescription = "Ctrl + D",
                onClick = onStrikeThroughClick
            )

            IconButtonWithTooltip(
                imageVector = Icons.Outlined.FormatPaint,
                contentDescription = stringResource(id = R.string.mark),
                shortCutDescription = "Ctrl + M",
                onClick = onHighlightClick
            )

            IconButtonWithTooltip(
                imageVector = Icons.Outlined.Code,
                contentDescription = stringResource(id = R.string.code),
                shortCutDescription = "Ctrl + Shift + K",
                onClick = onCodeClick
            )

            IconButtonWithTooltip(
                imageVector = Icons.Outlined.DataArray,
                contentDescription = "Brackets",
                onClick = onBracketsClick
            )

            IconButtonWithTooltip(
                imageVector = Icons.Outlined.DataObject,
                contentDescription = "Braces",
                onClick = onBracesClick
            )

            IconButtonWithTooltip(
                imageVector = Icons.AutoMirrored.Outlined.FormatIndentIncrease,
                contentDescription = "Tab+",
                onClick = onTabIClick
            )

            IconButtonWithTooltip(
                imageVector = Icons.AutoMirrored.Outlined.FormatIndentDecrease,
                contentDescription = "Tab-",
                onClick = onTabDClick
            )

            IconButtonWithTooltip(
                imageVector = Icons.AutoMirrored.Outlined.TextSnippet,
                contentDescription = stringResource(id = R.string.templates),
                shortCutDescription = "Ctrl + Shift + P",
                onClick = onTemplateClick
            )
        }
    }
}

@Composable
fun MarkdownEditorRow(
    canUndo: Boolean,
    canRedo: Boolean,
    onEdit: (String) -> Unit,
    onTableButtonClick: () -> Unit,
    onListButtonClick: () -> Unit,
    onTaskButtonClick: () -> Unit,
    onLinkButtonClick: () -> Unit,
    onImageButtonClick: () -> Unit,
    onAudioButtonClick: () -> Unit,
    onVideoButtonClick: () -> Unit,
    onTemplateClick: () -> Unit
) {

    var isExpanded by rememberSaveable { mutableStateOf(false) }
    var isAlertExpanded by rememberSaveable { mutableStateOf(false) }


    Row(
        Modifier
            .fillMaxWidth()
            .background(BottomAppBarDefaults.containerColor)
            .navigationBarsPadding()
            .height(48.dp)
            .horizontalScroll(rememberScrollState()),
        verticalAlignment = Alignment.CenterVertically
    ) {

        IconButtonWithTooltip(
            enabled = canUndo,
            painter = R.drawable.undo,
            contentDescription = stringResource(id = R.string.undo),
            shortCutDescription = "Ctrl + Z"
        ) {
            onEdit(Constants.Editor.UNDO)
        }

        IconButtonWithTooltip(
            enabled = canRedo,
            painter = R.drawable.redo,
            contentDescription = stringResource(id = R.string.redo),
            shortCutDescription = "Ctrl + Y"
        ) {
            onEdit(Constants.Editor.REDO)
        }

        IconButtonWithTooltip(
            imageVector = Icons.Outlined.Title,
            contentDescription = "Heading Level"
        ) {
            isExpanded = !isExpanded
        }

        AnimatedVisibility(visible = isExpanded) {
            Row(
                modifier = Modifier.fillMaxHeight(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {

                IconButtonWithTooltip(
                    painter = R.drawable.format_h1,
                    contentDescription = "H1",
                    shortCutDescription = "Ctrl + 1"
                ) {
                    onEdit(Constants.Editor.H1)
                }

                IconButtonWithTooltip(
                    painter = R.drawable.format_h2,
                    contentDescription = "H2",
                    shortCutDescription = "Ctrl + 2"
                ) {
                    onEdit(Constants.Editor.H2)
                }

                IconButtonWithTooltip(
                    painter = R.drawable.format_h3,
                    contentDescription = "H3",
                    shortCutDescription = "Ctrl + 3"
                ) {
                    onEdit(Constants.Editor.H3)
                }

                IconButtonWithTooltip(
                    painter = R.drawable.format_h4,
                    contentDescription = "H4",
                    shortCutDescription = "Ctrl + 4"
                ) {
                    onEdit(Constants.Editor.H4)
                }

                IconButtonWithTooltip(
                    painter = R.drawable.format_h5,
                    contentDescription = "H5",
                    shortCutDescription = "Ctrl + 5"
                ) {
                    onEdit(Constants.Editor.H5)
                }

                IconButtonWithTooltip(
                    painter = R.drawable.format_h6,
                    contentDescription = "H6",
                    shortCutDescription = "Ctrl + 6"
                ) {
                    onEdit(Constants.Editor.H6)
                }
            }
        }

        IconButtonWithTooltip(
            imageVector = Icons.Outlined.FormatBold,
            contentDescription = stringResource(id = R.string.bold),
            shortCutDescription = "Ctrl + B"
        ) {
            onEdit(Constants.Editor.BOLD)
        }

        IconButtonWithTooltip(
            imageVector = Icons.Outlined.FormatItalic,
            contentDescription = stringResource(id = R.string.italic),
            shortCutDescription = "Ctrl + I"
        ) {
            onEdit(Constants.Editor.ITALIC)
        }

        IconButtonWithTooltip(
            imageVector = Icons.Outlined.FormatUnderlined,
            contentDescription = stringResource(id = R.string.underline),
            shortCutDescription = "Ctrl + U"
        ) {
            onEdit(Constants.Editor.UNDERLINE)
        }

        IconButtonWithTooltip(
            imageVector = Icons.Outlined.StrikethroughS,
            contentDescription = stringResource(id = R.string.strikethrough),
            shortCutDescription = "Ctrl + D"
        ) {
            onEdit(Constants.Editor.STRIKETHROUGH)
        }

        IconButtonWithTooltip(
            imageVector = Icons.Outlined.FormatPaint,
            contentDescription = stringResource(id = R.string.mark),
            shortCutDescription = "Ctrl + M"
        ) {
            onEdit(Constants.Editor.MARK)
        }

        IconButtonWithTooltip(
            imageVector = Icons.Outlined.Code,
            contentDescription = stringResource(id = R.string.code),
            shortCutDescription = "Ctrl + Shift + K"
        ) {
            onEdit(Constants.Editor.INLINE_CODE)
        }

        IconButtonWithTooltip(
            imageVector = Icons.Outlined.DataArray,
            contentDescription = "Brackets"
        ) {
            onEdit(Constants.Editor.INLINE_BRACKETS)
        }

        IconButtonWithTooltip(
            imageVector = Icons.Outlined.DataObject,
            contentDescription = "Braces"
        ) {
            onEdit(Constants.Editor.INLINE_BRACES)
        }

        IconButtonWithTooltip(
            imageVector = Icons.AutoMirrored.Outlined.FormatIndentIncrease,
            contentDescription = "Tab"
        ) {
            onEdit(Constants.Editor.TAB)
        }

        IconButtonWithTooltip(
            imageVector = Icons.AutoMirrored.Outlined.FormatIndentDecrease,
            contentDescription = "unTab"
        ) {
            onEdit(Constants.Editor.UN_TAB)
        }

        IconButtonWithTooltip(
            painter = R.drawable.function,
            contentDescription = stringResource(id = R.string.math),
            shortCutDescription = "Ctrl + Shift + M"
        ) {
            onEdit(Constants.Editor.INLINE_MATH)
        }

        IconButtonWithTooltip(
            imageVector = Icons.Outlined.FormatQuote,
            contentDescription = stringResource(id = R.string.quote),
            shortCutDescription = "Ctrl + Shift + Q"
        ) {
            onEdit(Constants.Editor.QUOTE)
        }

        IconButtonWithTooltip(
            imageVector = Icons.AutoMirrored.Outlined.Label,
            contentDescription = stringResource(id = R.string.alert),
        ) {
            isAlertExpanded = !isAlertExpanded
        }

        AnimatedVisibility(visible = isAlertExpanded) {
            Row(
                modifier = Modifier.fillMaxHeight(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                IconButtonWithTooltip(
                    imageVector = Icons.Outlined.Info,
                    contentDescription = stringResource(R.string.note_alert),
                ) {
                    onEdit(Constants.Editor.NOTE)
                }

                IconButtonWithTooltip(
                    imageVector = Icons.Outlined.Lightbulb,
                    contentDescription = stringResource(R.string.tip_alert),
                ) {
                    onEdit(Constants.Editor.TIP)
                }

                IconButtonWithTooltip(
                    imageVector = Icons.Outlined.Feedback,
                    contentDescription = stringResource(R.string.important_alert),
                ) {
                    onEdit(Constants.Editor.IMPORTANT)

                }
                IconButtonWithTooltip(
                    imageVector = Icons.Outlined.Warning,
                    contentDescription = stringResource(R.string.warning_alert),
                ) {
                    onEdit(Constants.Editor.WARNING)
                }

                IconButtonWithTooltip(
                    imageVector = Icons.Outlined.ReportGmailerrorred,
                    contentDescription = stringResource(R.string.caution_alert),
                ) {
                    onEdit(Constants.Editor.CAUTION)

                }
            }
        }

        IconButtonWithTooltip(
            imageVector = Icons.Outlined.HorizontalRule,
            contentDescription = stringResource(id = R.string.horizontal_rule),
            shortCutDescription = "Ctrl + Shift + R"
        ) {
            onEdit(Constants.Editor.RULE)
        }

        IconButtonWithTooltip(
            imageVector = Icons.Outlined.TableChart,
            contentDescription = stringResource(id = R.string.table),
            shortCutDescription = "Ctrl + T",
            onClick = onTableButtonClick
        )

        IconButtonWithTooltip(
            imageVector = Icons.Outlined.AddChart,
            contentDescription = stringResource(id = R.string.mermaid_diagram),
            shortCutDescription = "Ctrl + Shift + D"
        ) {
            onEdit(Constants.Editor.DIAGRAM)
        }

        IconButtonWithTooltip(
            imageVector = Icons.AutoMirrored.Outlined.List,
            contentDescription = stringResource(id = R.string.list),
            shortCutDescription = "Ctrl + Shift + L",
            onClick = onListButtonClick
        )

        IconButtonWithTooltip(
            imageVector = Icons.Outlined.CheckBox,
            contentDescription = stringResource(id = R.string.task_list),
            shortCutDescription = "Ctrl + Shift + T",
            onClick = onTaskButtonClick
        )

        IconButtonWithTooltip(
            imageVector = Icons.Outlined.Link,
            contentDescription = stringResource(id = R.string.link),
            shortCutDescription = "Ctrl + K",
            onClick = onLinkButtonClick
        )

        IconButtonWithTooltip(
            imageVector = Icons.Outlined.Mic,
            contentDescription = stringResource(id = R.string.audio),
            shortCutDescription = "Ctrl + Shift + A",
            onClick = onAudioButtonClick
        )

        IconButtonWithTooltip(
            imageVector = Icons.Outlined.VideoFile,
            contentDescription = stringResource(id = R.string.video),
            shortCutDescription = "Ctrl + Shift + V",
            onClick = onVideoButtonClick
        )

        IconButtonWithTooltip(
            imageVector = Icons.Outlined.Image,
            contentDescription = stringResource(id = R.string.image),
            shortCutDescription = "Ctrl + Shift + I",
            onClick = onImageButtonClick
        )

        IconButtonWithTooltip(
            imageVector = Icons.AutoMirrored.Outlined.TextSnippet,
            contentDescription = stringResource(id = R.string.templates),
            shortCutDescription = "Ctrl + Shift + P",
            onClick = onTemplateClick
        )
    }
}
