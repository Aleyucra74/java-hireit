#
# Build stage
#
FROM maven:3.6.0-jdk-11-slim AS build
COPY . /home/app/hireit
RUN mvn -f /home/app/hireit/pom.xml clean package

#
#package
#
RUN yum -y update
RUN yum -y remove java-1.7.0-openjdk
RUN yum install -y \
       java-1.8.0-openjdk \
       java-1.8.0-openjdk-devel

RUN yum install -y maven
RUN yum install -y curl 
RUN yum install -y unzip
COPY --from=build /home/app/hireit/target/*.jar /usr/local/lib/hireit-app.jar
ENTRYPOINT ["java","-jar","/usr/lib/hireit-app.jar"]
