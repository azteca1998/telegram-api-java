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

public final class ReplyKeyboardMarkup implements TelegramObject, ReplyMarkup {

	private List<KeyboardRow> rows;
	private Boolean resizeKeyboard;
	private Boolean oneTimeKeyboard;
	private Boolean selective;

	public static class KeyboardRow {

		private List<String> buttons;

		public KeyboardRow() {
			buttons = new ArrayList<String>();
		}

		public final KeyboardRow addButton(String text) {
			buttons.add(text);
			return this;
		}

		public final List<String> getButtons() {
			return buttons;
		}

	}

	public ReplyKeyboardMarkup() {
		rows = new ArrayList<ReplyKeyboardMarkup.KeyboardRow>();
		resizeKeyboard = null;
		oneTimeKeyboard = null;
		selective = null;
	}

	public JSONObject toJSONObject() {
		JSONObject object = new JSONObject();

		{
			JSONArray rows = new JSONArray();
			for (KeyboardRow row : this.rows) {
				JSONArray buttons = new JSONArray();
				for (String button : row.getButtons()) {
					buttons.put(button);
				}
				rows.put(buttons);
			}
			object.put("keyboard", rows);
		}

		if (resizeKeyboard != null) {
			object.put("resize_keyboard", resizeKeyboard);
		}
		if (oneTimeKeyboard != null) {
			object.put("one_time_keyboard", oneTimeKeyboard);
		}
		if (selective != null) {
			object.put("selective", selective);
		}

		return object;
	}

	public ReplyKeyboardMarkup addRow(KeyboardRow row) {
		rows.add(row);
		return this;
	}

	public ReplyKeyboardMarkup setResizeKeyboard(Boolean resizeKeyboard) {
		this.resizeKeyboard = resizeKeyboard;
		return this;
	}

	public ReplyKeyboardMarkup setOneTimeKeyboard(Boolean oneTimeKeyboard) {
		this.oneTimeKeyboard = oneTimeKeyboard;
		return this;
	}

	public ReplyKeyboardMarkup setSelective(Boolean selective) {
		this.selective = selective;
		return this;
	}

}
