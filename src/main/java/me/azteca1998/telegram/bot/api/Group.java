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

public final class Group extends TelegramChat {

	private final String title;

	protected Group(final JSONObject object) {
		super(object.getInt("id"));
		title = object.getString("title");
	}

	public JSONObject toJSONObject() {
		throw new UnsupportedOperationException();
	}

	public final String getTitle() {
		return title;
	}

	@Override
	public boolean isPrivateConversation() {
		return false;
	}

	@Override
	public boolean isGroupConversation() {
		return true;
	}

}
