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

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public final class UserProfilePhotos implements TelegramObject {

	private final int count;
	private final List<List<PhotoSize>> photos;

	public UserProfilePhotos(JSONObject object) {
		count = object.getInt("total_count");
		photos = new ArrayList<List<PhotoSize>>();
		JSONArray photos = object.getJSONArray("photos");
		for (int i = 0; i < photos.length(); i++) {
			List<PhotoSize> photoSizes = new ArrayList<PhotoSize>();
			JSONArray jsonPhotoSizes = photos.getJSONArray(i);
			for (int j = 0; j < jsonPhotoSizes.length(); j++) {
				photoSizes.add(new PhotoSize(jsonPhotoSizes.getJSONObject(j)));
			}
			this.photos.add(photoSizes);
		}
	}

	public final JSONObject toJSONObject() {
		throw new UnsupportedOperationException();
	}

	public final int getPhotosCount() {
		return count;
	}

	public final List<List<PhotoSize>> getPhotos() {
		return photos;
	}

}
