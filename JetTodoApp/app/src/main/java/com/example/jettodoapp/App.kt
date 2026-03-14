package com.example.jettodoapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp // Hiltの起動ポイント。アプリ全体で使うDIコンテナを作るために必要
class App : Application()