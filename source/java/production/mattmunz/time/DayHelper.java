package mattmunz.time;

import static java.time.ZonedDateTime.now; 
import static java.time.DayOfWeek.MONDAY;
import static java.time.DayOfWeek.TUESDAY;
import static java.time.DayOfWeek.WEDNESDAY;
import static java.time.DayOfWeek.THURSDAY;
import static java.time.DayOfWeek.FRIDAY;
import static java.time.DayOfWeek.SATURDAY;
import static java.time.DayOfWeek.SUNDAY;

import java.time.DayOfWeek;
import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.ImmutableMap;

public class DayHelper
{
  public static DayOfWeek today() { return DayOfWeek.from(now()); }

  private final Map<String, DayOfWeek> daysByIdentifiers 
    = ImmutableMap.<String, DayOfWeek>builder()
        .put("M", MONDAY).put("Tu", TUESDAY).put("W", WEDNESDAY).put("Th", THURSDAY)
        .put("F", FRIDAY).put("Sa", SATURDAY).put("Su", SUNDAY).build();
  
  /**
   * @param identifier like M, Tu, Th, etc. For use with these task fields for NextToDo: 
   *        day:M, day:Tu, day:W, day:Th, day:F, day:Sa, day:Su
   */
  public DayOfWeek getDayOfWeek(String identifier)
  {
    DayOfWeek day = daysByIdentifiers.get(identifier);
    
    if (day == null) { throw new IllegalArgumentException("Unknown identifier: " + identifier); }
    
    return day;
  }

	public String getIdentifier(DayOfWeek day)
	{
		for (Entry<String, DayOfWeek> entry : daysByIdentifiers.entrySet())
		{
			if (entry.getValue() == day) { return entry.getKey(); }
		}
		
		throw new IllegalArgumentException("Unsupported day of week: " + day);
	}
}
