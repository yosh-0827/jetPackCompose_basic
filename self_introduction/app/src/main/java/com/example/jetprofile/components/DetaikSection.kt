package com.example.jetprofile.components

import Label
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun DetailSection() {
    Column(
        // 背面のグレー枠の見た目設定
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(Color.LightGray.copy(alpha = 0.3f))  // LightGrayが色味強いのでちょっと薄くする
            .padding(horizontal = 10.dp, vertical = 20.dp)  // 枠の太さ
    ) {
        Label(
            icon = Icons.Default.Favorite,
            text = "趣味: 筋トレ",
            color = Color.Gray,
        )
        Spacer(modifier = Modifier.height(10.dp))
        Label(
            icon = Icons.Default.LocationOn,
            text = "居住地: アメリカ合衆国フロリダ州",
            color = Color.Gray,
        )
    }
}