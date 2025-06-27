# Java 17ベースの公式イメージを使用（21でも可）
FROM eclipse-temurin:21-jdk

# 作業ディレクトリを作成
WORKDIR /app

# ビルド済みJARファイルをコピー（名前はjarのファイル名に合わせて）
COPY build/libs/consult_ensyu-0.0.1-SNAPSHOT.jar app.jar

# アプリを実行
CMD ["java", "-jar", "app.jar"]