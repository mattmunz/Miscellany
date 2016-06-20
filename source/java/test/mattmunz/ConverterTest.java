package mattmunz;

import static org.junit.Assert.assertEquals; 

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;

public class ConverterTest
{
  @Test
  public void convertToPath()
  {
    assertEquals(Paths.get("/foo/bar/baz"), 
                 new Converter().convert("/foo/bar/baz", Path.class));
  }
}
