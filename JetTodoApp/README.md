# JetTodoApp メモ

この README は、後で見返して復習するための作業メモです。  
参照：https://developer.android.com/training/data-storage/room?hl=ja#kts

## 0. このアプリで学んだこと

このアプリでは、Jetpack Compose による UI 作成と、`Room` / `Hilt` を使った基本的な Android アプリ構成を学んだ。

### UI 面

- `AlertDialog` を使って、タスクの新規作成・更新用ダイアログを実装した
- `isShowDialog` という `mutableState` で、ダイアログの表示 / 非表示を切り替える流れを学んだ
- `LazyColumn` を使ってタスク一覧を表示し、件数が増えても必要な部分だけ描画される仕組みを確認した
- 画面に収まらない件数になった場合でも、スクロールできるリスト UI を Compose で作れることを学んだ

### Room

`Room` では、主に次の 3 つのコンポーネントを使ってデータベースを構成した。

- `Entity`
  - SQLite のテーブル構造を定義する
  - このアプリでは `id`, `title`, `description` を持つ `Task` テーブルを作成した
- `Dao`
  - CRUD 操作を定義するインターフェース
  - `insert`, `loadAll`, `update`, `delete` をアノテーション付きで定義した
- `RoomDatabase`
  - アプリから DB にアクセスするための入口
  - `TaskDao` をアプリ全体に提供する役割を持つ

また、一覧取得では `Flow<List<Task>>` を使い、DB の内容が変わったら UI 側が追従しやすい構成を学んだ。

### Hilt

- `Hilt` は依存関係注入のために使用した
- `MainViewModel` に `TaskDao` をコンストラクタインジェクションで注入した
- `MainActivity` や Compose 側では `hiltViewModel()` を使って `MainViewModel` を取得した
- 今回は 1 画面構成なので恩恵は小さめだが、画面数が増えるほどボイラープレートを減らせることを学んだ

### まとめ

このアプリを通して、次のような Android 開発の基本を一通り触れた。

- Compose でのダイアログ表示
- `LazyColumn` によるリスト表示
- `Room` によるローカル DB 操作
- `Hilt` による依存関係注入

小規模な Todo アプリではあるものの、実務でもよく出てくる UI とアーキテクチャの基礎を練習できる構成になっている。

## 1. Compose の初期テンプレート（Scaffold）

`MainActivity` は、最小構成の `Scaffold` + `Box` を土台にしている。

ポイント:
- `Scaffold` のラムダ引数 `innerPadding` を受け取る
- 中のコンテンツに `Modifier.padding(innerPadding)` を適用する
- これでシステムバーと重なりにくくなり、`innerPadding` 未使用エラーも防げる

例（要点）:

```kotlin
Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
    ) {
        // ここに画面 UI を追加
    }
}
```

## 2. Room 導入時に実際に行った手順

### 手順 1: KSP プラグインを Version Catalog に追加

対象: `gradle/libs.versions.toml`

- `versions` に KSP バージョンを追加
- `plugins` に KSP を追加

```toml
[versions]
ksp = "2.0.21-1.0.28"

[plugins]
ksp = { id = "com.google.devtools.ksp", version.ref = "ksp" }
```

### 手順 2: ルート build.gradle.kts でプラグインを宣言

対象: `build.gradle.kts`

```kotlin
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.ksp) apply false
}
```

### 手順 3: app モジュールで KSP を有効化

対象: `app/build.gradle.kts`

```kotlin
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)
}
```

### 手順 4: Room 依存を追加

対象: `app/build.gradle.kts`

```kotlin
val roomVersion = "2.8.4"
implementation("androidx.room:room-runtime:$roomVersion")
ksp("androidx.room:room-compiler:$roomVersion")
```

必要に応じて追加:

```kotlin
implementation("androidx.room:room-ktx:$roomVersion")
```

### 手順 5: AGP 9 + Built-in Kotlin 対応

対象: `gradle.properties`

```properties
android.disallowKotlinSourceSets=false
```

補足:
- この設定がないと、KSP 生成ソース関連で次のエラーが出る場合がある。
- `Using kotlin.sourceSets DSL to add Kotlin sources is not allowed with built-in Kotlin.`

## 3. よくある詰まりポイント

- `alias(libs.plugins.ksp)` で `Unresolved reference 'ksp'` が出る
  - `libs.versions.toml` の `[plugins]` に `ksp` 定義がないのが原因
- `id(...)` と `alias(...)` を混ぜる
  - Version Catalog 方式では `alias(...)` で統一する
- `ksp(...)` を使っているのにプラグイン未適用
  - `app/build.gradle.kts` の `plugins` に `alias(libs.plugins.ksp)` が必要

## 4. 確認コマンド

```bash
./gradlew :app:compileDebugKotlin --console=plain
```

Room 導入や Gradle 修正後は、まずこのコマンドでコンパイル確認する。
