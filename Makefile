.PHONY: all help install build run

help: ## Display this help
	@awk 'BEGIN {FS = ":.*##"; printf "\nUsage:\n  make \033[36m<target>\033[0m\n"} /^[a-zA-Z_0-9-\\.]+:.*?##/ { printf "  \033[36m%-15s\033[0m %s\n", $$1, $$2 } /^##@/ { printf "\n\033[1m%s\033[0m\n", substr($$0, 5) } ' $(MAKEFILE_LIST)

all: help

.PHONY: build
build: ## Build a Docker image
	$(info Building Docker image...)
	docker build --rm --pull --tag products:1.0 . 

install: ## Install Java dependencies
	$(info Installing dependencies...)
	./mvnw dependency:resolve

lint: ## Run the linter
	$(info Running linting...)
	./mvnw checkstyle:check

.PHONY: tests
tests: ## Run the unit tests
	$(info Running tests...)
	./mvnw test

.PHONY: integration-tests
integration-tests: ## Run integration tests
	$(info Running integration tests...)
	./mvnw verify -P integration-test

run: ## Run the service
	$(info Starting service...)
	./mvnw spring-boot:run

dbrm: ## Stop and remove PostgreSQL in Docker
	$(info Stopping and removing PostgreSQL...)
	-docker stop postgres
	-docker rm postgres

db: ## Run PostgreSQL in Docker
	$(info Running PostgreSQL...)
	docker run -d --name postgres \
		-p 5432:5432 \
		-e POSTGRES_PASSWORD=postgres \
		-v postgres:/var/lib/postgresql/data \
		postgres:alpine

clean: ## Clean the project
	$(info Cleaning the project...)
	./mvnw clean

package: ## Package the application
	$(info Packaging the application...)
	./mvnw package

cucumber: ## Run Cucumber BDD tests
	$(info Running Cucumber tests...)
	./mvnw test -Dtest=CucumberTestRunner