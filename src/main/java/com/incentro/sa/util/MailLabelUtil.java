package com.incentro.sa.util;

import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Label;
import com.google.api.services.gmail.model.ListLabelsResponse;
import com.incentro.sa.models.GoogleUser;
import com.incentro.sa.services.GmailAPIService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Tristan on 20-Feb-17 in gmail-sa
 */
public class MailLabelUtil {
    
    public static void checkLabelsAndAnalyseMail(GoogleUser googleUser) throws IOException {
        Gmail service = GmailAPIService.getGmailService(googleUser);
        String primaryEmail = googleUser.getPrimaryEmail();
        
        List<String> labelIds = findInboxId(service, primaryEmail);
        checkAndMakeRequiredLabels(service, primaryEmail);
        
        try {
            GmailAPIService.listMessagesWithLabels(service, googleUser.getPrimaryEmail(), labelIds, googleUser);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static void checkAndMakeRequiredLabels(Gmail service, String primaryEmail) throws IOException {
        List<String> requiredLabelList = Arrays.asList("Language Unknown", "Slightly Negative", "Negative", "Extremely Negative", "Slightly Positive", "Positive", "Extremely Positive", "Neutral");
        
        for (String labelName : requiredLabelList) {
            try {
                checkForLabelByName(service, primaryEmail, labelName);
            } catch (Exception e) {
                createLabel(service, primaryEmail, labelName);
            }
        }
    }
    
    private static List<String> findInboxId(Gmail service, String primaryEmail) throws IOException {
        ListLabelsResponse listResponse =
                service.users().labels().list(primaryEmail).execute();
        
        List<Label> labels = listResponse.getLabels();
        
        List<String> labelIds = new ArrayList<>();
        for (Label label : labels) {
            if (label.getName().equals("INBOX")) {
                labelIds.add(label.getId());
            }
        }
        return labelIds;
    }
    
    private static void checkForLabelByName(Gmail service, String primaryEmail, String labelName) throws IOException {
        service.users().labels().get(primaryEmail, GmailAPIService.getLabelIdByName(service, primaryEmail, labelName)).execute();
    }
    
    private static void createLabel(Gmail service, String primaryEmail, String labelName) throws IOException {
        Label label = new Label().setName(labelName).setLabelListVisibility("labelShow").setMessageListVisibility("show");
        service.users().labels().create(primaryEmail, label).execute();
    }
}
