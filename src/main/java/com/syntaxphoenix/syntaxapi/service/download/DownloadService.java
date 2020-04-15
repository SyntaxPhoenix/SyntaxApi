package com.syntaxphoenix.syntaxapi.service.download;

import java.io.File;
import java.io.FileOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.syntaxphoenix.syntaxapi.exceptions.DownloadFailedException;
import com.syntaxphoenix.syntaxapi.logging.ILogger;
import com.syntaxphoenix.syntaxapi.service.IService;
import com.syntaxphoenix.syntaxapi.service.IServiceValue;
import com.syntaxphoenix.syntaxapi.service.ServiceManager;
import com.syntaxphoenix.syntaxapi.service.SubscribeService;
import com.syntaxphoenix.syntaxapi.service.ValueType;
import com.syntaxphoenix.syntaxapi.utils.general.Status;
import com.syntaxphoenix.syntaxapi.utils.java.Reflections;
import com.syntaxphoenix.syntaxapi.utils.java.Streams;

public class DownloadService implements IService {

	private final ExecutorService executor;

	public DownloadService() {
		this.executor = Executors.newSingleThreadExecutor();
	}

	public DownloadService(ExecutorService executor) {
		this.executor = executor;
	}

	/*
	 * 
	 */

	@Override
	public String getId() {
		return "download";
	}

	@Override
	public Status execute(ServiceManager services) {

		Status status = Status.create();

		executor.submit(() -> {

			IServiceValue[] subscriptions = services.getSubscriptions(getOwner(), ValueType.METHOD);
			
			if(services.hasLogger()) {
				ILogger logger = services.getLogger();
				if(logger.getState().extendedInfo())
					logger.log("Found " + subscriptions.length + " Subscriptions");
			}

			for (IServiceValue subscription : subscriptions) {

				SubscribeService annotation = subscription.getAnnotation();

				try {

					if (!annotation.returnsObject()) {
						status.skip();
						Reflections.execute(subscription.getOwnerInstance(), subscription.asMethod());
						continue;
					}

					if (!annotation.returnType().isAssignableFrom(Download.class)) {
						status.skip();
						Reflections.execute(subscription.getOwnerInstance(), subscription.asMethod());
						continue;
					}

					Download download = (Download) Reflections.execute(subscription.getOwnerInstance(),
							subscription.asMethod());

					int timeout = download.getTimeout();
					
					String host = download.getHost();
					HashMap<String, String> paths = download.getPaths();

					if (paths.isEmpty())
						continue;

					URL url = new URL(host);

					URLConnection connection = url.openConnection();

					status.add(paths.size());

					if (connection instanceof HttpURLConnection) {
						HttpURLConnection http = (HttpURLConnection) connection;
						
						http.setReadTimeout(30000);
						http.setConnectTimeout(12000);
						http.setRequestMethod("GET");
						
						int code = http.getResponseCode();
						
						if (code != 200) {
							while (status.failed())
								;
							continue;
						}
						
					}

					Set<Entry<String, String>> entries = paths.entrySet();

					Throwable throwable;
					for (Entry<String, String> entry : entries) {
						if ((throwable = download(host, entry.getKey(), entry.getValue(), timeout)) == null) {
							status.success();
						} else {
							status.failed();

							if (services.hasLogger())
								services.getLogger()
										.log(new DownloadFailedException(
												"Failed to download file from host (" + host + ") with path ("
														+ entry.getKey() + ") to '" + entry.getValue() + "'",
												throwable));
						}
					}

				} catch (Throwable throwable) {
					if (services.hasLogger())
						services.getLogger().log(new DownloadFailedException(throwable));
				}

			}

			status.done();

		});

		return status;

	}

	private Throwable download(String host, String path, String location, int timeout) {

		try {

			URL url = new URL(host.endsWith("/") ? host + path : host + '/' + path);

			URLConnection connection = url.openConnection();

			connection.setConnectTimeout(12000);
			connection.setReadTimeout(timeout * 1000);

			if (connection instanceof HttpURLConnection) {
				HttpURLConnection http = (HttpURLConnection) connection;

				http.setRequestMethod("GET");

				int code = http.getResponseCode();

				if (code != 200) {
					return new DownloadFailedException("Got response code " + code + "!");
				}
			}

			byte[] bytes = Streams.toByteArray(connection.getInputStream());

			File file = new File(location);

			if (file.exists()) {
				if (!file.canWrite())
					return new DownloadFailedException("No permission to write data to file!");
			} else {
				if (file.getParent() != null) {
					File parent = file.getParentFile();
					if (!parent.exists())
						parent.mkdirs();
				}
				file.createNewFile();
			}

			FileOutputStream stream = new FileOutputStream(file);

			stream.write(bytes);

			stream.flush();
			stream.close();

			return null;

		} catch (Throwable throwable) {
			return throwable;
		}

	}

}
