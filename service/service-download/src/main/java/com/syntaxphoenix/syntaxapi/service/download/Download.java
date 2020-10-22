package com.syntaxphoenix.syntaxapi.service.download;

import java.util.ArrayList;
import java.util.HashMap;

public class Download {

	private final String host;
	private final HashMap<String, String> paths = new HashMap<>();
	private final ArrayList<DownloadListener> listeners = new ArrayList<>();

	private int timeout = 30;

	public Download(String host) {
		this.host = host;
	}

	/*
	 * 
	 */

	public void add(String path, String location) {
		if (!paths.containsKey(path))
			paths.put(path, location);
	}

	public void set(String path, String location) {
		paths.put(path, location);
	}

	public void remove(String path) {
		paths.remove(path);
	}

	/*
	 * 
	 */

	public void addListener(DownloadListener listener) {
		if (listeners.contains(listener))
			return;
		listeners.add(listener);
	}

	public void removeListener(DownloadListener listener) {
		listeners.remove(listener);
	}

	public ArrayList<DownloadListener> getListeners() {
		return listeners;
	}

	/*
	 * 
	 */

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public int getTimeout() {
		return timeout;
	}

	/*
	 * 
	 */

	public String getHost() {
		return host;
	}

	public HashMap<String, String> getPaths() {
		return paths;
	}

}
