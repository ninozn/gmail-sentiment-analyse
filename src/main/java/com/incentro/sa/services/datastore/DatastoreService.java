package com.incentro.sa.services.datastore;

import com.incentro.sa.models.EmailObject;
import com.incentro.sa.models.GoogleUser;

import java.util.List;

import static com.googlecode.objectify.ObjectifyService.ofy;

/**
 * Created by Tristan on 20-Feb-17 in gmail-sa
 */
public class DatastoreService {
    
    public static GoogleUser getUserByMail(String email) {
        try {
            return ofy().load().type(GoogleUser.class).filter("primaryEmail", email).first().now();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static void deleteUserByEntity(GoogleUser googleUser) {
        try {
            ofy().delete().entity(googleUser).now();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static List<EmailObject> getEmailObjectsByMail(String email) {
        try {
            return ofy().load().type(EmailObject.class).filter("primaryEmail", email).list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static List<GoogleUser> getAllSubscribedUsers() {
        try {
            return ofy().load().type(GoogleUser.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
