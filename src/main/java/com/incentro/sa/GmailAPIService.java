package com.incentro.sa;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.repackaged.org.apache.commons.codec.binary.Base64;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.GmailScopes;
import com.google.api.services.gmail.model.*;
import com.google.api.services.language.v1beta1.model.Sentiment;
import com.incentro.sa.models.EmailObject;
import com.incentro.sa.models.GoogleUser;
import com.incentro.sa.models.UsersInfo;
import org.apache.commons.mail.util.MimeMessageParser;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.*;
import java.util.Date;
import java.util.logging.Logger;

import static com.googlecode.objectify.ObjectifyService.ofy;


public class GmailAPIService {
    /** Application name. */
    private static final String APPLICATION_NAME =
            "Gmail API Java Quickstart";


    /** Directory to store user credentials for this application. */
    private static final java.io.File DATA_STORE_DIR = new java.io.File("WEB-INF/credentials/");

    /** Global instance of the {@link FileDataStoreFactory}. */
    private static FileDataStoreFactory DATA_STORE_FACTORY;


    /** Global instance of the JSON factory. */
    private static final JsonFactory JSON_FACTORY =
            JacksonFactory.getDefaultInstance();

    /** Global instance of the HTTP transport. */
    private static HttpTransport HTTP_TRANSPORT;

    private static String resultPolarityString;

    /** Global instance of the scopes required by this quickstart.
     *
     * If modifying these scopes, delete your previously saved credentials
     * at ~/.credentials/gmail-java-quickstart
     */
    private static final List<String> SCOPES =
            Arrays.asList(GmailScopes.MAIL_GOOGLE_COM);

    private final static Logger LOGGER = Logger.getLogger(GmailAPIService.class.getName());
    static {
        try {
            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        } catch (Throwable t) {
            LOGGER.warning("data source failed");
            t.printStackTrace();
        }
    }

    public static Credential getCredentialObject(GoogleUser gu) throws IOException {

        return new GoogleCredential.Builder()
                .setTransport(HTTP_TRANSPORT)
                .setJsonFactory(JSON_FACTORY)
                .setClientSecrets(Constant.WEBCLIENT_ID, Constant.WEBCLIENT_SECRET)
                .build().setRefreshToken(gu.getRefreshToken());
    }


    public static Gmail getGmailService(GoogleUser gu) throws IOException {
        Credential credential = getCredentialObject(gu);
        return new Gmail.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
               .build();
    }

    public static String getLabelIdByName(Gmail service, String userId, String labelName) throws IOException {
        ListLabelsResponse listResponse = service.users().labels().list(userId).execute();
        List<Label> labels = listResponse.getLabels();
        for(Label l : labels) {
            if(l.getName().toLowerCase().equals(labelName.toLowerCase())){
                return l.getId();
            }
        }
        return null;
    }

    public static List<Message> listMessagesWithLabels(Gmail service, String userId,
                                                       List<String> labelIds,GoogleUser gu) throws Exception {
        ListMessagesResponse response = service.users().messages().list(userId).setLabelIds(labelIds).execute();
        List<Message> messages = new ArrayList<Message>();

        while (response.getMessages() != null) {
            messages.addAll(response.getMessages());
            if (response.getNextPageToken() != null) {
                String pageToken = response.getNextPageToken();
                response = service.users().messages().list(userId).setLabelIds(labelIds)
                        .setPageToken(pageToken).execute();
            } else {
                break;
            }
        }

        for (Message message : messages) {
            MimeMessage yourMimeMessage = getMimeMessage(getGmailService(gu),userId,message.getId());
            MimeMessageParser parser = new MimeMessageParser(yourMimeMessage);
            parser.parse();
            String plainContent = parser.getPlainContent();
            String fromMail = parser.getFrom();
            String subjectMail = parser.getSubject();

            NaturalLanguageAPIService app = new NaturalLanguageAPIService(NaturalLanguageAPIService.getLanguageService(gu));
            try{
                Sentiment resultofanalyse = app.analyzeSentiment(plainContent);
                Float magnitudeResult = resultofanalyse.getMagnitude();
                Float polarityResult = resultofanalyse.getPolarity();
                modifyClassification(service,userId,message.getId(),polarityResult,gu);
            } catch (Exception e)
            {
                List<String> labelstoadd = new ArrayList<String>();
                resultPolarityString = "Language Unknown";
                System.out.println("1" + resultPolarityString);
                labelstoadd.add(getLabelIdByName(service,userId,"Language Unknown"));
                List<String> labelstoremove = new ArrayList<String>();
                labelstoremove.add("INBOX");
                UsersInfo userinfo = null;
                userinfo = checkUserInfo(userId);
                if(userinfo!= null)
                {
                    userinfo.addUnknown();
                    ofy().save().entity(userinfo).now();
                }
                modifyMessage(getGmailService(gu),userId,message.getId(),labelstoadd,labelstoremove);
            }

            createEmailObject(service,gu,message.getId(), fromMail, subjectMail);
        }
        return messages;
    }

    private static void createEmailObject(Gmail service, GoogleUser gu, String mesID, String from, String sub) {
        Date currentDate = new Date();

        String subshort = sub;
        if (subshort.length() > 50) {
            subshort = subshort.substring(0,49) + "...";
        }
        EmailObject eo = new EmailObject(gu.getPrimaryEmail(),currentDate,mesID , from, subshort);
        eo.setStatusMail(resultPolarityString);
        ofy().save().entity(eo).now();
    }

    public static MimeMessage getMimeMessage(Gmail service, String userId, String messageId)
            throws IOException, MessagingException {
        Message message = service.users().messages().get(userId, messageId).setFormat("raw").execute();

        Base64 base64Url = new Base64(true);
        byte[] emailBytes = base64Url.decodeBase64(message.getRaw());

        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);

        return new MimeMessage(session, new ByteArrayInputStream(emailBytes));
    }


    public static void modifyClassification(Gmail service, String userId, String messageId, Float pol,GoogleUser gu) throws IOException {
        String labelText = "";
        List<String> labelstoadd = new ArrayList<String>();
        List<String> labelstoremove = new ArrayList<String>();
        labelstoremove.add("INBOX");
        UsersInfo userinfo = null;
        userinfo = checkUserInfo(userId);

        if(pol <= -0.9){
            labelText = "Extremely Negative";
            userinfo.addENegative();
        }
        else if(pol < -0.3 && pol > -0.9){
            labelText = "Negative";
            userinfo.addNegative();
        }
        else if(pol < 0 && pol >= -0.3){
            labelText = "Slightly Negative";
            userinfo.addSNegative();
        }
        else if(pol > 0 && pol <= 0.3){
            labelText = "Slightly Positive";
            userinfo.addSPositive();
        }
        else if(pol > 0.3 && pol < 0.9){
            labelText = "Positive";
            userinfo.addPositive();
        }
        else if(pol >= 0.9){
            labelText = "Extremely Positive";
            userinfo.addEPositive();
        }
        else{
            labelText = "Neutral";
            userinfo.addNeutral();
        }


        resultPolarityString = labelText;
        System.out.println("2" + resultPolarityString);
        String labelID = getLabelIdByName(service, userId, labelText);
        LOGGER.severe(userinfo + "");
        ofy().save().entity(userinfo).now();
        labelstoadd.add(labelID);
        modifyMessage(getGmailService(gu),userId,messageId,labelstoadd,labelstoremove);
    }

    public static UsersInfo checkUserInfo(String userId)
    {
        UsersInfo userinfo = null;
            try{
                userinfo = ofy().load().type(UsersInfo.class).filter("userId", userId).first().now();
                if( userinfo == null)
                {
                    userinfo = new UsersInfo();
                    userinfo.setUserId(userId);
                }
            }
            catch (Exception e)
            {
                System.out.println("cannot find user in database");
            }

        return userinfo;
    }
    public static void modifyMessage(Gmail service, String userId, String messageId,
                                     List<String> labelsToAdd, List<String> labelsToRemove) throws IOException {

        ModifyMessageRequest mods = new ModifyMessageRequest().setAddLabelIds(labelsToAdd)
                .setRemoveLabelIds(labelsToRemove);
        Message message = service.users().messages().modify(userId, messageId, mods).execute();
    }

}