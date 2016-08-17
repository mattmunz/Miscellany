package mattmunz.persistence.mongodb;

import com.mongodb.MongoClient;

public class MongoClientFactory
{
  public MongoClient getClient() { return new MongoClient(); }
}
