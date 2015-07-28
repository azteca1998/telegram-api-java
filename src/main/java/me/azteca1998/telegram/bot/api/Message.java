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

import me.azteca1998.telegram.bot.api.Message.MessageContent.MessageType;

public class Message implements TelegramObject {

	private final int messageId;
	private final User sender;
	private final int sendDate;
	private final TelegramChat chat;

	private final ForwardingFrom forwardingFrom;
	private final ReplyingTo replyingTo;

	private final MessageContent messageContent;

	public static class ForwardingFrom {

		private final User sender;
		private final int sendDate;

		public ForwardingFrom(final User sender, final int sendDate) {
			if (sender == null) {
				throw new NullPointerException("The sender argument must not be null.");
			}

			this.sender = sender;
			this.sendDate = sendDate;
		}

		public final User getSender() {
			return sender;
		}

		public final int getSendDate() {
			return sendDate;
		}

	}

	public static class ReplyingTo {

		private final Message message;

		public ReplyingTo(final Message message) {
			if (message == null) {
				throw new NullPointerException("The message argument must not be null.");
			}
			
			this.message = message;
		}

		public final Message getMessage() {
			return message;
		}

	}

	public static class MessageContent {

		private final Object data;
		private final MessageType type;
		private final String caption;

		public static enum MessageType {
			Text, Audio, Document, Photo, Sticker, Video, Contact, Location, UserJoin, UserLeft, TitleUpdate, PhotoUpdate, PhotoDelete, GroupCreation
		}

		public MessageContent(Object data, MessageType type, String caption) {
			if (data == null && type != MessageType.PhotoDelete && type != MessageType.GroupCreation) {
				throw new NullPointerException(
						"The data argument must not be null except for PhotoDelete and GroupCreation types.");
			}

			this.data = data;
			this.type = type;
			this.caption = caption;
		}

		public final String asText() {
			if (type == MessageType.Text) {
				return (String) data;
			} else {
				return null;
			}
		}

		public final Audio asAudio() {
			if (type == MessageType.Audio) {
				return (Audio) data;
			} else {
				return null;
			}
		}

		public final Document asDocument() {
			if (type == MessageType.Document) {
				return (Document) data;
			} else {
				return null;
			}
		}

		public final PhotoSize asPhoto() {
			if (type == MessageType.Photo) {
				return (PhotoSize) data;
			} else {
				return null;
			}
		}

		public final Sticker asSticker() {
			if (type == MessageType.Sticker) {
				return (Sticker) data;
			} else {
				return null;
			}
		}

		public final Video asVideo() {
			if (type == MessageType.Video) {
				return (Video) data;
			} else {
				return null;
			}
		}

		public final Contact asContact() {
			if (type == MessageType.Contact) {
				return (Contact) data;
			} else {
				return null;
			}
		}

		public final Location asLocation() {
			if (type == MessageType.Location) {
				return (Location) data;
			} else {
				return null;
			}
		}

		public final User asUserJoin() {
			if (type == MessageType.UserJoin) {
				return (User) data;
			} else {
				return null;
			}
		}

		public final User asUserLeft() {
			if (type == MessageType.UserLeft) {
				return (User) data;
			} else {
				return null;
			}
		}

		public final String asTitleUpdate() {
			if (type == MessageType.TitleUpdate) {
				return (String) data;
			} else {
				return null;
			}
		}

		public final PhotoSize[] asPhotoUpdate() {
			if (type == MessageType.PhotoUpdate) {
				return (PhotoSize[]) data;
			} else {
				return null;
			}
		}

		public final MessageType getMessageType() {
			return type;
		}

		public final String getCaption() {
			return caption;
		}

	}

	public Message(JSONObject object) {
		messageId = object.getInt("message_id");
		sender = new User(object.getJSONObject("from"));
		sendDate = object.getInt("date");
		{
			JSONObject object2 = object.getJSONObject("chat");
			if (object2.has("username")) {
				chat = new User(object2);
			} else {
				chat = new Group(object2);
			}
		}

		forwardingFrom = object.has("forward_from")
				? new ForwardingFrom(new User(object.getJSONObject("forward_from")), object.getInt("forward_date"))
				: null;
		replyingTo = object.has("reply_to_message")
				? new ReplyingTo(new Message(object.getJSONObject("reply_to_message"))) : null;

		if (object.has("text")) {
			messageContent = new MessageContent(object.get("text"), MessageType.Text,
					object.optString("caption", null));
		} else if (object.has("audio")) {
			messageContent = new MessageContent(object.get("audio"), MessageType.Audio,
					object.optString("caption", null));
		} else if (object.has("document")) {
			messageContent = new MessageContent(object.get("document"), MessageType.Document,
					object.optString("caption", null));
		} else if (object.has("photo")) {
			messageContent = new MessageContent(object.get("photo"), MessageType.Photo,
					object.optString("caption", null));
		} else if (object.has("sticker")) {
			messageContent = new MessageContent(object.get("sticker"), MessageType.Sticker,
					object.optString("caption", null));
		} else if (object.has("video")) {
			messageContent = new MessageContent(object.get("video"), MessageType.Video,
					object.optString("caption", null));
		} else if (object.has("contact")) {
			messageContent = new MessageContent(object.get("contact"), MessageType.Contact,
					object.optString("caption", null));
		} else if (object.has("location")) {
			messageContent = new MessageContent(object.get("location"), MessageType.Location,
					object.optString("caption", null));
		} else if (object.has("new_chat_participant")) {
			messageContent = new MessageContent(object.get("new_chat_participant"), MessageType.UserJoin,
					object.optString("caption", null));
		} else if (object.has("left_chat_participant")) {
			messageContent = new MessageContent(object.get("left_chat_participant"), MessageType.UserLeft,
					object.optString("caption", null));
		} else if (object.has("new_chat_title")) {
			messageContent = new MessageContent(object.get("new_chat_title"), MessageType.TitleUpdate,
					object.optString("caption", null));
		} else if (object.has("new_chat_photo")) {
			messageContent = new MessageContent(object.get("new_chat_photo"), MessageType.PhotoUpdate,
					object.optString("caption", null));
		} else if (object.has("delete_chat_photo")) {
			messageContent = new MessageContent(null, MessageType.PhotoDelete, object.optString("caption", null));
		} else if (object.has("group_chat_created")) {
			messageContent = new MessageContent(null, MessageType.GroupCreation, object.optString("caption", null));
		} else {
			messageContent = null;
		}
	}

	public JSONObject toJSONObject() {
		throw new UnsupportedOperationException();
	}

	public final int getMessageId() {
		return messageId;
	}

	public final User getSender() {
		return sender;
	}

	public final int getSendDate() {
		return sendDate;
	}

	public final TelegramChat getChatRoom() {
		return chat;
	}

	public final boolean isForwarding() {
		return forwardingFrom != null;
	}

	public final ForwardingFrom getForwardingFrom() {
		return forwardingFrom;
	}

	public final boolean isReplying() {
		return replyingTo != null;
	}

	public final ReplyingTo getReplyingTo() {
		return replyingTo;
	}

	public final MessageContent getMessageContent() {
		return messageContent;
	}

}
