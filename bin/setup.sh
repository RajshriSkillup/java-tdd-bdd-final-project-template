#!/bin/bash
echo "**************************************************"
echo " Setting up TDD/BDD Final Project Environment"
echo "**************************************************"

echo "*** Installing Java 21 and tools"
sudo apt-get update
sudo DEBIAN_FRONTEND=noninteractive apt-get install -y openjdk-21-jdk maven

echo "*** Checking the Java version..."
java --version

echo "*** Configuring the developer environment..."
echo "# TDD/BDD Final Project additions" >> ~/.bashrc
echo "export GITHUB_ACCOUNT=$GITHUB_ACCOUNT" >> ~/.bashrc
echo 'export PS1="\[\e]0;\u:\W\a\]${debian_chroot:+($debian_chroot)}\[\033[01;32m\]\u\[\033[00m\]:\[\033[01;34m\]\W\[\033[00m\]\$ "' >> ~/.bashrc
echo "export JAVA_HOME=/usr/lib/jvm/java-21-openjdk-amd64" >> ~/.bashrc
echo "export PATH=\$PATH:\$JAVA_HOME/bin" >> ~/.bashrc

echo "*** Installing Selenium and Chrome for BDD"
sudo apt-get update
sudo DEBIAN_FRONTEND=noninteractive apt-get install -y sqlite3 ca-certificates chromium-driver

echo "*** Installing Maven wrapper..."
mvn -N io.takari:maven:wrapper -Dmaven=3.8.6

echo "*** Establishing application.properties file"
cp src/main/resources/application-example.properties src/main/resources/application.properties

echo "*** Starting the Postgres Docker container..."
make db

echo "*** Checking the Postgres Docker container..."
docker ps

echo "*** Building the project..."
./mvnw clean package -DskipTests

echo "**************************************************"
echo " TDD/BDD Final Project Environment Setup Complete"
echo "**************************************************"
echo ""
echo "Use 'exit' to close this terminal and open a new one to initialize the environment"
echo ""