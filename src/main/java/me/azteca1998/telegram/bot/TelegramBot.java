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

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.json.JSONException;
import org.json.JSONObject;

import me.azteca1998.telegram.TelegramAPIError;
import me.azteca1998.telegram.bot.api.Audio;
import me.azteca1998.telegram.bot.api.Document;
import me.azteca1998.telegram.bot.api.Message;
import me.azteca1998.telegram.bot.api.PhotoSize;
import me.azteca1998.telegram.bot.api.ReplyMarkup;
import me.azteca1998.telegram.bot.api.Sticker;
import me.azteca1998.telegram.bot.api.TelegramChat;
import me.azteca1998.telegram.bot.api.User;
import me.azteca1998.telegram.bot.api.UserProfilePhotos;
import me.azteca1998.telegram.bot.api.Video;
import me.azteca1998.telegram.bot.requester.Request;
import me.azteca1998.telegram.bot.update.Updater;

public final class TelegramBot implements Runnable {

	private final Updater updater;
	private final String apiToken;

	public static enum ChatAction {
		Typing, UploadingPhoto, RecordingVideo, UploadingVideo, RecordingAudio, UploadingAudio, UploadingDocument, FindingLocation
	}

	public TelegramBot(Updater updater, String apiToken) {
		this.updater = updater;
		this.apiToken = apiToken;
	}

	public void run() {
		updater.run();
	}

	public User getMe() throws URISyntaxException, JSONException, UnsupportedOperationException, IOException {
		JSONObject response = new Request(getURIFromMethod("getMe")).execute().getResultAsJSONObject();
		if (response.getBoolean("ok")) {
			return new User(response.getJSONObject("result"));
		} else {
			throw new TelegramAPIError(response.getString("description"));
		}
	}

	public Message sendMessage(TelegramChat chat, String text, Boolean disableWebPagePreview, Message replyToMessage,
			ReplyMarkup markup) throws JSONException, UnsupportedOperationException, IOException, URISyntaxException {
		if (chat == null) {
			throw new NullPointerException("The chat argument must not be null.");
		}
		if (text == null) {
			throw new NullPointerException("The text argument must not be null.");
		}
		JSONObject data = new JSONObject();
		data.put("chat_id", chat.getId());
		data.put("text", text);
		if (disableWebPagePreview != null) {
			data.put("disable_web_page_preview", disableWebPagePreview);
		}
		if (replyToMessage != null) {
			data.put("reply_to_message_id", replyToMessage.getMessageId());
		}
		if (markup != null) {
			data.put("reply_markup", markup.toJSONObject());
		}

		JSONObject response = new Request(getURIFromMethod("sendMessage")).execute(data).getResultAsJSONObject();
		if (response.getBoolean("ok")) {
			return new Message(response.getJSONObject("result"));
		} else {
			throw new TelegramAPIError(response.getString("description"));
		}
	}

	public Message forwardMessage(TelegramChat chat, Message source)
			throws JSONException, UnsupportedOperationException, IOException, URISyntaxException {
		if (chat == null) {
			throw new NullPointerException("The chat argument must not be null.");
		}
		if (source == null) {
			throw new NullPointerException("The source argument must not be null.");
		}
		JSONObject data = new JSONObject();
		data.put("chat_id", chat.getId());
		data.put("from_chat_id", source.getChatRoom().getId());
		data.put("message_id", source.getMessageId());

		JSONObject response = new Request(getURIFromMethod("forwardMessage")).execute(data).getResultAsJSONObject();
		if (response.getBoolean("ok")) {
			return new Message(response.getJSONObject("result"));
		} else {
			throw new TelegramAPIError(response.getString("description"));
		}
	}

	public Message sendPhoto(TelegramChat chat, File file, String caption, Message replyToMessage, ReplyMarkup markup)
			throws JSONException, UnsupportedOperationException, IOException, URISyntaxException {
		if (chat == null) {
			throw new NullPointerException("The chat argument must not be null.");
		}
		if (file == null) {
			throw new NullPointerException("The file argument must not be null.");
		}
		JSONObject data = new JSONObject();
		data.put("chat_id", chat.getId());
		if (caption != null) {
			data.put("caption", caption);
		}
		if (replyToMessage != null) {
			data.put("reply_to_message_id", replyToMessage.getMessageId());
		}
		if (markup != null) {
			data.put("reply_markup", markup.toJSONObject());
		}

		JSONObject response = new Request(getURIFromMethod("sendPhoto")).execute(data, "photo", file)
				.getResultAsJSONObject();
		if (response.getBoolean("ok")) {
			return new Message(response.getJSONObject("result"));
		} else {
			throw new TelegramAPIError(response.getString("description"));
		}
	}

	public Message sendPhoto(TelegramChat chat, PhotoSize file, String caption, Message replyToMessage,
			ReplyMarkup markup) throws JSONException, UnsupportedOperationException, IOException, URISyntaxException {
		if (chat == null) {
			throw new NullPointerException("The chat argument must not be null.");
		}
		if (file == null) {
			throw new NullPointerException("The file argument must not be null.");
		}
		JSONObject data = new JSONObject();
		data.put("chat_id", chat.getId());
		data.put("photo", file.getFileId());
		if (caption != null) {
			data.put("caption", caption);
		}
		if (replyToMessage != null) {
			data.put("reply_to_message_id", replyToMessage.getMessageId());
		}
		if (markup != null) {
			data.put("reply_markup", markup.toJSONObject());
		}

		JSONObject response = new Request(getURIFromMethod("sendPhoto")).execute(data).getResultAsJSONObject();
		if (response.getBoolean("ok")) {
			return new Message(response.getJSONObject("result"));
		} else {
			throw new TelegramAPIError(response.getString("description"));
		}
	}

	public Message sendAudio(TelegramChat chat, File file, String caption, Message replyToMessage, ReplyMarkup markup)
			throws JSONException, UnsupportedOperationException, IOException, URISyntaxException {
		if (chat == null) {
			throw new NullPointerException("The chat argument must not be null.");
		}
		if (file == null) {
			throw new NullPointerException("The file argument must not be null.");
		}
		JSONObject data = new JSONObject();
		data.put("chat_id", chat.getId());
		if (caption != null) {
			data.put("caption", caption);
		}
		if (replyToMessage != null) {
			data.put("reply_to_message_id", replyToMessage.getMessageId());
		}
		if (markup != null) {
			data.put("reply_markup", markup.toJSONObject());
		}

		JSONObject response = new Request(getURIFromMethod("sendAudio")).execute(data, "audio", file)
				.getResultAsJSONObject();
		if (response.getBoolean("ok")) {
			return new Message(response.getJSONObject("result"));
		} else {
			throw new TelegramAPIError(response.getString("description"));
		}
	}

	public Message sendAudio(TelegramChat chat, Audio file, String caption, Message replyToMessage,
			ReplyMarkup markup) throws JSONException, UnsupportedOperationException, IOException, URISyntaxException {
		if (chat == null) {
			throw new NullPointerException("The chat argument must not be null.");
		}
		if (file == null) {
			throw new NullPointerException("The file argument must not be null.");
		}
		JSONObject data = new JSONObject();
		data.put("chat_id", chat.getId());
		data.put("audio", file.getFileId());
		if (caption != null) {
			data.put("caption", caption);
		}
		if (replyToMessage != null) {
			data.put("reply_to_message_id", replyToMessage.getMessageId());
		}
		if (markup != null) {
			data.put("reply_markup", markup.toJSONObject());
		}

		JSONObject response = new Request(getURIFromMethod("sendAudio")).execute(data).getResultAsJSONObject();
		if (response.getBoolean("ok")) {
			return new Message(response.getJSONObject("result"));
		} else {
			throw new TelegramAPIError(response.getString("description"));
		}
	}
	
	public Message sendDocument(TelegramChat chat, File file, String caption, Message replyToMessage, ReplyMarkup markup)
			throws JSONException, UnsupportedOperationException, IOException, URISyntaxException {
		if (chat == null) {
			throw new NullPointerException("The chat argument must not be null.");
		}
		if (file == null) {
			throw new NullPointerException("The file argument must not be null.");
		}
		JSONObject data = new JSONObject();
		data.put("chat_id", chat.getId());
		if (caption != null) {
			data.put("caption", caption);
		}
		if (replyToMessage != null) {
			data.put("reply_to_message_id", replyToMessage.getMessageId());
		}
		if (markup != null) {
			data.put("reply_markup", markup.toJSONObject());
		}

		JSONObject response = new Request(getURIFromMethod("sendDocument")).execute(data, "document", file)
				.getResultAsJSONObject();
		if (response.getBoolean("ok")) {
			return new Message(response.getJSONObject("result"));
		} else {
			throw new TelegramAPIError(response.getString("description"));
		}
	}

	public Message sendDocument(TelegramChat chat, Document file, String caption, Message replyToMessage,
			ReplyMarkup markup) throws JSONException, UnsupportedOperationException, IOException, URISyntaxException {
		if (chat == null) {
			throw new NullPointerException("The chat argument must not be null.");
		}
		if (file == null) {
			throw new NullPointerException("The file argument must not be null.");
		}
		JSONObject data = new JSONObject();
		data.put("chat_id", chat.getId());
		data.put("document", file.getFileId());
		if (caption != null) {
			data.put("caption", caption);
		}
		if (replyToMessage != null) {
			data.put("reply_to_message_id", replyToMessage.getMessageId());
		}
		if (markup != null) {
			data.put("reply_markup", markup.toJSONObject());
		}

		JSONObject response = new Request(getURIFromMethod("sendDocument")).execute(data).getResultAsJSONObject();
		if (response.getBoolean("ok")) {
			return new Message(response.getJSONObject("result"));
		} else {
			throw new TelegramAPIError(response.getString("description"));
		}
	}

	public Message sendSticker(TelegramChat chat, File file, String caption, Message replyToMessage, ReplyMarkup markup)
			throws JSONException, UnsupportedOperationException, IOException, URISyntaxException {
		if (chat == null) {
			throw new NullPointerException("The chat argument must not be null.");
		}
		if (file == null) {
			throw new NullPointerException("The file argument must not be null.");
		}
		JSONObject data = new JSONObject();
		data.put("chat_id", chat.getId());
		if (caption != null) {
			data.put("caption", caption);
		}
		if (replyToMessage != null) {
			data.put("reply_to_message_id", replyToMessage.getMessageId());
		}
		if (markup != null) {
			data.put("reply_markup", markup.toJSONObject());
		}

		JSONObject response = new Request(getURIFromMethod("sendSticker")).execute(data, "sticker", file)
				.getResultAsJSONObject();
		if (response.getBoolean("ok")) {
			return new Message(response.getJSONObject("result"));
		} else {
			throw new TelegramAPIError(response.getString("description"));
		}
	}

	public Message sendSticker(TelegramChat chat, Sticker file, String caption, Message replyToMessage,
			ReplyMarkup markup) throws JSONException, UnsupportedOperationException, IOException, URISyntaxException {
		if (chat == null) {
			throw new NullPointerException("The chat argument must not be null.");
		}
		if (file == null) {
			throw new NullPointerException("The file argument must not be null.");
		}
		JSONObject data = new JSONObject();
		data.put("chat_id", chat.getId());
		data.put("sticker", file.getFileId());
		if (caption != null) {
			data.put("caption", caption);
		}
		if (replyToMessage != null) {
			data.put("reply_to_message_id", replyToMessage.getMessageId());
		}
		if (markup != null) {
			data.put("reply_markup", markup.toJSONObject());
		}

		JSONObject response = new Request(getURIFromMethod("sendSticker")).execute(data).getResultAsJSONObject();
		if (response.getBoolean("ok")) {
			return new Message(response.getJSONObject("result"));
		} else {
			throw new TelegramAPIError(response.getString("description"));
		}
	}
	
	public Message sendVideo(TelegramChat chat, File file, String caption, Message replyToMessage, ReplyMarkup markup)
			throws JSONException, UnsupportedOperationException, IOException, URISyntaxException {
		if (chat == null) {
			throw new NullPointerException("The chat argument must not be null.");
		}
		if (file == null) {
			throw new NullPointerException("The file argument must not be null.");
		}
		JSONObject data = new JSONObject();
		data.put("chat_id", chat.getId());
		if (caption != null) {
			data.put("caption", caption);
		}
		if (replyToMessage != null) {
			data.put("reply_to_message_id", replyToMessage.getMessageId());
		}
		if (markup != null) {
			data.put("reply_markup", markup.toJSONObject());
		}

		JSONObject response = new Request(getURIFromMethod("sendVideo")).execute(data, "video", file)
				.getResultAsJSONObject();
		if (response.getBoolean("ok")) {
			return new Message(response.getJSONObject("result"));
		} else {
			throw new TelegramAPIError(response.getString("description"));
		}
	}

	public Message sendVideo(TelegramChat chat, Video file, String caption, Message replyToMessage,
			ReplyMarkup markup) throws JSONException, UnsupportedOperationException, IOException, URISyntaxException {
		if (chat == null) {
			throw new NullPointerException("The chat argument must not be null.");
		}
		if (file == null) {
			throw new NullPointerException("The file argument must not be null.");
		}
		JSONObject data = new JSONObject();
		data.put("chat_id", chat.getId());
		data.put("video", file.getFileId());
		if (caption != null) {
			data.put("caption", caption);
		}
		if (replyToMessage != null) {
			data.put("reply_to_message_id", replyToMessage.getMessageId());
		}
		if (markup != null) {
			data.put("reply_markup", markup.toJSONObject());
		}

		JSONObject response = new Request(getURIFromMethod("sendVideo")).execute(data).getResultAsJSONObject();
		if (response.getBoolean("ok")) {
			return new Message(response.getJSONObject("result"));
		} else {
			throw new TelegramAPIError(response.getString("description"));
		}
	}

	public Message sendLocation(TelegramChat chat, float latitude, float longitude, Message replyToMessage,
			ReplyMarkup markup) throws JSONException, UnsupportedOperationException, IOException, URISyntaxException {
		if (chat == null) {
			throw new NullPointerException("The chat argument must not be null.");
		}
		JSONObject data = new JSONObject();
		data.put("chat_id", chat.getId());
		data.put("latutide", latitude);
		data.put("longitude", longitude);
		if (replyToMessage != null) {
			data.put("reply_to_message_id", replyToMessage.getMessageId());
		}
		if (markup != null) {
			data.put("reply_markup", markup.toJSONObject());
		}

		JSONObject response = new Request(getURIFromMethod("sendLocation")).execute(data).getResultAsJSONObject();
		if (response.getBoolean("ok")) {
			return new Message(response.getJSONObject("result"));
		} else {
			throw new TelegramAPIError(response.getString("description"));
		}
	}

	public void sendChatAction(TelegramChat chat, ChatAction action)
			throws JSONException, UnsupportedOperationException, IOException, URISyntaxException {
		if (chat == null) {
			throw new NullPointerException("The chat argument must not be null.");
		}
		if (action == null) {
			throw new NullPointerException("The action argument must not be null.");
		}
		JSONObject data = new JSONObject();
		data.put("chat_id", chat.getId());
		switch (action) {
		case FindingLocation:
			data.put("action", "find_location");
			break;
		case RecordingAudio:
			data.put("action", "record_audio");
			break;
		case RecordingVideo:
			data.put("action", "record_video");
			break;
		case Typing:
			data.put("action", "typing");
			break;
		case UploadingAudio:
			data.put("action", "upload_audio");
			break;
		case UploadingDocument:
			data.put("action", "upload_document");
			break;
		case UploadingPhoto:
			data.put("action", "upload_photo");
			break;
		case UploadingVideo:
			data.put("action", "upload_video");
			break;
		default:
			break;
		}

		JSONObject response = new Request(getURIFromMethod("sendChatAction")).execute(data).getResultAsJSONObject();
		if (!response.getBoolean("ok")) {
			throw new TelegramAPIError(response.getString("description"));
		}
	}

	public UserProfilePhotos getUserProfilePhotos(User user, Integer offset, Integer limit)
			throws JSONException, UnsupportedOperationException, IOException, URISyntaxException {
		if (user == null) {
			throw new NullPointerException("The user argument must not be null.");
		}
		JSONObject data = new JSONObject();
		data.put("user_id", user.getId());
		if (offset != null) {
			data.put("offset", offset);
		}
		if (limit != null) {
			data.put("limit", limit);
		}

		JSONObject response = new Request(getURIFromMethod("getUserProfilePhotos")).execute(data)
				.getResultAsJSONObject();
		if (response.getBoolean("ok")) {
			return new UserProfilePhotos(response.getJSONObject("result"));
		} else {
			throw new TelegramAPIError(response.getString("description"));
		}
	}

	private URI getURIFromMethod(String method) throws URISyntaxException {
		return new URI("https://api.telegram.org/bot" + apiToken + "/" + method);
	}

}
