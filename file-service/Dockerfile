FROM amazoncorretto:21

WORKDIR /app

# ソースコードをイメージ内にコピー
COPY file-service/src ./file-service/src
COPY shared/src ./shared/src
# シェルスクリプをイメージ内にコピー
COPY file-service/entrypoint.sh ./spring-entrypoint.sh
COPY ../build.gradle ./
COPY ../gradlew ./
COPY ../gradle ./gradle/
COPY ../settings.gradle ./
# 実行権限を付与
RUN chmod +x ./spring-entrypoint.sh
# gradlewに実行権限を追加
RUN chmod +x ./gradlew

# コンテナを起動する際に実行されるコマンド
ENTRYPOINT ["/app/spring-entrypoint.sh"]