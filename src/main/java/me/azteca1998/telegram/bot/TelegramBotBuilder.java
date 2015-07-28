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

package me.azteca1998.telegram.bot;

import java.net.URISyntaxException;

import me.azteca1998.telegram.bot.update.ManualUpdater;
import me.azteca1998.telegram.bot.update.UpdateListener;
import me.azteca1998.telegram.bot.update.Updater;

public final class TelegramBotBuilder {

	private final String apiToken;
	private final UpdateListener listener;
	private final int startAt;
	private GetUpdateMethod updateMethod = null;
	private Integer getUpdatesTimeout = null;

	private static enum GetUpdateMethod {
		GetUpdates, WebHook
	}

	public TelegramBotBuilder(String apiToken, UpdateListener listener, int startAt) {
		if (apiToken == null) {
			throw new NullPointerException("The apiToken argument must not be null.");
		}

		this.apiToken = apiToken;
		this.listener = listener;
		this.startAt = startAt;
	}

	public TelegramBotBuilder setWebHookUpdater() {
		this.updateMethod = GetUpdateMethod.WebHook;
		return this;
	}

	public TelegramBotBuilder setGetUpdatesUpdater() {
		return setGetUpdatesUpdater(1000);
	}

	public TelegramBotBuilder setGetUpdatesUpdater(int timeout) {
		this.updateMethod = GetUpdateMethod.GetUpdates;
		this.getUpdatesTimeout = timeout;
		return this;
	}

	public TelegramBot build() throws URISyntaxException {
		if (updateMethod == null) {
			throw new NullPointerException();
		}

		Updater updater = null;
		switch (updateMethod) {
		case GetUpdates:
			updater = new ManualUpdater(apiToken, listener, startAt, getUpdatesTimeout);
			break;
		case WebHook:
			throw new UnsupportedOperationException();
		}

		return new TelegramBot(updater, apiToken);
	}

}
