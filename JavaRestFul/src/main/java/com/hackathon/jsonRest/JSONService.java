package com.hackathon.jsonRest;


import java.net.URI;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.UriBuilder;

import org.bson.Document;

import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.util.JSON;


@Path("json/fooInstance")
public class JSONService {

	@GET
	@Path("/get")
	@Produces(MediaType.APPLICATION_JSON)
	public FindIterable<Document> getFooInJSON() {
		
		MongoClient mongoCLient = new MongoClient();
		
		MongoDatabase db = mongoCLient.getDatabase("hackathon");
		
		return db.getCollection("markers").find();
	}
	
	@POST
	@Path("/post")
	public Response postData(@FormParam("latitud") String latitud, @FormParam("longitud") String longitud, @FormParam("threat-category") String threat) {
		
		String foo = "{ \"nombre\": { \"type\": \"literal\", \"value\": \"" + threat + "\" }, \"geo_long\": { \"type\": \"typed-literal\", \"value\": \"" + longitud + "\" }, \"geo_lat\": { \"type\": \"typed-literal\", \"value\": \"" + latitud + "\" } }";
		

		Document doc = Document.parse(foo);
		
		System.out.println(foo);
		
		MongoClient mongoCLient = new MongoClient();
		
		MongoDatabase db = mongoCLient.getDatabase("hackathon");
				
		db.getCollection("markers").insertOne(doc);
		
		URI uri = UriBuilder.fromUri("http://127.0.0.1:40667/index.html").build();
		
		return Response.seeOther(uri).build();

		
		
	}
	
}
