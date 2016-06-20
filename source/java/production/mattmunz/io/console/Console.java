package mattmunz.io.console;

import java.io.InputStreamReader; 
import java.io.Reader;

/**
 * A testable/mockable alternative for system.io.Console. Mostly just delegates to a console.
 */
public class Console
{
  private final java.io.Console console;
  
  public Console(java.io.Console console) 
  { 
    if (console == null) { throw new IllegalArgumentException("Console cannot be null"); }
    
    this.console = console; 
  }

  public Reader reader() { return new InputStreamReader(System.in); }

  public void format(String format, Object ...arguments) { console.format(format, arguments); }
}
