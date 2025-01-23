# ショッピングアプリ




## 主な機能

1. アカウント登録/ログイン
2. 購入時の商品属性の選択
3. ショッピングカート
4. 注文した商品のリスト (オーダー )
5. その他: バナー、商品タイプ、提案商品のリスト


## プログラム言語

- Kotlin
- XML
- Java


## IDE

- Android Studio


## データベース

- **Firebase:** Realtime Database, Storage


## ソフトウェアアーキテクチャ
- MVVM (Model-View-ViewModel)


## ダウンロード

- APK: [download](https://github.com/ThanhDanhDo/Shopping-Application/releases/download/untagged-89dfbe4091ed350e09e4/app-debug-16-08-24.apk)
- DriveからのAPK: [drive](https://drive.google.com/file/d/1tEvOU4r3GDPvmhW2ETdUEpzPpq2JByF-/view?usp=drive_link)
  
## ディレクトリ構造

```text
Shopping-Application/
├── .idea/                   # Android StudioのIDE設定ファイル
├── apk/                     # ビルド済みAPKファイル格納用ディレクトリ
├── gradle/                  # Gradleビルドシステムの設定
├── screenshots/             # アプリケーションの動作デモ画像
│
└── app/                     # メインアプリケーションソースコード
    └── src/
        └── main/
            └── java/
                └── com/
                    └── example/
                        └── shopping_application/
                            │
                            ├── Adapter/                     # RecyclerView用アダプタークラス群
                            ├── Helper/                      # 共通ユーティリティ（SharedPreferences操作など）
                            ├── Model/                       # データモデルクラス（カート/注文/ユーザー）
                            ├── ViewModel/                   # MVVMアーキテクチャ用ViewModel
                            │
                            ├── BaseActivity.kt              # Activity基底クラス
                            ├── CartActivity.kt              # ショッピングカート画面
                            ├── DetailActivity.kt            # 商品詳細画面
                            ├── IntroActivity.kt             # 初期起動画面
                            ├── MainActivity.kt              # ホーム画面
                            ├── MoreActivity.kt              # 追加オプション画面
                            ├── OrderActivity.kt             # 注文処理画面
                            ├── SelectProductActivity.kt     # 商品選択画面
                            ├── SignInActivity.kt            # ログイン画面
                            ├── SignUpActivity.kt            # 新規登録画面
                            │
                            └── Entities/                    # コアデータクラス:
                                ├── EachProductOrderData.kt  # 注文内商品詳細
                                ├── OrderData.kt             # 注文情報総合
                                └── UserData.kt              # ユーザー情報
```

## デモ

https://github.com/user-attachments/assets/d9696e76-7f71-4aa6-a1b7-46f1bed56fc4

Google Drive: [link](https://drive.google.com/file/d/1UMVh-sM1LM37z8JFD42mRTqIQVN0LM4_/view?usp=sharing)


## デモ データベース (Firebase)

https://github.com/user-attachments/assets/cf91f8b8-5647-4074-b2bf-c0640c309543

Google Drive: [link](https://drive.google.com/file/d/1Gm5E-iETwyYmPtuIdYc8s14-r_czMaYA/view?usp=sharing)


## スクリーンショット

**イントロ アクティビティ**

<p align="center">
  <img src="https://github.com/ThanhDanhDo/Shopping-Application/blob/8daf60eec25f258df4c41a1e65ebace40c9b88e1/screenshots/IntroAct.png" width="200" />
</p>

**アカウント登録/ログイン アクティビティ**
- サインアップとサインインはRealtime Databaseに投稿され、取得されます
- 間違った入力時のエラーメッセージ

<p align="center">
  <img src="https://github.com/ThanhDanhDo/Shopping-Application/blob/8daf60eec25f258df4c41a1e65ebace40c9b88e1/screenshots/SignUp.png" width="200" />
  <img src="https://github.com/ThanhDanhDo/Shopping-Application/blob/8daf60eec25f258df4c41a1e65ebace40c9b88e1/screenshots/SignIn.png" width="200" />
  <img src="https://github.com/ThanhDanhDo/Shopping-Application/blob/f70cc9aef610a9f59ced5d42385090ad8b31d83d/screenshots/SignUp_entered_incorrectly.png" width="200" />
  <img src="https://github.com/ThanhDanhDo/Shopping-Application/blob/f70cc9aef610a9f59ced5d42385090ad8b31d83d/screenshots/SignIn_entered%20incorrectly.png" width="200" />
</p>

**主なアクティビティ** 
- バナー (ViewPager2)
- 製品タイプ、おすすめ (RecyclerView)
- すべての製品画像はストレージにアップロードされる
- アプリ内で表示されるすべての製品画像はRealtime Database (picURL) から取得される

<p align="center">
  <img src="https://github.com/ThanhDanhDo/Shopping-Application/blob/8daf60eec25f258df4c41a1e65ebace40c9b88e1/screenshots/MainAct.png" width="200" />
</p>

**詳細アクティビティ**

<p align="center">
  <img src="https://github.com/ThanhDanhDo/Shopping-Application/blob/dcee6fbbe2dd1b6492c8b043da97c3e146d79ecf/screenshots/DetailAct.png" width="200" />
</p>

**製品を選択するアクティビティ**
- 製品リスト、サイズ  (RecyclerView)

<p align="center">
  <img src="https://github.com/ThanhDanhDo/Shopping-Application/blob/dcee6fbbe2dd1b6492c8b043da97c3e146d79ecf/screenshots/SelectProductAct.png" width="200" />
</p>

**カート アクティビティ**
- 合計金額の計算
- プラスボタン、マイナスボタンで数量を調整する
- 支払い情報およびすべての製品はRealtime Databaseの注文データに保存される

<p align="center">
  <img src="https://github.com/ThanhDanhDo/Shopping-Application/blob/dcee6fbbe2dd1b6492c8b043da97c3e146d79ecf/screenshots/CartAct.png" width="200" />
  <img src="https://github.com/ThanhDanhDo/Shopping-Application/blob/dcee6fbbe2dd1b6492c8b043da97c3e146d79ecf/screenshots/EmptyCartAct.png" width="200" />
</p>

**私の注文アクティビティ**
- 注文の最初の製品を表示する

<p align="center">
  <img src="https://github.com/ThanhDanhDo/Shopping-Application/blob/f70cc9aef610a9f59ced5d42385090ad8b31d83d/screenshots/MyOrderAct.png" width="200" />
  <img src="https://github.com/ThanhDanhDo/Shopping-Application/blob/f70cc9aef610a9f59ced5d42385090ad8b31d83d/screenshots/EmptyMyOrder.png" width="200" />
</p>

**モア アクティビティ (More Activity)**

<p align="center">
  <img src="https://github.com/ThanhDanhDo/Shopping-Application/blob/f70cc9aef610a9f59ced5d42385090ad8b31d83d/screenshots/MoreAct.png" width="200" />
</p>

**Realtime Database, Storage**

<p align="center">
  <img src="https://github.com/ThanhDanhDo/Shopping-Application/blob/041939bc579b5fec239eb06cb238738e506d3737/screenshots/database.png" height="300" />
  <img src="https://github.com/ThanhDanhDo/Shopping-Application/blob/041939bc579b5fec239eb06cb238738e506d3737/screenshots/database_user_order.png" height="300" />
  <img src="https://github.com/ThanhDanhDo/Shopping-Application/blob/041939bc579b5fec239eb06cb238738e506d3737/screenshots/storage.png" height="300" />
</p>

## 🔗 参照リンク

[UiLover Android](https://www.youtube.com/watch?v=jh1GXnFw7rM&t=7919s)
[Android Knowledge](https://www.youtube.com/watch?v=MhLkezKsHbY&list=PLNVJlM5Ce3JziULawRZ3IWlvbbZLcHnHV&index=5)
[Gà Lại Lập Trình](https://www.youtube.com/playlist?list=PLPt6-BtUI22qf3KE1V1PyAm1v8M2qqwL5)
