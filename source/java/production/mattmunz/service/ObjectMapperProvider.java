package mattmunz.service;

import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS;  

import javax.ws.rs.ext.ContextResolver;  
import javax.ws.rs.ext.Provider;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Provider
public class ObjectMapperProvider implements ContextResolver<ObjectMapper> 
{
  private final ObjectMapper mapper;
  
  public ObjectMapperProvider() 
  { 
    mapper = new ObjectMapper();
    mapper.registerModule(new JavaTimeModule());
    mapper.disable(WRITE_DATES_AS_TIMESTAMPS);
  }

  @Override
  public ObjectMapper getContext(Class<?> type) { return mapper; }
}
