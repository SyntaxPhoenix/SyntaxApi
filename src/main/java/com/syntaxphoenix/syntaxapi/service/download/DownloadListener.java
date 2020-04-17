package com.syntaxphoenix.syntaxapi.service.download;

public interface DownloadListener {

	public default void onConnect(Download download) {}
	public default void onDisconnect(Download download, DisconnectReason reason) {}

	public default void onStart(Download download, String path, String location) {}
	public default void onFail(Download download, String path, String location) {}
	public default void onSuccess(Download download, String path, String location) {}

}
