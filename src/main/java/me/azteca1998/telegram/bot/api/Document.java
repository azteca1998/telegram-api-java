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

package me.azteca1998.telegram.bot.api;

import org.json.JSONObject;

public final class Document extends File {

	private final PhotoSize thumbnail;
	private final String fileName;

	public Document(final JSONObject object) {
		super(object.getString("file_id"), object.optString("mime_type", null),
				object.has("file_size") ? object.getInt("file_size") : null);
		thumbnail = object.has("thumb") ? new PhotoSize(object.getJSONObject("thumb")) : null;
		fileName = object.optString("file_name", null);
	}

	public JSONObject toJSONObject() {
		throw new UnsupportedOperationException();
	}

	public final PhotoSize getThumbnail() {
		return thumbnail;
	}

	public final String getFileName() {
		return fileName;
	}

}
