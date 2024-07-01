package nl.meubelreview.com.setup;

import nl.meubelreview.com.security.MyUser;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class AppInitializer implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        if(!MyUser.addUser(new MyUser("user", "password"))) {
            sce.getServletContext().log("User 'user' already exists");
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
