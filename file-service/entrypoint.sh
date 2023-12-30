#!/bin/bash
# entrypoint.sh

# フラグファイルのパスを指定
FLAG_FILE="/app/file-service/build/jooq-codegen-completed"

# jOOQのコード生成がまだ行われていないか確認
if [ ! -f "$FLAG_FILE" ]; then
  echo "Generating jOOQ code for file-service..."
  ./gradlew :file-service:generateJooq

  echo "Creating flag file: $FLAG_FILE"
  touch "$FLAG_FILE"
else
  echo "jOOQ code generation for file-service is not needed."
fi

# file-service アプリケーションの起動
./gradlew :file-service:bootRun -x generateJooq
