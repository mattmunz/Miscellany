package mattmunz.logging;

import java.io.IOException;  
import java.nio.file.Path;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Convenience extensions to the logging API.
 * 
 * TODO Needs a better name
 */
public class LogHelper
{
  private FileHandlerProvider fileHandlerProvider;

  public LogHelper(FileHandlerProvider fileHandlerProvider) 
  { 
    this.fileHandlerProvider = fileHandlerProvider;
  }

  public void configureRootWithHandler(Path logsDirectory, String logName, Level level) 
    throws IOException
  {
    // TODO This handler is never closed. Does it matter?
    Handler handler 
      = createLogRecordHandler(logsDirectory, logName, level, new SimpleFormatter());
    
    configure(getRootLogger(), level, handler);
  }

  public void configure(Logger logger, Level level, Handler logRecordHandler)
  {
    logger.setLevel(level);
    logger.setUseParentHandlers(false);
    
    removeAllHandlers(logger);
    logger.addHandler(logRecordHandler);
  }

  public Handler createLogRecordHandler(Path logsPath, String fileName, Level level, 
                                        Formatter formatter)
      throws IOException
  {
    Handler handler 
      = fileHandlerProvider.getHandler("" + logsPath + "/" + fileName + ".%u.%g.log");
    handler.setLevel(level);
    handler.setFormatter(formatter);
    
    return handler;
  }

  private void removeAllHandlers(Logger logger)
  {
    for (Handler handler : logger.getHandlers()) { logger.removeHandler(handler); }
  }

  private Logger getRootLogger() { return Logger.getLogger(""); }
}
