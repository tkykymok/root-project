init:
	docker compose up --build -d

destroy:
	docker compose down --rmi all --volumes --remove-orphans

remake:
	@make destroy
	@make init

gen-jooq:
	docker exec -it spring-demo /bin/sh -c "./gradlew generateJooq"

create-queue:
	aws --endpoint-url=http://localhost:4566 sqs create-queue --queue-name test-queue.fifo --attributes FifoQueue=true

# aws sqs list-queues --endpoint-url=http://localhost:4566
# aws sqs receive-message --queue-url 'http://sqs.ap-northeast-1.localhost.localstack.cloud:4566/000000000000/test-queue' --endpoint-url http://localhost:4566

#  docker exec -it mssql-demo /bin/sh
#  docker exec -it spring-demo /bin/sh
