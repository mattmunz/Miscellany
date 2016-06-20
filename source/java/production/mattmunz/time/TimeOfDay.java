package mattmunz.time;

/**
 * A description of the time of day suitable for use with NextToDo and other calendaring 
 * applications.
 * 
 * TODO Should eventually be folded in with java.util.time equivalents.
 */
public enum TimeOfDay
{
  MORNING("M"), AFTERNOON("A"), EVENING("E");

  /**
   * @param identifier like M, A, E, etc.
   */
  public static TimeOfDay forIdentifier(String identifier)
  {
    for (TimeOfDay time : values()) 
    { 
      if (time.getIdentifier().equals(identifier)) { return time; } 
    }
    
    throw new IllegalArgumentException("Unknown identifier: " + identifier);
  }

  public String getIdentifier() { return identifier; }
  
  private final String identifier;

  private TimeOfDay(String identifier) { this.identifier = identifier; }
}
