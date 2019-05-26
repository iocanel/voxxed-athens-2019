
package org.acme;

import java.util.concurrent.CompletionStage;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import io.reactiverse.axle.pgclient.PgPool;

@ApplicationScoped
public class GreetingService {

    @Inject
    PgPool pg;
    
    @ConfigProperty(name="message")
    String message;
    
    CompletionStage<String> greet() {
        return pg.query("select * from greetings where lang='es'").thenApply(rs -> rs.iterator().next().getString("greeting"));
    }
}
