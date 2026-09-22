package com.napier.sem;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class App
{
    private MongoClient mongoClient = null;

    /**
     * Connect to the MongoDB database with retry logic.
     */
    public void connect()
    {
        int retries = 10;
        for (int i = 0; i < retries; ++i)
        {
            System.out.println("Connecting to database...");
            try
            {
                // Pause for 5 seconds to wait for MongoDB to initialize
                Thread.sleep(5000);
                // Connect to MongoDB container on the Docker bridge network
                mongoClient = new MongoClient("mongo-dbserver", 27017);
                System.out.println("Successfully connected");
                break;
            }
            catch (InterruptedException ie)
            {
                System.out.println("Thread interrupted? Should not happen.");
            }
            catch (Exception e)
            {
                System.out.println("Failed to connect to database attempt " + (i + 1));
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * Disconnect from the MongoDB database.
     */
    public void disconnect()
    {
        if (mongoClient != null)
        {
            mongoClient.close();
            System.out.println("Closed connection to database.");
        }
    }

    public static void main(String[] args)
    {
        App a = new App();

        // Connect to database
        a.connect();

        if (a.mongoClient != null)
        {
            // Get database
            MongoDatabase database = a.mongoClient.getDatabase("mydb");

            // Get collection
            MongoCollection<Document> collection = database.getCollection("test");

            // Create document
            Document doc = new Document("name", "Kevin Sim")
                    .append("class", "DevOps")
                    .append("year", "2024")
                    .append("result", new Document("CW", 95).append("EX", 85));

            // Add document to collection
            collection.insertOne(doc);

            // Check document in collection
            Document myDoc = collection.find().first();
            if (myDoc != null)
            {
                System.out.println(myDoc.toJson());
            }

            // Disconnect from database
            a.disconnect();
        }
    }
}