package com.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.cors.CorsConfiguration;

import javax.validation.constraints.NotNull;


/**
 * Properties specific to JHipster.
 *
 * <p>
 *     Properties are configured in the application.yml file.
 * </p>
 */
@ConfigurationProperties(prefix = "jhipster", ignoreUnknownFields = false)
public class ApplicationProperties {



    private final CorsConfiguration cors = new CorsConfiguration();
    private final Security security = new Security();
    private  String outputdirectory;


    public String getOutputdirectory() {
        return outputdirectory;
    }

    public void setOutputdirectory(String outputdirectory) {
        this.outputdirectory = outputdirectory;
    }

    public CorsConfiguration getCors() {
        return cors;
    }

    public Security getSecurity() {
        return security;
    }

    public static class Security {

        private final Rememberme rememberme = new Rememberme();

        private final Authentication authentication = new Authentication();

        public Rememberme getRememberme() {
            return rememberme;
        }

        public Authentication getAuthentication() {
            return authentication;
        }

        public static class Authentication {

            private final Oauth oauth = new Oauth();

            public Oauth getOauth() {
                return oauth;
            }

            public static class Oauth {

                private String clientid;

                private String secret;

                private int tokenValidityInSeconds = 1800;

                public String getClientid() {
                    return clientid;
                }

                public void setClientid(String clientid) {
                    this.clientid = clientid;
                }

                public String getSecret() {
                    return secret;
                }

                public void setSecret(String secret) {
                    this.secret = secret;
                }

                public int getTokenValidityInSeconds() {
                    return tokenValidityInSeconds;
                }

                public void setTokenValidityInSeconds(int tokenValidityInSeconds) {
                    this.tokenValidityInSeconds = tokenValidityInSeconds;
                }
            }
        }
        public static class Rememberme {

            @NotNull
            private String key;

            public String getKey() {
                return key;
            }

            public void setKey(String key) {
                this.key = key;
            }
        }
    }


}
