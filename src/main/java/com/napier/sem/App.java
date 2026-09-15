package com.napier.sem;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class App
{
    public static void main(String[] args)
    {
        // Connect to MongoDB on local system using try-with-resources
        try (MongoClient mongoClient = new MongoClient("localhost", 27000))
        {
            // Get database
            MongoDatabase database = mongoClient.getDatabase("mydb");

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
            if (myDoc != null) {
                System.out.println(myDoc.toJson());
            }
        }
    }
}