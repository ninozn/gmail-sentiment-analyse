//[START all]
package com.incentro.sa.services.datastore;

import com.googlecode.objectify.ObjectifyService;
import com.incentro.sa.models.EmailObject;
import com.incentro.sa.models.GoogleUser;
import com.incentro.sa.models.UserMailStatistics;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * OfyHelper, a ServletContextListener, is setup in web.xml to run before a JSP is run.  This is
 * required to let JSP's access Ofy.
 **/
public class OfyHelper implements ServletContextListener {
  public void contextInitialized(ServletContextEvent event) {
    // This will be invoked as part of a warmup request, or the first user request if no warmup
    // request.
    ObjectifyService.register(GoogleUser.class);
      ObjectifyService.register(UserMailStatistics.class);
    ObjectifyService.register(EmailObject.class);
  }

  public void contextDestroyed(ServletContextEvent event) {
    // App Engine does not currently invoke this method.
  }
}
//[END all]
