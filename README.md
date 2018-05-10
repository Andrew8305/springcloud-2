# Spring Cloud modules demonstrated
changes applied to original version https://github.com/azarai/spring-boot-intermediate-microservices
## changes
1. Change native to GIT config store

## readings
1. [spring cloud tutorial](http://www.baeldung.com/spring-cloud-tutorial)
2. [spring cloud io](http://projects.spring.io/spring-cloud/)
3. [excellent getting started page](https://howtodoinjava.com/spring/spring-cloud/spring-cloud-config-server-git/)
4. [Eclipse Spring Tools getting started](https://o7planning.org/en/11723/understanding-spring-cloud-config-server-with-example)
5. [GIT as config backend](https://dzone.com/articles/spring-cloud-config-series-part-2-git-backend)

## references
1. [spring cloud github](https://github.com/spring-cloud)

## building
1. mvn clean install

## verifiy config server
1. java -jar target\spring-config-server-0.0.1-SNAPSHOT.jar
2. http://localhost:8888/config-server-client/development
3. http://localhost:8888/config-server-client/production

## about actuator and productive ready features
1. [actuator endpoints](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#production-ready)
