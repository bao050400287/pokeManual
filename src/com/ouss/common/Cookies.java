package com.ouss.common;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

/**
 * ��ͬ������Cookie�ļ�ֵ
 * 
 * @author SomeWind
 * 
 */
public class Cookies {

        private HashMap<String, String> hashMap;

        public Cookies() {
                hashMap = new HashMap<String, String>();
        }

        /**
         * ��� Cookies ��������� Cookie ��¼
         */
        public void clear() {
                hashMap.clear();
        }

        /**
         * ���� key ��ȡ��Ӧ�� Cookie ֵ
         * 
         * @param key
         *            Ҫ��ȡ�� Cookie ֵ�� key
         * @return ��������� key �򷵻� null
         */
        public String getCookie(String key) {
                return hashMap.get(key);
        }

        /**
         * �� Cookies ������һ�� Cookie
         * 
         * @param key
         *            Ҫ���õ� Cookie �� key
         * @param value
         *            Ҫ���õ� Cookie �� value
         */
        public void putCookie(String key, String value) {
                hashMap.put(key, value);
        }

        /**
         * �� Cookies �������ô���� cookies
         * 
         * @param cookies
         */
        public void putCookies(String cookies) {
                if (cookies == null)
                        return;

                String[] strCookies = cookies.split(";");
                for (int i = 0; i < strCookies.length; i++) {
                        for (int j = 0; j < strCookies[i].length(); j++) {
                                if (strCookies[i].charAt(j) == '=') {
                                        this.putCookie(
                                                        strCookies[i].substring(0, j),
                                                        strCookies[i].substring(j + 1,
                                                                        strCookies[i].length()));
                                }
                        }
                }
        }

        /**
         * ��ȡ Cookies ���ַ���
         */
        @Override
        public String toString() {
                // TODO Auto-generated method stub
                if (hashMap.isEmpty())
                        return "";

                Set<Entry<String, String>> set = hashMap.entrySet();
                StringBuilder sb = new StringBuilder(set.size() * 50);
                for (Entry<String, String> entry : set) {
                        sb.append(String.format("%s=%s;", entry.getKey(), entry.getValue()));
                }
                sb.delete(sb.length() - 1, sb.length());
                return sb.toString();
        }
}