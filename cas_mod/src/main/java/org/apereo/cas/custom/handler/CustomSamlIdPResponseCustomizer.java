package org.apereo.cas.custom.handler;

import org.apereo.cas.support.saml.util.Saml20ObjectBuilder;
import org.apereo.cas.support.saml.web.idp.profile.builders.SamlProfileBuilderContext;
import org.apereo.cas.support.saml.web.idp.profile.builders.response.SamlIdPResponseCustomizer;

import org.opensaml.saml.saml2.core.Assertion;
import org.opensaml.saml.saml2.core.Issuer;
import org.opensaml.saml.saml2.core.Response;
import org.opensaml.saml.saml2.core.Audience;
import org.opensaml.saml.saml2.core.NameID;


import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomSamlIdPResponseCustomizer implements SamlIdPResponseCustomizer {

    private static final String MICROSOFT_ONLINE_AUDIENCE = "urn:federation:MicrosoftOnline";

    @Override
    public void customizeAssertion(final SamlProfileBuilderContext context, 
                                   final Saml20ObjectBuilder builder, 
                                   final Assertion samlAssertion) {

        String customIssuer = determineCustomIssuer(context, samlAssertion);
        if (customIssuer != null) {
            Issuer issuer = builder.newIssuer(customIssuer);
            samlAssertion.setIssuer(issuer);
        }
    }

    @Override
    public void customizeResponse(final SamlProfileBuilderContext context, 
                                  final Saml20ObjectBuilder builder, 
                                  final Response samlResponse) {

        String customIssuer = determineCustomIssuer(context, samlResponse.getAssertions().get(0));
        if (customIssuer != null) {
            Issuer issuer = builder.newIssuer(customIssuer);
            samlResponse.setIssuer(issuer);
        }
    }

    private String determineCustomIssuer(final SamlProfileBuilderContext context, final Assertion samlAssertion) {
        NameID nameId = samlAssertion.getSubject().getNameID();
        String nameIdValue = nameId != null ? nameId.getValue() : null;
        String spNameQualifier = nameId != null ? nameId.getSPNameQualifier() : null;
        
        if (spNameQualifier != null && MICROSOFT_ONLINE_AUDIENCE.equals(spNameQualifier)) {
            if (nameIdValue != null && nameIdValue.contains("@")) {
                if (nameIdValue.contains("dfl.iiit.ac.in")) {
                    return "https://login-new.iiit.ac.in/cas/idp-dfl";
                }
                else if (nameIdValue.contains("students.iiit.ac.in")) {
                    return "https://login-new.iiit.ac.in/cas/idp-students";
                }
                else if (nameIdValue.contains("research.iiit.ac.in")) {
                    return "https://login-new.iiit.ac.in/cas/idp-research";
                }
		else if (nameIdValue.contains("research-test.iiit.ac.in")) {
                    return "https://login-new.iiit.ac.in/cas/idp-research-test";
                }
                return "https://login-new.iiit.ac.in/cas/idp";
            }
        }
        return null;
    }
}
