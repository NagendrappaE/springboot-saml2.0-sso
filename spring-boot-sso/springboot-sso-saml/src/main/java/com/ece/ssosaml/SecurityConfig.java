/**
 * 
 */
package com.ece.ssosaml;

import java.io.File;
import java.security.cert.X509Certificate;

import javax.servlet.http.HttpServletRequest;

import org.opensaml.security.x509.X509Support;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.saml2.core.Saml2X509Credential;
import org.springframework.security.saml2.provider.service.metadata.OpenSamlMetadataResolver;
import org.springframework.security.saml2.provider.service.registration.InMemoryRelyingPartyRegistrationRepository;
import org.springframework.security.saml2.provider.service.registration.RelyingPartyRegistration;
import org.springframework.security.saml2.provider.service.registration.RelyingPartyRegistrationRepository;
import org.springframework.security.saml2.provider.service.servlet.filter.Saml2WebSsoAuthenticationFilter;
import org.springframework.security.saml2.provider.service.web.DefaultRelyingPartyRegistrationResolver;
import org.springframework.security.saml2.provider.service.web.Saml2MetadataFilter;
/**
 * @author nagendrappae
 *
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig  extends WebSecurityConfigurerAdapter{

	@Autowired
	private RelyingPartyRegistrationRepository relyingPartyRegistrationRepository;

	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests(authorize -> authorize.antMatchers("/").permitAll().anyRequest().authenticated())
				.saml2Login();

		// add auto-generation of ServiceProvider Metadata
		Converter<HttpServletRequest, RelyingPartyRegistration> relyingPartyRegistrationResolver = new DefaultRelyingPartyRegistrationResolver(
				relyingPartyRegistrationRepository);
		Saml2MetadataFilter filter = new Saml2MetadataFilter(relyingPartyRegistrationResolver,
				new OpenSamlMetadataResolver());
		http.addFilterBefore(filter, Saml2WebSsoAuthenticationFilter.class);
	}

	/*
	 * @Bean protected RelyingPartyRegistrationRepository
	 * relyingPartyRegistrations() throws Exception { ClassLoader classLoader =
	 * getClass().getClassLoader(); File verificationKey = new
	 * File(classLoader.getResource("saml-certificate/okta.cert").getFile());
	 * X509Certificate certificate = X509Support.decodeCertificate(verificationKey);
	 * Saml2X509Credential credential =
	 * Saml2X509Credential.verification(certificate); RelyingPartyRegistration
	 * registration = RelyingPartyRegistration.withRegistrationId("okta-saml")
	 * .assertingPartyDetails(party ->
	 * party.entityId("http://www.okta.com/exka2fgpd5SWoUKUd5d7")
	 * .singleSignOnServiceLocation(
	 * "https://dev-53681077.okta.com/app/dev-53681077_nagendrademosso_1/exka2fgpd5SWoUKUd5d7/sso/saml")
	 * .wantAuthnRequestsSigned(false).verificationX509Credentials(c ->
	 * c.add(credential))) .build(); return new
	 * InMemoryRelyingPartyRegistrationRepository(registration); }
	 */
}
