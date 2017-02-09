package com.incentro.sa;

import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.extensions.appengine.datastore.AppEngineDataStoreFactory;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.oauth2.Oauth2;
import com.google.api.services.oauth2.model.Tokeninfo;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.appengine.api.utils.SystemProperty;
import com.google.apphosting.api.ApiProxy;
import com.incentro.sa.models.GoogleUser;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import static com.googlecode.objectify.ObjectifyService.ofy;

/**
 * To use this class copy/paste this on a location where you want to start the login process for the visitor:
 * **************************************************************
 GoogleOAuth2Util googleOAuth2Util = new GoogleOAuth2Util();
 String loginUrl = googleOAuth2Util.getSubscribeUrl(httpServletRequest);
 httpServletResponse.sendRedirect(loginUrl);
 * **************************************************************
 * 1) In Google Cloud Console, set correct "Authorised redirect URIs"
 * 2) Be sure to have a working url for /oauth2callback
 * 3) Make sure the Constant.* variables in this file are correct
 */
public class GoogleOAuth2Util {
    static GoogleUser currentuser = null;

    public Tokeninfo getTokenInfo(HttpServletRequest httpServletRequest, String authorisationCode) throws IOException {
        GoogleAuthorizationCodeFlow authorizationCodeFlow = getAuthorizationCodeFlow();

        TokenResponse tokenResponse = authorizationCodeFlow.newTokenRequest(authorisationCode).setRedirectUri(getCallbackUrl(httpServletRequest)).execute();

        Oauth2 oauth2 = new Oauth2.Builder(new NetHttpTransport(),  new JacksonFactory(), null).setApplicationName(Constant.APPLICATION_NAME).build();
        Tokeninfo tokeninfo = oauth2.tokeninfo().setAccessToken(tokenResponse.getAccessToken()).execute();

        try {
            UserService userService = UserServiceFactory.getUserService();
            User serviceuser = userService.getCurrentUser();
            if (!serviceuser.getEmail().equals(tokeninfo.getEmail())){
                return null;
            }

            boolean test = true;
            for (GoogleUser gu : ofy().load().type(GoogleUser.class).list())
            {
                if(gu.getPrimaryEmail().equals(tokeninfo.getEmail())){
                    test = false;
                    //httpServletRequest.setAttribute("errormessage","email is already subscribed");
                    return tokeninfo;
                }
            }
            if (test) {
                if (tokenResponse.getRefreshToken() != null && !tokenResponse.getRefreshToken().equals("") || tokenResponse.getAccessToken() != null) {
                    GoogleUser user = new GoogleUser();
                    user.setRefreshToken(tokenResponse.getRefreshToken());
                    user.setPrimaryEmail(tokeninfo.getEmail());
                    user.setUserId(tokeninfo.getUserId());
                    user.setAccessToken(tokenResponse.getAccessToken());
                    ofy().save().entity(user).now();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return tokeninfo;
    }

    public GoogleAuthorizationCodeFlow getAuthorizationCodeFlow() throws IOException {
        GoogleAuthorizationCodeFlow authorizationCodeFlow = new GoogleAuthorizationCodeFlow.Builder(
                new NetHttpTransport(),
                JacksonFactory.getDefaultInstance(),
                Constant.WEBCLIENT_ID,
                Constant.WEBCLIENT_SECRET,
                Constant.WEBCLIENT_SCOPES
        ).setDataStoreFactory(
                new AppEngineDataStoreFactory()
        ).build();
        return authorizationCodeFlow;
    }

    public String getSubscribeUrl(HttpServletRequest httpServletRequest) throws IOException {

        GoogleAuthorizationCodeFlow authorizationCodeFlow = getAuthorizationCodeFlow();
        GenericUrl url = authorizationCodeFlow.newAuthorizationUrl()
                .setScopes(Constant.WEBCLIENT_SCOPES)
                .setRedirectUri(getCallbackUrl(httpServletRequest));
        List<BasicNameValuePair> params = new LinkedList<>();
        params.add(new BasicNameValuePair("intended", httpServletRequest.getRequestURL().toString() ));

        String prompt = httpServletRequest.getParameter("prompt");
        if (prompt != null && Objects.equals(prompt, "true")) {
            url.set("prompt", "select_account");
        } else {
            url.set("approval_prompt", "force");
        }

        url.set("state", URLEncodedUtils.format(params, "utf-8"));
        url.set("access_type", "offline");

        return url.build();
    }

    private String getCallbackUrl(HttpServletRequest httpServletRequest) {

        String domain = null;
        try {
            domain = new URL(httpServletRequest.getRequestURL().toString()).getHost();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        if (SystemProperty.environment.value() == SystemProperty.Environment.Value.Development) {
            ApiProxy.Environment env = ApiProxy.getCurrentEnvironment();
            // localhost needs proper port, not always 8080
            if(domain.equals("sentiment-analyse.appspot.com")){
                domain += ":" + env.getAttributes().get("com.google.appengine.instance.port"); // produces localhost:8080, localhost:8081
            } else {
                domain = (String) env.getAttributes().get("com.google.appengine.runtime.default_version_hostname"); // produces ips.appspot.com, randstad.com
            }
        }

        if (httpServletRequest.isSecure()) {
            return "https://" + domain + "/oauth2callback";
        }
        return "http://" + domain + "/oauth2callback";

    }
    public static GoogleUser getUser()
    {
        return currentuser;
    }
}