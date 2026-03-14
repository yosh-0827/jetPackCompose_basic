package com.example.jetbmicalc

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlin.math.pow
import kotlin.math.roundToInt

/*
 ViewModelの定義をする。
 ・画面の回転などで画面に入力したデータが消えるが、
 　ViewModelをかますことで、そのデータを保持することができる。
 ・Mainではメモリでデータを保存しておくが、大変なので、分離してviewModelでデータを保存する
 ・viewModelを利用することで、データはviewModel,　UIはMainActivityという責任の分離ができる
 */
class MainViewModel : ViewModel() {
    val test = "test" // ログ用に仕込む。

    // mutablestate: reactのstateみたいなやつで、onValueChangedで設定した値を常に監視保持する
    var height by mutableStateOf("")  // 身長のmutablestate
    var weight by mutableStateOf("")  // 体重のmutablestate
    var bmi by mutableStateOf(0f)  // bmiの初期値。floatにしたいのでfをつける

    /*
      BMI計算メソッド
      身長と体重はfloatに変換する
     */
    fun calculateBMI() {
        // 「?:」: Kotlin の Elvis演算子で、意味は「左側が null なら右側を使う」です。
        // 「?.」: 「左側が null なら、そこで処理を止めて null を返す」演算子
        // toFloatOrNull: 数字以外を計算に使った際にExceptionではなくNullを返す
        // div(100): 身長は現在cmだが、BMI計算時にはmが好ましいので100で割る
        // 「: 0f」: nullの場合は0fにする
        var heightNumber = height.toFloatOrNull()?.div(100) ?: 0f
        var weightNumber = weight.toFloatOrNull() ?: 0f

        bmi = if (heightNumber> 0f && weightNumber > 0f) {
            (weightNumber / heightNumber.pow(2) * 10).roundToInt() / 10f
        } else 0f
    }
}