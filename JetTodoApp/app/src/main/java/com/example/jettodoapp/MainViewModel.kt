package com.example.jettodoapp

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch


/**
 * ViewModel
 * viewModelなので「viewModel」を継承
 *
 */
@HiltViewModel // HiltをViewModelで使うときに必要らしい
// @Inject constructor(private val taskDao: TaskDao) : hiltからtaskDaoのインスタンスを注入できる
class MainViewModel @Inject constructor(private val taskDao: TaskDao): ViewModel() {
    // テキストフィールドを管理するmutable state
    var title by mutableStateOf("")
    var description by mutableStateOf("")
    var isShowDialog by mutableStateOf(false)
    // DBから全件取得する。ただし、extentionのdistinctUntilChangedのおかげで中身が変わってなければ走らない。(無駄なデータを取らない)
    val tasks = taskDao.loadAllTasks().distinctUntilChanged()

    // 更新に使用する変数
    private var editingTask: Task? = null
    val isEditing: Boolean  // 編集中か
        get() = editingTask != null

    // seter
    // dialogを押したときに元々のカードの情報を反映させる
    fun setEditingTask(task: Task) {
        editingTask = task
        title = task.title
        description = task.description
    }

    // DBにデータを保存する
    fun createTask() {
        // insertTaskがsuspend(非同期ワンショット)なのでviewModelScope.launchの中で実行させる
        viewModelScope.launch {
            val newTask = Task(title = title, description = description)
            taskDao.insertTask(newTask)
            // MainViewModel::class.simpleName は、MainViewModel クラスの「クラス名だけ」を文字列で取る式
            Log.d(MainViewModel::class.simpleName, "success create task")
        }
    }

    // update
    fun updateTask() {
        // editingTaskの中身がnullなら{}が実行されない
        editingTask?.let { task ->
            val updatedTitle = title
            val updatedDescription = description
            viewModelScope.launch {
                val updatedTask = task.copy(
                    title = updatedTitle,
                    description = updatedDescription,
                )
                taskDao.updateTask(updatedTask)
            }
        }
    }

    //delete
    fun deleteTask(task: Task) {
        viewModelScope.launch {
            taskDao.deleteTask(task)
        }
    }

    // editDialogが非表示になる段階で呼ぶ関数
    fun resetProperties() {
        editingTask = null
        title = ""
        description = ""
    }
}
