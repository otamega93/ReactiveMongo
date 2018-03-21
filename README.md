# ReactiveMongo
Poc of Spring Reactive using MongoDb (Embedded).

This is a proof of concept of spring webflux (which uses Reactor Core as reactive library). The present project has two simple domain objects (Product and User); product is there just to test an async REST API with webflux and User is just so Spring Security could tie up with everything.

# Regarding the design

Products has its owns endpoints (they're within the samples folder) and this is so also for Users. Every endpoint of products, except for the delete one, can be used without role based auth. This means all the product is actually "locked" just for authenticated users. So first of all we need to create a user so we can log in and then use the product endpoint at will.

This project also integrates Spring security but it's modified so we don't get a cookie, but a JWT. This implementation is based on TransEmpiric's webFluxTemplate project (with some minor tweaks and improvements). Since I'm fairly new to reactive programming and there's not so much info regarding a custom auth filter for spring wecurity in a webflux app, I cannot say for sure that this is 100% reactive; in fact, the user and pass validation is done in a sync non-blocking way blended with the reactive, async/non-blocking way.

Even so, since there're not so much to based this on, I think this is a pretty nice first attempt. It works OK and since it has security based on JWT (so no state) and service layer (using Mongo and the reactive driver), you could think of it like a "complete" microservice.

This also integrates with my Eureka server for service registry (this one is inside my SpringCloudArquitectureEnv project under the "register-service" name). You can run that Spring Boot project and then this one shall register there as a microservice.

# Disclaimer

As I said, This is a proof of concept and should be taken as for what it is. This is for my learning. If someone spots a flaw or something, I'm very open to listen :)

Regarding exception handling: https://github.com/callicoder/spring-webflux-reactive-rest-api-demo/issues/1.
Regarding the project I based my security design on: https://github.com/TransEmpiric/webFluxTemplate.
