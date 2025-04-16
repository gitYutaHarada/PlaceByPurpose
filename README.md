# 目的から地物検索ができるWebアプリケーション

## 概要（Summary）  
Google Mapでは「ギターの練習」などと検索しても、音楽スタジオやカラオケなどの「実際に練習できる場所」が十分に表示されません。  
本プロジェクトは、ユーザーが入力した目的に基づいて、目的を達成できる場所をGoogle Maps上に表示するアプリケーションです。

## 制作背景 / 目的（Background / Purpose）  
このプロジェクトは、大学の研究室で「目的から地物検索」に関する論文に触発されたことがきっかけです。  
また、GPT APIを利用した新たなアプリ開発への挑戦として、このプロジェクトに取り組むことにしました。

## 主な機能（Features）  
- **目的と徒歩時間入力**: ユーザーが目的と徒歩何分以内かを入力  
- **キーワードの生成**: GPT APIにより目的に応じたキーワードを自動生成  
- **地図表示**: 生成されたキーワードに基づき、Google Maps上に該当する場所をピンで表示

## 使用技術（Tech Stack）  
- **フロントエンド**:  
  - HTML, CSS, JavaScript  
  - Thymeleaf
- **バックエンド**:  
  - Java (Spring Boot)  
  - jackson.databind.JsonNode
- **API**:  
  - GPT API  
  - Google Place API  
  - Google Maps JavaScript API

## 工夫した点 / 技術的チャレンジ（Highlights）

### 技術的チャレンジ  
- 初めて外部API（GPT APIとGoogle API）を組み合わせたWebアプリケーションの実装

### 工夫した点  

#### 単語の拡張処理  
- 「ギターの練習」といった入力に対し、「ウクレレの練習」など、意味の近い語をGPT APIで拡張し、検索範囲を広げる工夫を実施。

#### GPT APIの活用方法  
- 出力フォーマットを「目的 → 行動（複数）→ 条件（複数）→ 場所（複数）」に設定し、JSON形式でキーワードを抽出。  
- プロンプト設計で具体例（例：「コンビニ」→「セブンイレブン」「ローソン」など）を追加。  
- 出力を英語で統一し、Google Place APIとの連携を円滑に実施。

#### Google Place API の工夫  
- **Text Search API** を採用し、柔軟な検索を実現。  
- 曖昧な「コンビニ」検索の問題を、具体的な店舗名を用いた出力で解決。

## 苦労した点とその解決方法  
- **課題**: Google Place APIで「コンビニ」単体の検索結果が得られない（「コンビニエンスストア」の略称で認識されにくい）。
- **解決**: GPT APIで「セブンイレブン」「ローソン」などの具体的店舗名を出力し、正確な検索結果を実現。

## 今後の改善点 / 拡張予定（Future Work）  
- ユーザーが出発地点を選択できるようにする（現状は淵野辺駅固定）。  
- ログイン機能を追加し、ユーザーの検索履歴を保存・再利用できるようにする。  
- 徒歩時間から単純な距離計算（10分×80m）ではなく、実際の道順を考慮したナビゲーション機能の実装。

## リンク（GitHub / デモ）  
(https://place-by-purpose-d2f9f7d7519a.herokuapp.com/)
