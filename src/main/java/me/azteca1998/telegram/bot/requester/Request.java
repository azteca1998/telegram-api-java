/* 
 * This file is part of telegram-api.
 * 
 * Telegram-api is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.
 * 
 * Telegram-api is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with Telegram-api.  If not, see http://www.gnu.org/licenses/.
 */

package me.azteca1998.telegram.bot.requester;

import java.io.File;
import java.io.IOException;
import java.net.URI;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import me.azteca1998.telegram.TelegramAPIError;
import me.azteca1998.telegram.bot.api.TelegramObject;

public class Request {

	private final URI uri;
	JSONTokener tokener;
	CloseableHttpClient client;

	public Request(final URI uri) {
		if (uri == null) {
			throw new NullPointerException("The uri argument must not be null.");
		}

		this.uri = uri;
		tokener = null;
		client = null;
	}

	public Request execute() throws JSONException, UnsupportedOperationException, IOException {
		return execute(null, null, (File) null);
	}

	public Request execute(TelegramObject data) throws JSONException, UnsupportedOperationException, IOException {
		return execute(data.toJSONObject(), null, (File) null);
	}

	public Request execute(JSONObject data) throws JSONException, UnsupportedOperationException, IOException {
		return execute(data, null, (File) null);
	}

	public Request execute(JSONObject data, String bodyKey, File uploadData)
			throws JSONException, UnsupportedOperationException, IOException {
		if (tokener != null || client != null) {
			throw new RuntimeException();
		}

		client = HttpClientBuilder.create().build();

		CloseableHttpResponse response = null;
		if (data == null) {
			response = client.execute(new HttpGet(uri));
		} else {
			HttpPost post = new HttpPost(uri);

			MultipartEntityBuilder entityBuilder = MultipartEntityBuilder.create();
			if (bodyKey != null && uploadData != null) {
				entityBuilder.addPart(bodyKey, new FileBody(uploadData));
			}
			for (String key : JSONObject.getNames(data)) {
				entityBuilder.addTextBody(key, data.get(key).toString());
			}

			post.setEntity(entityBuilder.build());

			response = client.execute(post);
		}

		int statusCode = response.getStatusLine().getStatusCode();
		if (statusCode != 200) {
			JSONObject errorData = null;
			try {
				errorData = new JSONObject(new JSONTokener(response.getEntity().getContent()));
			} catch (JSONException e) {
			}
			throw new TelegramAPIError(errorData != null ? errorData.getString("description")
					: ("HTTP Status Code " + Integer.toString(statusCode)));
		} else {
			tokener = new JSONTokener(response.getEntity().getContent());
		}
		return this;
	}

	public JSONObject getResultAsJSONObject() {
		JSONObject object = new JSONObject(tokener);
		try {
			client.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return object;
	}

	public JSONArray getResultAsJSONArray() {
		JSONArray object = new JSONArray(tokener);
		try {
			client.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return object;
	}

	public URI getURI() {
		return uri;
	}

}
