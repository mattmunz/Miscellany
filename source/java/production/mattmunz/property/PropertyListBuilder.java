package mattmunz.property;

import java.util.ArrayList;
import java.util.List;

public class PropertyListBuilder
{
  private final List<Property> properties = new ArrayList<Property>();
  
  public PropertyListBuilder add(String name, Object value)
  {
    properties.add(new Property(name, value)); return this;
  }
  
  public List<Property> build() { return properties; }
}
