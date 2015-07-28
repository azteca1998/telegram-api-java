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

public class Contact implements TelegramObject {

	private final String phoneNumber;
	private final String firstName;
	private final String lastName;
	private final String userId;

	public Contact(JSONObject object) {
		phoneNumber = object.getString("phone_number");
		firstName = object.getString("first_name");
		lastName = object.optString("last_name", null);
		userId = object.optString("user_id", null);
	}

	public JSONObject toJSONObject() {
		throw new UnsupportedOperationException();
	}

	public final String getPhoneNumber() {
		return phoneNumber;
	}

	public final String getFirstName() {
		return firstName;
	}

	public final String getLastName() {
		return lastName;
	}

	public final String getUserId() {
		return userId;
	}

}
