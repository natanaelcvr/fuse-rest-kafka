package com.uscellular.service.config;

import java.util.Arrays;
import java.util.List;

import org.apache.camel.util.jsse.KeyManagersParameters;
import org.apache.camel.util.jsse.KeyStoreParameters;
import org.apache.camel.util.jsse.SSLContextParameters;
import org.apache.camel.util.jsse.SecureSocketProtocolsParameters;
import org.apache.camel.util.jsse.TrustManagersParameters;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebServiceConfig {

	@Value("${ssl.truststore.secrets-path}")
	protected String secretPath;
	@Value("${ssl.truststore.jks-file}")
	protected String trustStoreFile;
	@Value("${ssl.truststore.jks-file-password}")
	protected String trustStorePassword;
	@Value("${ssl.identitystore.jks-file}")
	protected String identityStoreFile;
	@Value("${ssl.identitystore.jks-file-password}")
	protected String identityStorePassword;

	public String getSecretPath() {
		return secretPath;
	}

	public String getTrustStoreFile() {
		return trustStoreFile;
	}

	public String getTrustStorePassword() {
		return trustStorePassword;
	}

	@Bean(name = "clientSSLParameters")
	protected SSLContextParameters setSSLContextParameters() {
		// IDENTITY STORE
		KeyStoreParameters kspm = new KeyStoreParameters();
		kspm.setResource(secretPath + "/" + identityStoreFile);
		kspm.setPassword(identityStorePassword);
		KeyManagersParameters kmp = new KeyManagersParameters();
		kmp.setKeyStore(kspm);
		kmp.setKeyPassword(identityStorePassword);

		// TRUST STORE
		KeyStoreParameters kspt = new KeyStoreParameters();
		kspt.setResource(secretPath + "/" + trustStoreFile);
		kspt.setPassword(trustStorePassword);
		TrustManagersParameters tmp = new TrustManagersParameters();
		tmp.setKeyStore(kspt);

		SecureSocketProtocolsParameters socketProtocols = new SecureSocketProtocolsParameters();
		List<String> protocols = Arrays.asList("TLSv1", "TLSv1.1", "TLSv1.2");
		socketProtocols.setSecureSocketProtocol(protocols);

		SSLContextParameters scp = new SSLContextParameters();
		scp.setKeyManagers(kmp);
		scp.setTrustManagers(tmp);
		scp.setSecureSocketProtocols(socketProtocols);

		return scp;
	}

	@Bean(name = "noopHostnameVerifier")
	protected NoopHostnameVerifier noopHostnameVerifier() {
		return new NoopHostnameVerifier();
	}
}