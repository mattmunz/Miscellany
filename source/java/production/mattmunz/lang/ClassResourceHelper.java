package mattmunz.lang;

import static java.lang.System.getProperty;

import java.io.IOException;
import java.io.InputStream;

public class ClassResourceHelper
{
  /**
   * A replacement for the similar JDK API which uses null as an error code. 
   * 
   * @throws IOException if the resource cannot be found.
   */
  public InputStream getResource(Class<?> resourceClass, String resourceName) throws IOException
  {
    InputStream stream = resourceClass.getResourceAsStream(resourceName);
    
    if (stream == null)
    {
      String message 
        = "Resource not found: " + resourceName + ", using class/classpath: " 
          + resourceClass.getName() + ", " + getProperty("java.class.path");
      
      throw new IOException(message);
    }
    
    return stream;
  }
}
