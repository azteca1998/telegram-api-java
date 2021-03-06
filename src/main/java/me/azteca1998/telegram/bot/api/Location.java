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

public final class Location implements TelegramObject {

	private final float longitude;
	private final float latitude;

	public Location(JSONObject object) {
		longitude = (float) object.getDouble("longitude");
		latitude = (float) object.getDouble("latitude");
	}

	public JSONObject toJSONObject() {
		throw new UnsupportedOperationException();
	}

	public final float getLongitude() {
		return longitude;
	}

	public final float getLatitude() {
		return latitude;
	}

}
