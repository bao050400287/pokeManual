package com.ouss.pokemanual.html;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class HtmlHelper {
	
	final static public String pokeListUrl ="http://wiki.52poke.com/wiki/%E7%A5%9E%E5%A5%87%E5%AE%9D%E8%B4%9D%E5%88%97%E8%A1%A8%EF%BC%88%E6%8C%89%E5%85%A8%E5%9B%BD%E5%9B%BE%E9%89%B4%E7%BC%96%E5%8F%B7%EF%BC%89/%E7%AE%80%E5%8D%95%E7%89%88";
	final static public String pokeIconInfo = "http://wiki.52poke.com/load.php?debug=false&lang=zh-hans&modules=ext.gadget.collapsible%2Cedit0%2Cmsp%2Ctabber%2Ctimer%7Cmediawiki.legacy.commonPrint%2Cshared%7Cmediawiki.skinning.interface%7Cmediawiki.ui.button%7Cskins.vector.styles&only=styles&skin=vector&*";
	
	public LinkedHashMap<String, List<String>> GetPokeList(String html){
		LinkedHashMap<String, List<String>> pokeList = new LinkedHashMap<String, List<String>>();
		
		if (html.trim() != "") { 
			LinkedHashMap<String, String> matchList = new LinkedHashMap<String, String>();
			try {
				Pattern regex = Pattern.compile("<h3><span.+?>\\s*?(.+?)\\s*?</span>\\s*?</h3>\\s*?</td>\\s*?</tr>([\\s\\S]+?)(<tr>\\s*?<td\\s+?colspan\\=\"4\">|</table>)");
				Matcher regexMatcher = regex.matcher(html);
				while (regexMatcher.find()) {
					String group1  = regexMatcher.group(1);
					matchList.put(group1, regexMatcher.group(2));
				} 
			} catch (PatternSyntaxException ex) {
				// Syntax error in the regular expression
			}
			
			if (!matchList.isEmpty()){
				List<String> groupList;
				List<String> itemList;
				for(Map.Entry<String, String> entry : matchList.entrySet()) {
					groupList = new ArrayList<String>();
					
					try {
						Pattern regex = Pattern.compile("<tr>\\s*?<td>\\s*?#(\\d+)\\s*?</td>\\s*?<td>\\s*?(<a.+?>\\s*?(.+)\\s*?</a>|(\\[\\[\\]\\]))\\s*?</td>\\s*?<td>\\s*?<a\\s+?href=\"(.+?)\".+?>\\s*?(.+)\\s*?</a>\\s*?</td>\\s*?<td>\\s*?<a.+?>\\s*?(.+)\\s*?</a>\\s*?</td>\\s*?</tr>");
						Matcher regexMatcher = regex.matcher(entry.getValue());
						while (regexMatcher.find()) {
							itemList = new ArrayList<String>();
							itemList.add(regexMatcher.group(1));
							itemList.add(regexMatcher.group(3));
							itemList.add(regexMatcher.group(5));
							itemList.add(regexMatcher.group(6));
							itemList.add(regexMatcher.group(7));
							
							groupList.add(itemList.toString());
						}
						
						pokeList.put(entry.getKey(), groupList);
						
					} catch (PatternSyntaxException ex) {
						// Syntax error in the regular expression
					}
				}
			}
		}
		
		return pokeList;
	}
	
	public HashMap<String, List<String>> GetPokeIconInfo (String html) {
		HashMap<String, List<String>> iconInfo = new HashMap<String, List<String>>();
		List<String> sizeInfo;
		try {
			Pattern regex = Pattern.compile("sprite-icon-(\\w+)\\s*\\{\\s*background-position\\s*:\\s*(-?\\d+)px\\s*?(-?\\d+)px(\\s*;\\s*width\\s*:\\s*(\\d+)px(\\s*;\\s*height\\s*:\\s*(\\d+)px)?)?\\}");
			Matcher regexMatcher = regex.matcher(html);
			while (regexMatcher.find()) {
				sizeInfo = new ArrayList<String>();
				sizeInfo.add(regexMatcher.group(2));
				sizeInfo.add(regexMatcher.group(3));
				sizeInfo.add(regexMatcher.group(5));
				sizeInfo.add(regexMatcher.group(7));
				iconInfo.put(regexMatcher.group(1), sizeInfo);
			} 
		} catch (PatternSyntaxException ex) {
			// Syntax error in the regular expression
		}
		
		return iconInfo;
	}
}
