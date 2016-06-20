package mattmunz.lang;

import static java.lang.System.getProperty; 

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.stream.Stream;

public class SystemHelper
{
  public Stream<String> readLinesFromSystemIn(int maximumSize)
  {
    // TODO This reader needs to be closed?
    BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));
    return inputReader.lines().limit(maximumSize);
  }

  public String getClasspath() { return getProperty("java.class.path"); }
}
