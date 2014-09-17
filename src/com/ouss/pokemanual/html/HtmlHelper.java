package com.ouss.pokemanual.html;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import com.ouss.common.HttpHelper;

public class HtmlHelper {
	
	private String pokeListUrl ="http://wiki.52poke.com/wiki/%E7%A5%9E%E5%A5%87%E5%AE%9D%E8%B4%9D%E5%88%97%E8%A1%A8%EF%BC%88%E6%8C%89%E5%85%A8%E5%9B%BD%E5%9B%BE%E9%89%B4%E7%BC%96%E5%8F%B7%EF%BC%89/%E7%AE%80%E5%8D%95%E7%89%88";
	
	public HashMap<String, List<List<String>>> GetPokeList(){
		HashMap<String, List<List<String>>> pokeList = new HashMap<String, List<List<String>>>();
		
		HttpHelper httphelper = new HttpHelper();
		String html = httphelper.getHtml(pokeListUrl);
		
		HashMap<String, String> matchList = new HashMap<String, String>();
		try {
			Pattern regex = Pattern.compile("<h3><span.+?>\\s*?(.+?)\\s*?</span>\\s*?</h3>\\s*?</td>\\s*?</tr>([\\s\\S]+?)(<tr>\\s*?<td\\s+?colspan\\=\"4\">|</table>)");
			Matcher regexMatcher = regex.matcher(html);
			while (regexMatcher.find()) {
				matchList.put(regexMatcher.group(1), regexMatcher.group(2));
			} 
		} catch (PatternSyntaxException ex) {
			// Syntax error in the regular expression
		}
		
		if (!matchList.isEmpty()){
			for(Map.Entry<String, String> entry : matchList.entrySet()) {
				List<List<String>> groupList = new ArrayList<List<String>>();
				List<String> itemList = new ArrayList<String>();
				try {
					Pattern regex = Pattern.compile("<tr>\\s*?<td>\\s*?#(\\d+)\\s*?</td>\\s*?<td>\\s*?<a\\s+?href=\"(.+?)\".+?>\\s*?(.+)\\s*?</a>\\s*?</td>\\s*?<td>\\s*?<a.+?>\\s*?(.+)\\s*?</a>\\s*?</td>\\s*?<td>\\s*?<a.+?>\\s*?(.+)\\s*?</a>\\s*?</td>\\s*?</tr>");
					Matcher regexMatcher = regex.matcher(entry.getValue());
					while (regexMatcher.find()) {
						itemList.add(regexMatcher.group(1));
						itemList.add(regexMatcher.group(2));
						itemList.add(regexMatcher.group(3));
						itemList.add(regexMatcher.group(4));
						itemList.add(regexMatcher.group(5));
						
						groupList.add(itemList);
					}
					
					pokeList.put(entry.getKey(), groupList);
					
				} catch (PatternSyntaxException ex) {
					// Syntax error in the regular expression
				}
			}
		}
		
		return pokeList;
	}
}
