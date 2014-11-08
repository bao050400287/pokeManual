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
	final static public String pokeIconInfo = "http://wiki.52poke.com/load.php?debug=false&lang=zh-hans&modules=ext.gadget.collapsible%2Cedit0%2Cmsp%2Ctabber%2Ctimer|mediawiki.legacy.commonPrint%2Cshared|mediawiki.skinning.interface|mediawiki.ui.button|skins.vector.styles&only=styles&skin=vector&*";
			//"http://wiki.52poke.com/load.php?debug=false&lang=zh-hans&modules=ext.gadget.collapsible%2Cedit0%2Cmsp%2Ctabber%2Ctimer%7Cmediawiki.legacy.commonPrint%2Cshared%7Cmediawiki.skinning.interface%7Cmediawiki.ui.button%7Cskins.vector.styles&only=styles&skin=vector&*";
	
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
				return pokeList;
			}
			
			if (!matchList.isEmpty()){
				List<String> groupList;
				for(Map.Entry<String, String> entry : matchList.entrySet()) {
					groupList = new ArrayList<String>();
					
					try {
						Pattern regex = Pattern.compile("<tr>\\s*?<td>\\s*?#(\\d+)\\s*?</td>\\s*?<td>\\s*?(<a.+?>\\s*?(.+)\\s*?</a>|(\\[\\[\\]\\]))\\s*?</td>\\s*?<td>\\s*?<a\\s+?href=\"(.+?)\".+?>\\s*?(.+)\\s*?</a>\\s*?</td>\\s*?<td>\\s*?<a.+?>\\s*?(.+)\\s*?</a>\\s*?</td>\\s*?</tr>");
						Matcher regexMatcher = regex.matcher(entry.getValue());
						while (regexMatcher.find()) {
							
							groupList.add(regexMatcher.group(1) 
									+ "," + regexMatcher.group(3) 
									+ "," + regexMatcher.group(5)
									+ "," + regexMatcher.group(6) 
									+ "," + regexMatcher.group(7));
						}
						
						pokeList.put(entry.getKey(), groupList);
						
					} catch (PatternSyntaxException ex) {
						// Syntax error in the regular expression
						return pokeList;
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
			//Pattern regex = Pattern.compile("sprite-icon-(\\w+)\\s*\\{\\s*background-position\\s*:\\s*(-?\\d+)px\\s*?(-?\\d+)px(\\s*;\\s*width\\s*:\\s*(\\d+)px(\\s*;\\s*height\\s*:\\s*(\\d+)px)?)?\\}");
			Pattern regex = Pattern.compile("sprite-icon-(\\w+)\\s*\\{\\s*background-position\\s*:\\s*(-?\\d+)(px)?\\s*(-?\\d+)(px)?\\s*(;width:(\\d+)px;height:(\\d+)px\\s*)?\\s*\\}");
			
			Matcher regexMatcher = regex.matcher(html);
			while (regexMatcher.find()) {
				sizeInfo = new ArrayList<String>();
				sizeInfo.add(regexMatcher.group(2));
				sizeInfo.add(regexMatcher.group(4));
				sizeInfo.add(regexMatcher.group(7));
				sizeInfo.add(regexMatcher.group(8));
				iconInfo.put(regexMatcher.group(1), sizeInfo);
			} 
		} catch (PatternSyntaxException ex) {
			// Syntax error in the regular expression
			return iconInfo;
		}
		
		return iconInfo;
	}
	
	public static List<String> GetPokeItemInfo (String html) {
		List<String> info = new ArrayList<String>();
		try {
			//Pattern regex = Pattern.compile("<table\\s*?class=\\\"roundy\\s*?bg-(.+?)\\s*?bd?-(.+?)\\s*?align-right\\s*?alignt-center\\\"\\s*?.+?>[\\s\\S]+?<td\\s*width=\"10%\">\\s*(<a.+?>\\s*<img.+?data-srcset=\\\".+?,\\s+?(.+?)\\s+?.+?\\\"\\s*/>\\s*</a>)?\\s*</td>[\\s\\S]+?<a.+?class=\"image\".*?>\\s*<img.+?data-srcset=\\\".+?,\\s+?(.+?)\\s+?.+?\\\"[\\s\\S]+?<a.+?>分类[\\s\\S]+?<td.+?>\\s*(.+?)\\s*</td>[\\s\\S]+?<a.+?>特性[\\s\\S]+?(<table[\\s\\S]+?</table>)[\\s\\S]+?<a.+?title=\\\"?经验值\\\"?>[\\s\\S]+?<td.+?>\\s*(.+?)\\s*</td>[\\s\\S]+?<a.+?>身高</a>[\\s\\S]+?<td.+?>\\s*(.+)\\s[\\s\\S]+?<a.+?>体重</a>[\\s\\S]+?<td.+?>\\s*(.+)\\s[\\s\\S]+?<a.+?>体形</a>[\\s\\S]+?<td.+?>.+?<a.+?title=\\\"(.+?)\\\".+?<img.+?data-url=\\\"(.+)\\\"\\s[\\s\\S]+?脚印[\\s\\S]+?<td.+?>\\s*<a.+?>(<img.+?data-url=\\\"(.+?)\\\")?[\\s\\S]+?<a.+?>图鉴颜色\\s*</a>[\\s\\S]+?<span.+?color:#(.*?);.+?>\\s*(.+?)\\s*</span>[\\s\\S]+?<a.+?>捕获度</a>[\\s\\S]+?<td.+>\\s*(.+?)<br\\s*/>.+?<span.+?>(.+?)</span>[\\s\\S]+?<a.+?>\\s*性别比例\\s*</a>[\\s\\S]+?<tr[\\s\\S]+?<tr[\\s\\S]+?<td.+?>\\s*(.+?)\\s*</td>[\\s\\S]+?培育\\s*</a>[\\s\\S]+?<td.+?>\\s*(.+?)\\s*</td>\\s*<td.+?>\\s*(.+?)\\s*<a.+?<small>\\((.+?)\\)[\\s\\S]+?取得基础得点\\s*</a>[\\s\\S]+?<tr>\\s*<td.+?>.+?<br\\s*/>(\\d+)\\s*</td>\\s*<td.+?>.+?<br\\s*/>(\\d+)\\s*</td>\\s*<td.+?>.+?<br\\s*/>(\\d+)\\s*</td>\\s*<td.+?>.+?<br\\s*/>(\\d+)\\s*</td>\\s*<td.+?>.+?<br\\s*/>(\\d+)\\s*</td>\\s*<td.+?>.+?<br\\s*/>(\\d+)\\s*</td>\\s*</tr>\\s*<tr>\\s*.+?</small>\\s*(\\d+)\\s*[\\s\\S]+?</small>\\s*(\\d+)\\s*");
			Pattern regex = Pattern.compile("<table\\s*?class=\"roundy\\s*?bg-(.+?)\\s*?bd?-(.+?)\\s*?align-right\\s*?alignt-center\"\\s*?.+?>[\\s\\S]+?<td\\s*width=\"10%\">\\s*(<a.+?>\\s*<img.+?data-srcset=\".+?,\\s+?(.+?)\\s+?.+?\"\\s*/>\\s*</a>)?\\s*</td>[\\s\\S]+?<a.+?class=\"image\".*?>\\s*<img.+?data-srcset=\".+?,\\s+?(.+?)\\s+?.+?\"[\\s\\S]+?<a.+?>分类[\\s\\S]+?<td.+?>\\s*(.+?)\\s*</td>[\\s\\S]+?<a.+?>特性[\\s\\S]+?(<table[\\s\\S]+?</table>)[\\s\\S]+?<a.+?title=\"?经验值\"?>[\\s\\S]+?<td.+?>\\s*(.+?)\\s*</td>[\\s\\S]+?<a.+?>身高</a>[\\s\\S]+?<td.+?>\\s*(.+)\\s[\\s\\S]+?<a.+?>体重</a>[\\s\\S]+?<td.+?>\\s*(.+)\\s[\\s\\S]+?<a.+?>体形</a>[\\s\\S]+?<td.+?>.+?<a.+?title=\"(.+?)\".+?<img.+?data-url=\"(.+)\"\\s[\\s\\S]+?脚印[\\s\\S]+?<td.+?>\\s*<a.+?>(<img.+?data-url=\"(.+?)\")?[\\s\\S]+?<a.+?>图鉴颜色\\s*</a>[\\s\\S]+?<span.+?color:#(.*?);.+?>\\s*(.+?)\\s*</span>[\\s\\S]+?<a.+?>捕获度</a>[\\s\\S]+?<td.+>\\s*(.+?)<br\\s*/>.+?<span.+?>(.+?)</span>[\\s\\S]+?<a.+?>\\s*性别比例\\s*</a>[\\s\\S]+?<tr[\\s\\S]+?<tr[\\s\\S]+?<td.+?>\\s*(.+?)\\s*</td>[\\s\\S]+?培育\\s*</a>[\\s\\S]+?<td.+?>\\s*(.+?)\\s*</td>\\s*<td.+?>\\s*(.+?)\\s*<a.+?<small>\\((.+?)\\)[\\s\\S]+?取得基础得点\\s*</a>[\\s\\S]+?<tr>\\s*<td.+?>.+?<br\\s*/>(\\d+)\\s*</td>\\s*<td.+?>.+?<br\\s*/>(\\d+)\\s*</td>\\s*<td.+?>.+?<br\\s*/>(\\d+)\\s*</td>\\s*<td.+?>.+?<br\\s*/>(\\d+)\\s*</td>\\s*<td.+?>.+?<br\\s*/>(\\d+)\\s*</td>\\s*<td.+?>.+?<br\\s*/>(\\d+)\\s*</td>\\s*</tr>\\s*<tr>\\s*.+?</small>\\s*(\\d+)\\s*</td>[\\s\\S]+?</small>\\s*(\\d+)\\s*<span[\\s\\S]+?title=\"(.+?)\"");
			
			Matcher regexMatcher = regex.matcher(html);
    	 	if (regexMatcher.find()) {
    	 		for(int i = 1; i<regexMatcher.groupCount(); i++){ 
    	 			info.add(regexMatcher.group(i));
    	 		}
    	 	}
		} catch (PatternSyntaxException ex) {
		 	// Syntax error in the regular expression
			return info;
		}
		
		return info;
	}
	
	public static HashMap<String, Float> GetPokeSexRatio(String html) {
		HashMap<String, Float> info = new HashMap<String, Float>();
		try {
			Pattern regex = Pattern.compile("<span.+?>(.+?)%\\s*(雌|雄)性");
			Matcher regexMatcher = regex.matcher(html);
			while (regexMatcher.find()) {
				info.put(regexMatcher.group(2), Float.parseFloat(regexMatcher.group(1)));
			}
		} catch (PatternSyntaxException ex) {
			// Syntax error in the regular expression
			return info;
		}
		
		return info;
	}
	
	public static String ReplaceHtmlTag(String html) {
		try {
			html = html.replaceAll("<.+?>", "");
		} catch (PatternSyntaxException ex) {
			// Syntax error in the regular expression
			return html;
		} catch (IllegalArgumentException ex) {
			// Syntax error in the replacement text (unescaped $ signs?)
			return html;
		} catch (IndexOutOfBoundsException ex) {
			// Non-existent backreference used the replacement text
			return html;
		}
		return html;
	}
	
	public static List<String> getPokeCharacter(String cStr) {
		List<String> matchList = new ArrayList<String>();
		try {
			Pattern regex = Pattern.compile("<td.+?>\\s*<a.+?>\\s*(.+?)\\s*</a>\\s*(/<a.+?>\\s*(.+?)\\s*</a>)?[\\s\\S]*?</td>");
			Matcher regexMatcher = regex.matcher(cStr);
			while (regexMatcher.find()) {
				String strCharacter = regexMatcher.group(1);
				if (regexMatcher.group(3) != null){
					strCharacter += "/" + regexMatcher.group(3);
				}
				matchList.add(strCharacter);
			} 
		} catch (PatternSyntaxException ex) {
			return matchList;
		}
		
		return matchList;
	}
}
