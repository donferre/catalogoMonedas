package com.viewmodel;

import java.util.HashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.util.Notification;
import org.zkoss.zul.Window;

import com.dto.Usuario;

import config.PasswordEncryption;
import dao.impl.DaoStandard;
import util.Constantes;

public class LoginViewModel {
	private static final Logger log = LogManager.getLogger(LoginViewModel.class);
	private Usuario usuario;
	private Window window;
	private String okLogin = "S";
	private DaoStandard<Usuario> daoStandard = new DaoStandard<Usuario>();;

	@Init
	public void onInicializa() {
		log.info("Ejecuta el método onInicializa...");
		log.info("INICIAAA");
		usuario = new Usuario();
		  if (Sessions.getCurrent().getAttribute("user") != null) {
		        Sessions.getCurrent().invalidate();
		    }
	}

	public void onLogin() {
		log.info("Ejecuta el método onLogin...");
		if (usuario.getNombre() != null && usuario.getContrasena() != null) {
			try {
				Usuario usuarioDB = (Usuario) daoStandard.obtenerRegistro("SelectXUsuario", usuario);

				if (usuarioDB != null) {
				    String storedHash = usuarioDB.getContrasena(); 
				    String enteredPassword = usuario.getContrasena(); 

				    boolean isAuthenticated = PasswordEncryption.checkPassword(enteredPassword, storedHash);
				    log.info("isAuthenticated: " + isAuthenticated);

				    if (isAuthenticated) {
				    	Sessions.getCurrent().setAttribute("PADRE", this);
				        Sessions.getCurrent().setAttribute("user", usuarioDB);
				        Executions.sendRedirect("zul/home/home.zul");
				    } else {
				        Notification.show(Constantes.LOGIN_INCORRECTO);
				    }
				} else {
				    Notification.show(Constantes.ERROR_USUARIO);
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.info(e);
			}
		} else {
			Notification.show(Constantes.FORMULARIO_VACIO);
		}
	}
	

	@Command
	public void registroUser(Event e) {
		log.info("Ejecuta el método registroUser...");
		HashMap<String, Object> map = new HashMap<String,Object>();
		map.put("PADRE", this);
		window = (Window) Executions.createComponents("zul//user/registro_user.zul", null, map);
		window.doModal();
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Window getWindow() {
		return window;
	}

	public void setWindow(Window window) {
		this.window = window;
	}

	public String getOkLogin() {
		return okLogin;
	}

	public void setOkLogin(String okLogin) {
		this.okLogin = okLogin;
	}

}
