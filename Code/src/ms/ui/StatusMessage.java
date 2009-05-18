package ms.ui;

import ms.utils.I18NManager;


/**
 * statusmessages
 */
public class StatusMessage {
	
	private static I18NManager manager = I18NManager.getManager();

	public static enum StatusType {
		RUNMESSAGE, SENDMESSAGE, RELOADMESSAGE, CANCELMESSAGE
	}

	public static String getMessage(StatusType type) {
		switch (type) {
		case RUNMESSAGE:
			return manager.getString("StatusMessage.taskexecuted");
		case SENDMESSAGE:
			return manager.getString("StatusMessage.sendmessage");
		case RELOADMESSAGE:
			return manager.getString("StatusMessage.reloadmessage");
		case CANCELMESSAGE:
			return manager.getString("StatusMessage.cancelmessage");
		default:
			return manager.getString("StatusMessage.copyright");
		}
	}
}
