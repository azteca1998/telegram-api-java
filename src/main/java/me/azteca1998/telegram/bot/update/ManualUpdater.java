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

package me.azteca1998.telegram.bot.update;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import me.azteca1998.telegram.bot.api.Update;
import me.azteca1998.telegram.bot.requester.Request;

public final class ManualUpdater extends Updater {

	private final URI uri;
	private final int timeout;

	public ManualUpdater(String apiToken, UpdateListener listener, int startAt, int timeout) throws URISyntaxException {
		super(listener, startAt);

		uri = new URI("https://api.telegram.org/bot" + apiToken + "/getUpdates");
		this.timeout = timeout;
	}

	public void run() {
		while (true) {
			try {
				JSONObject data = new JSONObject();
				data.put("offset", lastUpdateId);

				JSONObject response = new Request(uri).execute(data).getResultAsJSONObject();
				if (response.getBoolean("ok")) {
					JSONArray updates = response.getJSONArray("result");
					int newUpdateId = lastUpdateId;
					for (int i = 0; i < updates.length(); i++) {
						Update update = new Update(updates.getJSONObject(i));
						if (update.getUpdateId() > newUpdateId) {
							newUpdateId = update.getUpdateId();

							listener.onUpdate(update);
						}
					}
					lastUpdateId = newUpdateId;
				} else {
					if (listener.onAPIError(response.getString("description"))) {
						break;
					}
				}

				Thread.sleep(timeout);
			} catch (ClientProtocolException e) {
				if (listener.onError(e)) {
					break;
				}
			} catch (JSONException e) {
				if (listener.onError(e)) {
					break;
				}
			} catch (IOException e) {
				if (listener.onError(e)) {
					break;
				}
			} catch (InterruptedException e) {
				break;
			}
		}
	}

}
