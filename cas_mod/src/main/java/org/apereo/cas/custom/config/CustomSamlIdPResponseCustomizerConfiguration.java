package org.apereo.cas.custom.config;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;

import org.apereo.cas.custom.handler.CustomSamlIdPResponseCustomizer;
import org.apereo.cas.support.saml.web.idp.profile.builders.response.SamlIdPResponseCustomizer;


@AutoConfiguration
@ConditionalOnClass(SamlIdPResponseCustomizer.class)
public class CustomSamlIdPResponseCustomizerConfiguration {

    @Bean
    public SamlIdPResponseCustomizer samlIdPResponseCustomizer() {
        return new CustomSamlIdPResponseCustomizer();
    }
}
