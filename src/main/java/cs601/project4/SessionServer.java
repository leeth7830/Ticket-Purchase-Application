package cs601.project4;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.SessionIdManager;
import org.eclipse.jetty.server.session.DefaultSessionIdManager;
import org.eclipse.jetty.server.session.SessionHandler;
import org.eclipse.jetty.servlet.ServletHandler;

/**
 * The server that handles paths associated with the server and reads requests and responds back to users
 * @author  Tae Hyon Lee
 *
 */
public class SessionServer {
	public static void main(String[] args) throws Exception {
		//Sets up SQL manager and config file
		ConfigurationManager manager = new ConfigurationManager();
		SQLManager.getInstance().setConfig(manager.getConfig()); 
		
		Server server = new Server(manager.getConfig().getServerPort());
        
        //create a ServletHander to attach servlets
		ServletHandler handler = new ServletHandler();
        server.setHandler(handler);
        SessionIdManager idmanager = new DefaultSessionIdManager(server);
        server.setSessionIdManager(idmanager);
        SessionHandler sessionsHandler = new SessionHandler();       
        handler.setHandler(sessionsHandler);
        
        //Add different paths to facilitate the service
        handler.addServletWithMapping(HomePageServlet.class, "/");
        handler.addServletWithMapping(SignUpServlet.class, "/signup");
        handler.addServletWithMapping(LoginServlet.class, "/login");
        handler.addServletWithMapping(LogoutServlet.class, "/logout");
        handler.addServletWithMapping(EventListServlet.class, "/eventlist");
        handler.addServletWithMapping(EventDetailServlet.class, "/event");
        handler.addServletWithMapping(CreateEventServlet.class, "/eventcreate");
        handler.addServletWithMapping(UserInfoServlet.class, "/userinfo");
        handler.addServletWithMapping(PurchaseTicketServlet.class, "/purchaseticket");
        handler.addServletWithMapping(TransferTicketServlet.class, "/transferticket");
        handler.addServletWithMapping(ModifyEventServlet.class, "/modifyevent");
        handler.addServletWithMapping(RemoveEventServlet.class, "/removeevent");
        //start the server
        server.start();
        server.join();

	}

}
