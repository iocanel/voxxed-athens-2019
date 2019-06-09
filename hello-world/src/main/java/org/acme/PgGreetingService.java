
package org.acme;

import java.util.concurrent.CompletionStage;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import io.reactiverse.axle.pgclient.PgPool;

@ApplicationScoped
public class PgGreetingService {
  
    @Inject
    PgPool client;

    CompletionStage<String> greet() {
        return client.query("select * from greeting where lang='it'").thenApply(rs->rs.iterator().next().getString("greeting"));
    }
}
