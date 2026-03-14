package com.example.jettodoapp.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jettodoapp.Task

@Composable
fun TaskRow(
    task: Task,
    onClickRow: (Task) -> Unit,
    onClickDelete: (Task) -> Unit,
) {
    Card(
        modifier = Modifier.fillMaxWidth()
            .padding(5.dp),
        // カードをどのくらい浮かせて見せるか
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
    ) {
        // カード
        Row(
            // 各カードの縦横幅
            modifier = Modifier
                .clickable { onClickRow(task) }  // カードをクリックした時
                .padding(10.dp),
            // 中身を真ん中に
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = task.title)
            Spacer(modifier = Modifier.weight(1f))
            IconButton(onClick = { onClickDelete(task) }) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = "削除")
            }

        }
    }
}

@Preview
@Composable
fun TaskRowPreview() {
    TaskRow(
        task = Task(title = "プレビュー", description = ""),
        onClickRow = {},
        onClickDelete = {}
    )
}
