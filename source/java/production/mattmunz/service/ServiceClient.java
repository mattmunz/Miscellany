package mattmunz.service;

import static java.util.logging.Level.SEVERE;   
import static java.util.logging.Logger.getLogger;
import static javax.ws.rs.client.ClientBuilder.newClient;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import java.util.List;
import java.util.logging.Logger;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status.Family;

import org.glassfish.jersey.jackson.JacksonFeature;

/** 
 * TODO Rework to use Sprint Boot/ReST?
 */
public class ServiceClient
{
  public static final Logger logger = getLogger(ServiceClient.class.getName());

  private final String host;
  private final int port;
  private final String applicationName;
  
  protected ServiceClient(String host, int port, String applicationName)
  {
    // TODO Assert that the host name syntax is valid
    this.host = host;
    
    // TODO Assert that the port number is a valid one -- limits set by IETF RFC
    this.port = port;
    
    this.applicationName = applicationName;
  }

  /**
   * @param path Will be URL escaped
   */
  protected <T> List<T> get(String path, GenericType<List<T>> responseType)
  {
    return get(getTarget(path), responseType);
  }

  protected <T> List<T> get(WebTarget target, GenericType<List<T>> responseType)
  {
    Builder invocationBuilder = target.request(APPLICATION_JSON);
    
    try { return invocationBuilder.get(responseType); }
  	  catch(NotFoundException e) 
    { 
  	    // TODO Similar catch blocks should be refactored together
  		
  	    String message = "Entity not found for " + target.getUri();
  		
  	    logger.log(SEVERE, message, e); 
  		
  	    throw new NotFoundException(message, e.getResponse(), e);
    }
    catch (ProcessingException e)
  	  {
  		  String message = "Couldn't process (maybe couldn't connect to) " + target.getUri();
  		
  		  logger.log(SEVERE, message, e); 
  		
  		  // TODO Better exception type here
  		  throw new RuntimeException(message, e);  		
  	  }
  }

  protected <T> Response post(String path, T body)
  {
    // TODO Use static imports
    // TODO This URL building is redundant with getTarget, below
    Response response 
      = ClientBuilder.newClient().target("http://" + host + ":" + port + "/" + applicationName)
              .register(JacksonFeature.class)
              .path(path)
              .request(MediaType.APPLICATION_JSON)
              .post(Entity.json(body));

    logger.fine("Response: " + response);
    
    // TODO Static import
    if (!(Family.SUCCESSFUL.equals(response.getStatusInfo().getFamily()))) {
      
      String message 
        = "Got an unsuccessful response status from the server [" + response.getStatus() 
          + "]. Response: [" + response + "].";

      // TODO Find a better exception type
      throw new RuntimeException(message);
    }
    
    return response;
  }

  protected WebTarget getTarget(String path) { return getTarget(applicationName, path); }
  
  /**
   * TODO applicationName is actually a class variable...
   * TODO Should use UriBuilder
   * @param path Will be URL escaped. If you want parameters, use the WebTarget API to add them.
   */
  private WebTarget getTarget(String applicationName, String path)
  {
    String uri = "http://" + host + ":" + port + "/" + applicationName;
    
    WebTarget target 
      = newClient().target(uri).register(JacksonFeature.class)
                   .register(ObjectMapperProvider.class).path(path);
    
    return target;
  }
}
