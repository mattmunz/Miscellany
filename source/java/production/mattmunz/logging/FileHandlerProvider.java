package mattmunz.logging;

import java.io.IOException;
import java.util.logging.FileHandler; 
import java.util.logging.Handler;

public class FileHandlerProvider 
{
  Handler getHandler(String pattern) throws SecurityException, IOException 
  {
    return new FileHandler(pattern);
  }
}