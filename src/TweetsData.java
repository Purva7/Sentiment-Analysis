import java.io.IOException;
import java.net.UnknownHostException;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;

import twitter4j.HashtagEntity;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

public class TweetsData {
	// private static final InputStream ByteArrayOutputStream = null;
		static String CONSUMER_KEY = "wtHK76lO5IltiioSyVgrV6jbV";
		static String CONSUMER_KEY_SECRET = "QEEJY3V2XFRhVlWKaNGfm2V04m1ETnJJAjW4ZPgmZfNO8mmVIj";
		static String accessToken = "3070374722-7sxntCL37m9e8HP5hZVHEDS6jhU2h03udfVaZBO";
		static String accessTokenSecret = "lPuhnXqJ1NPB9R9gzmMdP1RtwS3myhx0l4oaXsiZ3hxMy";

		public static void main(String[] args) throws TwitterException, IOException {

			Twitter twitter = new TwitterFactory().getInstance();
			// twitter.createFavorite(Long.parseLong(args[0]));

			twitter.setOAuthConsumer(CONSUMER_KEY, CONSUMER_KEY_SECRET);

			// here's the difference
			AccessToken oathAccessToken = new AccessToken(accessToken,

			accessTokenSecret);

			twitter.setOAuthAccessToken(oathAccessToken);

			Query query = new Query("#Iphone6");
			//query.setSince("2014-01-01");
			//query.setCount(1000);
			//query.setRpp(100);
			//query.setSince("2015-01-01");
			QueryResult result = twitter.search(query);

			try {
				MongoClient mongoClient = new MongoClient("localhost", 27017);
				DB db = mongoClient.getDB("tweetdb");
				System.out.println("Connect to database successfully");
				System.out.println("Connect to database successfully good");
				DBCollection coll = db.getCollection("mytweetcol7");
				System.out.println("Collection mycol selected successfully");
				int i = 0;
				twitter.favorites();
				for (Status status : result.getTweets()) {
					BasicDBObject doc = new BasicDBObject("title", "MongoDB")
							.append("records", i++)
							.append("Name", status.getUser().getScreenName())
							.append("Status", status.getText());

					coll.insert(doc);
				}
				System.out.println("Document inserted successfully");
			}

			catch (Exception e) {
				System.err.println(e.getClass().getName() + ": " + e.getMessage());
			}

		}

}
