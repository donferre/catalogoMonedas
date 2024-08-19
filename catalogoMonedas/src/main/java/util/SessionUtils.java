package util;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;

public class SessionUtils {

	public static boolean checkSession() {
		System.out.println("Ejecutando método static checkSession()...");
		/*
		 * Validamos que el user que enviamos desde la clase login no este null
		 */
		if (Sessions.getCurrent().getAttribute("user") == null) {
			Executions.sendRedirect("/login.zul");
			return false;
		}
		return true;
	}
}
