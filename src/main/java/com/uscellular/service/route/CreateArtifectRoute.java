package com.uscellular.service.route;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.gson.GsonDataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.uscellular.service.route.entity.MvnProperties;
import com.uscellular.service.route.processors.CreateArtifectProcessor;
import com.uscellular.service.route.processors.MavenProcessor;
import com.uscellular.service.route.processors.ZipProcessor;


@Component
public class CreateArtifectRoute extends RouteBuilder {

	public static final String ARTIFACT_BASE_DIR = "artifactBaseDir";
	public static final String ARTIFACT_FOLDER = "artifactFolder";

	@Autowired
	MavenProcessor mavenProcessor;

	@Autowired 
	CreateArtifectProcessor createArtifectProcessor;

	@Autowired
	ZipProcessor zipProcessor;

	@Override
	public void configure() throws Exception {

		from("direct:createArtifect").routeId("CreateArtifactRoute")
			.log(":: USCC :: CreateArtifectRoute :: IN")
			.unmarshal(new GsonDataFormat(MvnProperties.class))
			.log(":: USCC :: CreateArtifectProcessor :: IN")
			.process(createArtifectProcessor)
			.log(":: USCC :: CreateArtifectProcessor :: OUT")
			.log(":: USCC :: MavenProcessor :: IN")
			.process(mavenProcessor)
			.log(":: USCC :: MavenProcessor :: OUT")
			.to("direct:createProducerRoute")
			.to("direct:createConsumerRoute")
			.log(":: USCC :: ResponseProcessor :: IN")
			.process(zipProcessor)
			.log(":: USCC :: ResponseProcessor :: OUT")
			.log(":: USCC :: CreateArtifectRoute :: OUT");
	}

}
