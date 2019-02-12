package weatherGrabbing;
import java.util.Scanner;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject; 
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;


public class WeatherGrabbing {
	private static HttpURLConnection connect;
	static String baseurl = "http://api.openweathermap.org/data/2.5/weather?q=";
	static String apiKey = "f49d16d1bd98e2e85425e79c3a85eabe";
	static String units = "&units=metric";
	static String cityName;

	public static String cityNameGet(){
		Scanner userInput = new Scanner(System.in);
		StringBuilder tempcity = new StringBuilder(); 
		System.out.print("Where are you? ");
		cityName = userInput.next();
//		while (userInput.next()!= "\n") {
//		String temp=userInput.next();
//		if (temp.length()!=2) {
//		.concat(temp);
//		}
		return cityName;
	}
	
	public static String structURL() {
		return baseurl + cityNameGet() + "&appid=" + apiKey+units;
	}
	
	public static String selectWeather(JSONObject jsonData) throws JSONException {
		String weatherinfo = jsonData.getJSONObject("main").get("temp").toString();
		return weatherinfo;
		
	}
	
	public static final void main(String[] args)throws MalformedURLException,
    ProtocolException, IOException, JSONException  {
		try {
		URL myurl = new URL(structURL());
		connect = 
				(HttpURLConnection) myurl.openConnection();
		connect.setRequestMethod("GET");
		
		 StringBuilder content;
		 try (BufferedReader in = new BufferedReader(
                 new InputStreamReader(connect.getInputStream()))) {

             String line;
             content = new StringBuilder();

             while ((line = in.readLine()) != null) {
                 content.append(line + "\n");
             }
         }
         JSONObject obj = new JSONObject(content.toString());
         System.out.println(content.toString());
         System.out.println(cityName+" weather: ");
         System.out.println(selectWeather(obj) + " degrees Fahrenheit");
		}finally {
			connect.disconnect();
		}
	}

}
