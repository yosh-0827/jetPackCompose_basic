// Top-level build file where you can add configuration options common to all sub-projects/modules.
/*
プロジェクト全体に関わる設定を書く場所です。
例: 共通プラグインのバージョン管理、全モジュール共通の設定、buildscript の classpath など。
通常ここには android {} や implementation(...) は書きません（アプリ本体の設定ではないため）。

 */
buildscript {
    // Root/Appで同じ値を使うため、gradle.propertiesから参照する
    val hilt_version: String by project

    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath("com.google.dagger:hilt-android-gradle-plugin:$hilt_version")
    }
}

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.ksp) apply false  // ルートでプラグインのバージョン管理だけ宣言し、各モジュール（app）で有効化できるようにするため
}
