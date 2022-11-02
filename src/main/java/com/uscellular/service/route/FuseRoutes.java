package com.uscellular.service.route;

import javax.annotation.Generated;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestParamType;
import org.springframework.stereotype.Component;

/**
 * Generated from OpenApi specification by Camel REST DSL generator.
 */
@Generated("org.apache.camel.generator.openapi.PathGenerator")
@Component
public final class FuseRoutes extends RouteBuilder {
    /**
     * Defines Apache Camel routes using REST DSL fluent API.
     */
    public void configure() {

        restConfiguration().component("undertow").port(8080);

        rest("/api")
            .post("/generate")
                .id("createArtifect")
                .description("Create a fuse artifact")
                .consumes("application/json")
                .produces("application/zip,application/json")
                .param()
                    .name("body")
                    .type(RestParamType.body)
                    .required(true)
                    .description("Pet to add to the store")
                .endParam()
                .to("direct:createArtifect");

    }
}
