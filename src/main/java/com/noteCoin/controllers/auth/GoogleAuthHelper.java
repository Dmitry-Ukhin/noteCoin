package com.noteCoin.controllers.auth;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeRequestUrl;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson.JacksonFactory;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.Arrays;

public class GoogleAuthHelper {
    private static final String GOOGLE_CLIENT_ID =
            "123557132319-1p0flucik6sh5belbh9l2f1gi3kpk1ui.apps.googleusercontent.com";
    private static final String GOOGLE_CLIENT_SECRET = "gKMoAx9Oyb9OLRt0Jcg6it3P";
    private static final String CALLBACK_URI = "http://localhost:8080/auth";

    private static final Iterable<String> SCOPE = Arrays.asList(("https://www.googleapis.com/auth/userinfo.profile;" +
            "https://www.googleapis.com/auth/userinfo.email").split(";"));
    private static final String USER_INFO_URL = "https://www.googleapis.com/oauth2/v1/userinfo";
    private static final JsonFactory JSON_FACTORY = new JacksonFactory();
    private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();

    private String stateToken;

    private GoogleAuthorizationCodeFlow flow;

    public GoogleAuthHelper(){
        flow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY, GOOGLE_CLIENT_ID, GOOGLE_CLIENT_SECRET,
                SCOPE).build();
        generateStateToken();
    }

    public String buildLoginURI(){
        final GoogleAuthorizationCodeRequestUrl url = flow.newAuthorizationUrl();

        return url.setRedirectUri(CALLBACK_URI).setState(stateToken).build();
    }

    private void generateStateToken(){

        SecureRandom sr1 = new SecureRandom();

        stateToken = "google;"+sr1.nextInt();
    }

    public String getStateToken() {
        return stateToken;
    }

    public String getUserInfoJson(final String authCode) throws IOException {
        final GoogleTokenResponse response = flow.newTokenRequest(authCode).setRedirectUri(CALLBACK_URI).execute();
        final Credential credential = flow.createAndStoreCredential(response, null);
        final HttpRequestFactory requestFactory = HTTP_TRANSPORT.createRequestFactory(credential);

        final GenericUrl url = new GenericUrl(USER_INFO_URL);
        final HttpRequest request = requestFactory.buildGetRequest(url);

        return request.execute().parseAsString();
    }
}
