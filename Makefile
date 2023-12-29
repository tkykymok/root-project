init:
	docker compose up --build -d

destroy:
	docker compose down --rmi all --volumes --remove-orphans

remake:
	@make destroy
	@make init

gen-jooq:
	docker exec -it spring-demo /bin/sh -c "./gradlew generateJooq"

#  docker exec -it mssql-demo /bin/sh
#  docker exec -it spring-demo /bin/sh
