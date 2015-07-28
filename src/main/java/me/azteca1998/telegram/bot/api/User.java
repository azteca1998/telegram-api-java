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

public final class User extends TelegramChat {

	private final String firstName;
	private final String lastName;
	private final String username;

	public User(final JSONObject object) {
		super(object.getInt("id"));
		firstName = object.getString("first_name");
		lastName = object.optString("last_name", null);
		username = object.optString("username", null);
	}

	public final JSONObject toJSONObject() {
		throw new UnsupportedOperationException();
	}

	public final String getFirstName() {
		return firstName;
	}

	public final String getLastName() {
		return lastName;
	}

	public final String getUsername() {
		return username;
	}

	@Override
	public final boolean isPrivateConversation() {
		return true;
	}

	@Override
	public final boolean isGroupConversation() {
		return false;
	}

}
