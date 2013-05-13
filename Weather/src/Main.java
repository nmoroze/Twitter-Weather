import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import twitter4j.GeoLocation;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Tweet;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.conf.ConfigurationBuilder;

public class Main extends JPanel{

	/**
	 * @param args
	 */
	
	static ArrayList<Double> quakeLatitudes=new ArrayList<Double>();
	static ArrayList<Double> quakeLongitudes=new ArrayList<Double>();
	static ArrayList<Double> thunderLatitudes=new ArrayList<Double>();
	static ArrayList<Double> thunderLongitudes=new ArrayList<Double>();
	static ArrayList<Double> rainLatitudes=new ArrayList<Double>();
	static ArrayList<Double> rainLongitudes=new ArrayList<Double>();
	   public void paint(Graphics g) {

	        Graphics2D g2d = (Graphics2D) g;
	        BufferedImage img = null;
	        try {
	            img = ImageIO.read(new File("res/latlong2.gif"));
	        } catch (IOException e) {
	        }
	        int x,y;
	        double lat,lon;
	        Image map = img.getScaledInstance(360, 180, Image.SCALE_SMOOTH);

	        ImageObserver observer = null;
	        g2d.drawImage(map, 0, 0, observer);
	        g2d.setPaint(Color.red);
	        for(int i=0;i<quakeLatitudes.size();i++)
	        {
	        	lat=quakeLatitudes.get(i);
	        	lon=quakeLongitudes.get(i);
	        	x = (int) lon+180;
	        	y = (int) ((int) 90-lat);
	        	g2d.drawOval(x, y, 5, 5);
	        }
	        g2d.setPaint(Color.yellow);
	        for(int i=0;i<thunderLatitudes.size();i++)
	        {
	        	lat=thunderLatitudes.get(i);
	        	lon=thunderLongitudes.get(i);
	        	x = (int) lon+180;
	        	y = (int) ((int) 90-lat);
	        	g2d.drawOval(x, y, 5, 5);
	        }
	        g2d.setPaint(Color.blue);
	        for(int i=0;i<rainLatitudes.size();i++)
	        {
	        	lat=rainLatitudes.get(i);
	        	lon=rainLongitudes.get(i);
	        	x = (int) lon+180;
	        	y = (int) ((int) 90-lat);
	        	g2d.drawOval(x, y, 5, 5);
	        }
	    }
	public static void main(String[] args) {
		//while(true){
		 ConfigurationBuilder cb = new ConfigurationBuilder();
		   cb.setOAuthConsumerKey("Insert your key here");
		  cb.setOAuthConsumerSecret("Insert your secret key here");
		  cb.setOAuthAccessToken("Insert your access token here");
		  cb.setOAuthAccessTokenSecret("Insert your secret access token here");
		  //Make the twitter object and prepare the query
		  Twitter twitter = new TwitterFactory(cb.build()).getInstance();
		  Query query = new Query("#earthquake");
		  query.setRpp(1000000);
		  out("Earthquakes: ");
		 
		  try {
			    QueryResult result = twitter.search(query);
			    ArrayList<?> tweets = (ArrayList<?>) result.getTweets();
			 
			    for (int i = 0; i < tweets.size(); i++) {
			      Tweet t = (Tweet) tweets.get(i);
			   
			      GeoLocation location = t.getGeoLocation();

			      if(location != null)
			      {
				      double latitude = location.getLatitude();
				      double longitude = location.getLongitude();
				      String loc = "";
				      loc+=String.valueOf(latitude);
				      loc+=",";
				      loc+=String.valueOf(longitude);
				      out(loc);
				      quakeLatitudes.add(latitude);
				      quakeLongitudes.add(longitude);
			      }
			    };
			  }
			  catch (TwitterException te) {
			    out("Couldn't connect: " + te);
			  };
			  query = new Query("#thunder");
			  query.setRpp(1000000);
			  out("Thunder: ");

			  try {
				    QueryResult result = twitter.search(query);
				    ArrayList<?> tweets = (ArrayList<?>) result.getTweets();
				 
				    for (int i = 0; i < tweets.size(); i++) {
				      Tweet t = (Tweet) tweets.get(i);
				   
				      GeoLocation location = t.getGeoLocation();

				      if(location != null)
				      {
					      double latitude = location.getLatitude();
					      double longitude = location.getLongitude();
					      String loc = "";
					      loc+=String.valueOf(latitude);
					      loc+=",";
					      loc+=String.valueOf(longitude);
					      out(loc);
					      thunderLatitudes.add(latitude);
					      thunderLongitudes.add(longitude);
				      }
				    };
				    
				  }
				  catch (TwitterException te) {
				    out("Couldn't connect: " + te);
				  };
				  
				  query = new Query("#raining");
				  query.setRpp(1000000);
				  out("Rain: ");

				  try {
					    QueryResult result = twitter.search(query);
					    ArrayList<?> tweets = (ArrayList<?>) result.getTweets();
					 
					    for (int i = 0; i < tweets.size(); i++) {
					      Tweet t = (Tweet) tweets.get(i);
					      User u = twitter.showUser(t.getFromUserId());
					      GeoLocation location = t.getGeoLocation();
					      //GeoLocation location = u.getLocation();
					      if(location != null)
					      {
						      double latitude = location.getLatitude();
						      double longitude = location.getLongitude();
						      String loc = "";
						      loc+=String.valueOf(latitude);
						      loc+=",";
						      loc+=String.valueOf(longitude);
						      out(loc);
						      rainLatitudes.add(latitude);
						      rainLongitudes.add(longitude);
						      out(u.getLocation());
					      }
					    }
				  }
					  catch (TwitterException te) {
						    out("Couldn't connect: " + te);
						  };
				  
			    JFrame frame = new JFrame("Weather");
		        frame.add(new Main());
		        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		        frame.setSize(360, 180);
		        frame.setLocationRelativeTo(null);
		        frame.setVisible(true);
		}
	//}
	public static void out(String msg)
	{
		System.out.println(msg);
	}
}
