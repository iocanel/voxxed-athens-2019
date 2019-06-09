package org.acme;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.reactivestreams.Publisher;

@Path("/hello")
public class Hello {

    @Inject
    GreetingService greeting;
    
    @Inject
    StreaminGreetingService stream;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return greeting.greet();
    }

    @GET
    @Path("/stream")
    @Produces(MediaType.SERVER_SENT_EVENTS)
    public Publisher<String> stream() {
        return stream.greet();
    }
}
