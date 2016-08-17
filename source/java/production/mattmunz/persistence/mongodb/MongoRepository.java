package mattmunz.persistence.mongodb;

import java.util.function.BiConsumer; 
import java.util.function.BiFunction;
import java.util.function.Function;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;

/**
 * TODO Should probably use Spring Data instead. By this tutorial, it looks  
 * better: http://spring.io/guides/gs/accessing-data-mongodb/
 */
public abstract class MongoRepository
{
  private final MongoClientFactory clientFactory;
  private final String dbName;
  private final String collectionName; 
  
  protected MongoRepository(MongoClientFactory clientFactory, String dbName, 
                            String collectionName)
  {
    this.clientFactory = clientFactory;
    this.dbName = dbName; 
    this.collectionName = collectionName;
  }

  protected <V> void 
    consumeWithCollection(BiConsumer<MongoCollection<Document>, V> consumer, V value)
  {
    try (MongoClient client = getClient())
    {
      consumer.accept(getCollection(client), value); 
    }
  }

  protected <O, V> O 
    applyWithCollection(BiFunction<MongoCollection<Document>, V, O> function, V value)
  {
    try (MongoClient client = getClient())
    {
      return function.apply(getCollection(client), value);
    }
  }
  
  protected <O> O getWithCollection(Function<MongoCollection<Document>, O> function)
  {
    try (MongoClient client = getClient())
    {
      return function.apply(getCollection(client));
    }
  }

  private MongoCollection<Document> getCollection(MongoClient client) 
  {
    return client.getDatabase(dbName).getCollection(collectionName);
  }

  private MongoClient getClient() { return clientFactory.getClient(); }
}
