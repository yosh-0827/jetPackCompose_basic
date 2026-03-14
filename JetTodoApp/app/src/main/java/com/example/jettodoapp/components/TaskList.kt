package com.example.jettodoapp.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import com.example.jettodoapp.Task

@Composable
fun TaskList(
    tasks: List<Task>,
    onClickRow: (Task) -> Unit,
    onClickDelete: (Task) -> Unit,
) {
    // LazyColumn: Columnだと100件同時にリスト表示する際に重くてパフォーマンスが悪いので、その対策。
    LazyColumn {
        // task1件につきTaskRow()を実行する
        items(tasks) { task ->
            TaskRow(
                task = task,
                onClickRow = onClickRow,
                onClickDelete = onClickDelete,
            )
        }
    }
}