package com.ouss.common;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.*;
import org.apache.http.util.ByteArrayBuffer;
 
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.util.Log;
 
/**
 * ��������� http ��Դ�Ĺ�����
 */
public final class HttpHelper {
    public final static String TAG = "HttpHelper";
 
    private final static String CONTENT_TYPE = "application/x-www-form-urlencoded";
    private final static String ACCEPT = "*/*";
    private final static String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:32.0) Gecko/20100101 Firefox/32.0";
 
    /**
     * 1024 byte
     */
    private final static int BUFFER_LENGTH = 1024;
 
    private String referer;
    private Cookies cookies;
    private int timeout = 300000;
 
    public HttpHelper() {
        cookies = new Cookies();
    }
 
    /**
     * ��ȡ��ʱʱ�䣬���뵥λ��Ĭ��Ϊ300000���뼴5����
     *
     * @return
     */
    public int getTimeout() {
        return timeout;
    }
 
    /**
     * ���ó�ʱʱ�� ReadTimeOut �� ConnectTimeout ������Ϊ�ó�ʱʱ�䣬���뵥λ
     *
     * @param timeout
     */
    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }
 
    /**
     * ��ȡ Referer
     *
     * @return
     */
    public String getReferer() {
        return referer;
    }
 
    /**
     * ���� Referer
     *
     * @return
     */
    public void setReferer(String referer) {
        this.referer = referer;
    }
 
    /**
     * ��GET�����½�һ���̻߳�ȡ��ҳ�����뷽ʽΪ UTF-8����ʱ�������󷵻�null
     *
     * @param strUrl
     *            ��ҳURL��ַ
     * @param handler
     *            �������𱾴ε��õ��̷߳��ͽ����Ϣ
     * @param what
     *            handler�е�what���
     */
    public void getHtmlByThread(String strUrl, Handler handler, int what) {
        getHtmlByThread(strUrl, null, false, "UTF-8", handler, what);
    }
 
    /**
     * ��GET�����½�һ���̻߳�ȡ��ҳ����ʱ�������󷵻�null
     *
     * @param strUrl
     *            ��ҳURL��ַ
     * @param encoding
     *            ���뷽ʽ
     * @param handler
     *            �������𱾴ε��õ��̷߳��ͽ����Ϣ
     * @param what
     *            handler�е�what���
     */
    public void getHtmlByThread(String strUrl, String encoding,
            Handler handler, int what) {
        getHtmlByThread(strUrl, null, false, encoding, handler, what);
    }
 
    /**
     * ����GET��POST�����½�һ���̻߳�ȡ��ҳ����ʱ����null
     *
     * @param strUrl
     *            ��ҳURL��ַ
     * @param strPost
     *            POST ������
     * @param isPost
     *            �Ƿ� POST��true ��ΪPOST ,false ��Ϊ GET
     * @param encoding
     *            ���뷽ʽ
     * @param handler
     *            �������𱾴ε��õ��̷߳��ͽ����Ϣ
     * @param what
     *            handler�е�what���
     */
    public void getHtmlByThread(String strUrl, String strPost, boolean isPost,
            String encoding, Handler handler, int what) {
        if (handler == null)
            throw new NullPointerException("handler is null.");
 
        Thread t = new Thread(new Runner(strUrl, strPost, isPost, encoding,
                handler, what, Runner.TYPE_HTML));
        t.setDaemon(true);
        t.start();
    }
 
    /**
     * ��GET������ȡ��ҳ�����뷽ʽΪ UTF-8����ʱ�������󷵻�null
     *
     * @param strUrl
     *            ��ҳURL��ַ
     * @return ������ҳ���ַ���
     */
    public String getHtml(String strUrl) {
        return getHtml(strUrl, null, false, "UTF-8");
    }
 
    /**
     * ��GET������ȡ��ҳ����ʱ�������󷵻�null
     *
     * @param strUrl
     *            ��ҳURL��ַ
     * @param encoding
     *            ���뷽ʽ
     * @return ������ҳ���ַ���
     */
    public String getHtml(String strUrl, String encoding) {
        return getHtml(strUrl, null, false, encoding);
    }
 
    /**
     * ����GET��POST������ȡ��ҳ����ʱ����null
     *
     * @param strUrl
     *            ��ҳURL��ַ
     * @param strPost
     *            POST ������
     * @param isPost
     *            �Ƿ� POST��true ��ΪPOST ,false ��Ϊ GET
     * @param encoding
     *            ���뷽ʽ
     * @return ������ҳ���ַ���
     */
    public String getHtml(String strUrl, String strPost, boolean isPost,
            String encoding) {
        String ret = null;
        try {
            byte[] data = getHtmlBytes(strUrl, strPost, isPost, encoding);
            if (data != null)
                ret = new String(data, encoding);
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
        }
        return ret;
    }
 
    /**
     * ����GET��POST������ȡ�������ݣ���ʱ����null
     *
     * @param strUrl
     *            ��ҳURL��ַ
     * @param strPost
     *            POST ������
     * @param isPost
     *            �Ƿ�POST��true��ΪPOST,false��Ϊ GET
     * @param encoding
     *            ���뷽ʽ
     * @return ����bytes
     */
    public byte[] getHtmlBytes(String strUrl, String strPost, boolean isPost,
            String encoding) {
        byte[] ret = null;
        HttpURLConnection httpCon = null;
        InputStream is = null;
        try {
            URL url = new URL(strUrl);
            httpCon = (HttpURLConnection) url.openConnection();
            httpCon.setReadTimeout(timeout);
            httpCon.setConnectTimeout(timeout);
            httpCon.setUseCaches(false);
            httpCon.setInstanceFollowRedirects(true);
            httpCon.setRequestProperty("Referer", referer);
            httpCon.setRequestProperty("Content-Type", CONTENT_TYPE);
            httpCon.setRequestProperty("Accept", ACCEPT);
            httpCon.setRequestProperty("User-Agent", USER_AGENT);
            httpCon.setRequestProperty("Cookie", cookies.toString());
 
            if (isPost) {
                httpCon.setDoOutput(true);
                httpCon.setRequestMethod("POST");
                httpCon.connect();
 
                OutputStream os = null;
                try {
                    os = httpCon.getOutputStream();
                    os.write(URLEncoder.encode(strPost, encoding).getBytes());
                    os.flush();
                } finally {
                    if (os != null)
                        os.close();
                }
            }
 
            // ��ȡ����
            is = httpCon.getInputStream();
            ByteArrayBuffer baBuffer = null;
            byte[] buffer = new byte[BUFFER_LENGTH];
            int rNum = 0;
            // ����ȡ�����ݳ���С�� BUFFER_LENGTH��˵����ȡ��
            // ����С�� BUFFER_LENGTH �Ѿ�һ���Զ�ȡ���
            // ���ʱ�򲢲��ô��� ByteArrayBuffer
            //
            // ���ϲ�������
            // if ((rNum = is.read(buffer)) < BUFFER_LENGTH) {
            // ret = buffer;
            // } else {
            baBuffer = new ByteArrayBuffer(BUFFER_LENGTH << 1);
            // baBuffer.append(buffer, 0, BUFFER_LENGTH);
            while ((rNum = is.read(buffer)) != -1) {
                baBuffer.append(buffer, 0, rNum);
            }
            ret = baBuffer.toByteArray();
            // }
 
        } catch (Exception e) {
            // TODO Auto-generated catch block
            Log.e(TAG, e.getMessage() + ":" + e.getCause());
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
 
                }
            }
            // ���� Cookie
            if (httpCon != null) {
                cookies.putCookies(httpCon.getHeaderField("Set-Cookie"));
                // ���� referer
                referer = strUrl;
                httpCon.disconnect();
            }
        }
        return ret;
    }
 
    /**
     * �½�һ���̻߳�ȡһ����ҳͼƬ
     *
     * @param strUrl
     * @param handler
     *            �������𱾴ε��õ��̷߳��ͽ����Ϣ
     * @param what
     *            handler�е�what���
     */
    public void getBitmapByThread(String strUrl, Handler handler, int what) {
        if (handler == null)
            throw new NullPointerException("handler is null.");
 
        Thread t = new Thread(new Runner(strUrl, null, false, null, handler,
                what, Runner.TYPE_IMG));
        t.setDaemon(true);
        t.start();
    }
 
    /**
     * ��ȡһ����ҳͼƬ
     *
     * @param strUrl
     *            ��ҳͼƬ��URL��ַ
     * @return
     */
    public Bitmap getBitmap(String strUrl) {
        byte[] data = getHtmlBytes(strUrl, null, false, null);
        return BitmapFactory.decodeByteArray(data, 0, data.length);
    }
 
    private class Runner implements Runnable {
        public final static int TYPE_HTML = 1;
        public final static int TYPE_IMG = 2;
 
        private String strUrl;
        private String strPost;
        private boolean isPost;
        private String encoding;
        private Handler handler;
        private int what;
        private int type;
 
        public Runner(String strUrl, String strPost, boolean isPost,
                String encoding, Handler handler, int what, int type) {
            this.strUrl = strUrl;
            this.strPost = strPost;
            this.isPost = isPost;
            this.encoding = encoding;
            this.handler = handler;
            this.what = what;
            this.type = type;
        }
 
        @Override
        public void run() {
            // TODO Auto-generated method stub
            Object obj = null;
            switch (type) {
            case TYPE_HTML:
                obj = getHtml(strUrl, strPost, isPost, encoding);
                break;
            case TYPE_IMG:
                obj = getBitmap(strUrl);
                break;
            }
            synchronized (handler) {
                handler.sendMessage(handler.obtainMessage(what, obj));
            }
        }
 
    }
}