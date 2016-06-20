package mattmunz;

import java.nio.file.Path; 
import java.nio.file.Paths;
import java.time.DayOfWeek;
import java.util.function.Function;

import mattmunz.time.DayHelper;
import mattmunz.time.TimeOfDay;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.AbstractConverter;

/**
 * TODO Should implement the beanutils Converter interface?
 */
public class Converter
{
  /**
   * Delegates conversion to a provided function to make it easy to create new converters.
   * 
   * TODO Move to top level? 
   */
  private static class SimpleConverter<T> extends AbstractConverter
  {
    private final Class<?> targetType;
    private final Function<Object, T> converter;

    private static <T2> SimpleConverter<T2> 
      getConverter(Class<T2> targetType, Function<String, T2> converter) 
    {
      return new SimpleConverter<>(targetType, 
                                   (object -> converter.apply(object.toString())));
    }

    private SimpleConverter(Class<T> targetType, Function<Object, T> converter) 
    {
      this.targetType = targetType;
      this.converter = converter;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    protected <T2> T2 convertToType(Class<T2> type, Object value) throws Throwable
    {
      if (!type.isAssignableFrom(targetType)) { throw conversionException(type, value); }
      
      return (T2) converter.apply(value); 
    }

    @Override
    protected Class<?> getDefaultType() { return targetType; }
  }

  /**
   * TODO For better safety use the ConvertUtilsBean class. That allows safe converter registration.
   */
  static 
  { 
    DayHelper dayHelper = new DayHelper();

    // TODO Clean up, static imports, etc. 
    
    ConvertUtils.register(SimpleConverter.getConverter(Path.class, Paths::get), Path.class);
    ConvertUtils.register(SimpleConverter.getConverter(DayOfWeek.class, dayHelper::getDayOfWeek), DayOfWeek.class);
    ConvertUtils.register(SimpleConverter.getConverter(TimeOfDay.class, TimeOfDay::forIdentifier), TimeOfDay.class);
  }
  
  @SuppressWarnings("unchecked")
  public <V> V convert(Object value, Class<V> targetType)
  {
    if (value == null) { throw new IllegalArgumentException("Cannot convert null"); }

    /*
     * TODO For better safely use the ConvertUtilsBean class. That allows safe converter 
     *      registration.
     */
    Object newValue = ConvertUtils.convert(value, targetType);

    if (newValue == null || !(targetType.isAssignableFrom(newValue.getClass())))
    {
      String message 
        = "Couldn't convert [" + value + "] to type [" + targetType + "]. There may not be "
          + "a registered converter for this type.";
      throw new IllegalArgumentException(message);
    }

    return (V) newValue;
  }
}
