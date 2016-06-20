package mattmunz.property;

public class Property
{
  private final String name;
  private final Object value;
  
  Property(String name, Object value)
  {
    this.name = name;
    this.value = value;
  }

  String getName() { return name; }

  Object getValue() { return value; }
}
