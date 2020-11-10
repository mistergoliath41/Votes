package fr.mistergoliath.votes.utils;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import fr.mistergoliath.votes.annotations.APIInfos;

@APIInfos(baseUrl="https://www.rpg-paradize.com/?page=topsite&group=31&find=")
public class RpgAPI {
	
	private String serverName;
	private Document doc;
	private Elements elems;

	public RpgAPI(String serverName) {
		this.serverName = serverName;
	}
	
	public int getOut() throws IOException {
		this.doc = Jsoup.connect(getBaseUrl() + this.serverName).get();
		for (Element elem : doc.select(".top-info")) {
			if (elem.html().toLowerCase().contains(serverName)) {
				String s = elem.html().replace(" ", "");
				String rawOut = getBetween(s, "Out:", "-Rate:");
				if (rawOut != null) {
					return Integer.parseInt(rawOut);
				}
			}
		}
		return 0;
	}
	
	public static String getBetween(String msg, String word1, String word2) {
	    return msg.substring(msg.indexOf(word1) + word1.length(), msg.indexOf(word2));
	}
	
	public String getBaseUrl() {
		return RpgAPI.class.getAnnotation(APIInfos.class).baseUrl();
	}
	
}
