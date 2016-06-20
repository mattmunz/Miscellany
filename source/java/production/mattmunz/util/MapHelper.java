package mattmunz.util;

import java.util.Map;

public class MapHelper
{
  /**
   * @throws IllegalArgumentException if the key in question is already in use.
   */
  @SafeVarargs
  public final <K, V> void put(V value, Map<K, V> map, K... keys)
    throws IllegalArgumentException
  {
    for (K key : keys) 
    { 
      V priorValue = map.put(key, value);
      
      if (priorValue != null) 
      {
        String message = "Value already existed for key [" + key +"]. value: [" + value + "].";
        throw new IllegalArgumentException(message);
      }
    }
  }
}
