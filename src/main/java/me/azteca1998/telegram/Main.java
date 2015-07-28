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

package me.azteca1998.telegram;

import java.io.IOException;
import java.net.URISyntaxException;

import org.json.JSONException;

import jline.console.ConsoleReader;
import me.azteca1998.telegram.bot.TelegramBot;
import me.azteca1998.telegram.bot.TelegramBotBuilder;
import me.azteca1998.telegram.bot.api.Message;
import me.azteca1998.telegram.bot.api.Update;
import me.azteca1998.telegram.bot.update.UpdateListener;

final class Main implements UpdateListener {

	private final ConsoleReader reader;
	private final TelegramBot bot;

	public Main() throws Exception {
		reader = new ConsoleReader();
		try {
			String apiToken = reader.readLine("API Token: ");
			int startAt = Integer.parseInt(reader.readLine("Start at: "));
			int timeout = Integer.parseInt(reader.readLine("Timeout: "));

			bot = new TelegramBotBuilder(apiToken, this, startAt).setGetUpdatesUpdater(timeout).build();
		} finally {
			reader.getTerminal().restore();
		}

		// User botUser = bot.getMe();

		bot.run();
	}

	public static void main(String[] args) throws Exception {
		new Main();
	}

	public void onUpdate(Update update) {
		try {
			Message message = update.getMessage();

			String line = "[" + (message.getChatRoom().isPrivateConversation() ? "User" : "Group") + " "
					+ message.getChatRoom().getId() + "] ";
			switch (message.getMessageContent().getMessageType()) {
			case Audio:
				break;
			case Contact:
				break;
			case Document:
				break;
			case GroupCreation:
				break;
			case Location:
				break;
			case Photo:
				break;
			case PhotoDelete:
				break;
			case PhotoUpdate:
				break;
			case Sticker:
				break;
			case Text:
				line = line + "(" + message.getSender().getFirstName() + " "
						+ Integer.toString(message.getSender().getId()) + ") ";
				line = line + "{Text} " + message.getMessageContent().asText();

				try {
					bot.sendMessage(message.getChatRoom(), "Hello world", null, message, null);
					line = line + " Sent!";
				} catch (JSONException e) {
					e.printStackTrace();
				} catch (UnsupportedOperationException e) {
					e.printStackTrace();
				} catch (URISyntaxException e) {
					e.printStackTrace();
				}
				break;
			case TitleUpdate:
				break;
			case UserJoin:
				break;
			case UserLeft:
				break;
			case Video:
				break;
			default:
				break;
			}
			reader.println(line);
			reader.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean onError(Exception error) {
		error.printStackTrace();
		return false;
	}

	public boolean onAPIError(String error) {
		System.err.println(error);
		return false;
	}

}
