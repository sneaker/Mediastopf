/**
 * This file is part of JVector.
 * 
 * JVector is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * JVector is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with JVector.  If not, see <http://www.gnu.org/licenses/>.
 * 
 */

package ch.nomoresecrets.mediastopf.client.ui.dialogs;

import javax.swing.JOptionPane;

public class MessageDialog {
	
	/**
	 * show a information dialog
	 */
	public static void info(String title, String message) {
		JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);
	}
}
