package com.example.benjo.l8network1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class LoadText {
	private TextListener listener;
	private String stringUrl;
	private String stringEncoding;
	
	public LoadText(TextListener listener, String url, String encoding) {
		if(listener!=null) {
			this.listener = listener;
			this.stringUrl = url;
			this.stringEncoding = encoding;
			new Thread (new TextLoader()).start ();
		}
	}

	public void onPostExecute(String result) {
		listener.textLoaded(result);
	}

	public interface TextListener {
		public void textLoaded(String str);
	}

	private class TextLoader implements Runnable {
		@Override
		public void run() {
			StringBuilder result = new StringBuilder();
			BufferedReader reader = null;
			try {
				URL url = new URL(stringUrl);
				URLConnection connection = url.openConnection();
				reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), stringEncoding));
				String txt;
				while((txt=reader.readLine())!=null)
					result.append(txt+"\n");
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					reader.close();
				} catch(Exception e) {}
			}
			onPostExecute(result.toString());
		}
	}
}
