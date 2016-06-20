package mattmunz.property;

import static com.google.common.base.MoreObjects.toStringHelper;   
import static java.util.Objects.hash;
import static mattmunz.util.stream.Streams2.zip;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import mattmunz.property.Property;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.MoreObjects.ToStringHelper;

/**
 * Provides some basics (hashcode / toString) for objects that declare their properties.
 */
public interface PropertiedObject
{
  /**
   * TODO Move to a more general class like Properties
   * TODO Needs better handling of collections, etc.?
   */
  static boolean areValuesEqual(Property left, Property right)
  {
    return Objects.equals(left.getValue(), right.getValue());
  }

  List<Property> getProperties();
  
  @JsonIgnore
  default String getToStringText()
  {
    ToStringHelper toStringHelper = toStringHelper(this);

    for (Property property : getProperties())
    {
      toStringHelper.add(property.getName(), property.getValue());
    }
    
    return toStringHelper.toString();
  }

  @JsonIgnore
  default int getHashCode()
  {
    return hash(getProperties().stream().map(Property::getValue));
  }

  default boolean isEqualTo(Object other)
  {
    if (this == other) { return true; }
    
    if (other == null || !(getClass().isAssignableFrom(other.getClass()))) { return false; }
    
    PropertiedObject otherPropertiedObject = (PropertiedObject) other;
    
    Stream<Property> properties = getProperties().stream();
    Stream<Property> otherProperties = otherPropertiedObject.getProperties().stream();
    
    return zip(properties, otherProperties, PropertiedObject::areValuesEqual)
            .allMatch(areEqual -> areEqual == true);
  }
}
