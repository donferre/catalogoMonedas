package com.viewmodel;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.util.Notification;
import org.zkoss.zul.Messagebox;

import com.dto.Usuario;

import config.PasswordEncryption;
import dao.impl.DaoStandard;
import util.Constantes;

public class RegistroUsersViewModel extends SelectorComposer<Component> {
	private static final long serialVersionUID = 1L;
	private static final Logger log = LogManager.getLogger(LoginViewModel.class);
	private Usuario usuario = new Usuario();
	private String contrasena;
	private LoginViewModel padre;
	/* Dao */
	private DaoStandard<Usuario> daoStandard;

	@AfterCompose
	public void afterCompose() {
		log.info("Ejecutando el método afterCompose()...");
		daoStandard = new DaoStandard<Usuario>();

	}

	@Init
	public void init(@ContextParam(ContextType.VIEW) Component view, @ExecutionArgParam("PADRE") LoginViewModel vm) {
		log.info("Ejecutando el método init...");
		padre = vm;
	}

	@Command
	@NotifyChange("*")
	public void closeWindow() {
		Executions.sendRedirect("login.zul");
	}

	@Command
	@NotifyChange("*")
	public void onGuardarUsuario() {
		log.info("Ejecutando el método onGuardarUsuario()...");
		try {
			if (usuario.getNombre() == null || usuario.getContrasena() == null) {
				Messagebox.show(Constantes.FORMULARIO_VACIO, "Usuarios", Messagebox.OK, Messagebox.EXCLAMATION);
				return;
			}
			if (!usuario.getContrasena().equals(contrasena)) {
				Messagebox.show(Constantes.CONTRASEÑA_NO, "Usuarios", Messagebox.OK, Messagebox.EXCLAMATION);
				return;
			}

			List<Usuario> dtousuario = (List<Usuario>) daoStandard.obtenerListado("SelectXUsuario", usuario);

			if (dtousuario.size() > 0) {
				log.info("TAMAÑO LISTA: " + dtousuario.size());
				Messagebox.show(Constantes.USUARIO_EXISTENTE, "Usuarios", Messagebox.OK, Messagebox.EXCLAMATION);
				return;
			}
			String hashedPassword = PasswordEncryption.hashPassword(usuario.getContrasena());
			usuario.setContrasena(hashedPassword);
			daoStandard.insertarRegistro("guardarUsuario", usuario);
			Notification.show(Constantes.REGISTRO_NUEVO, "info", null, "top_right", 3000);
			padre.getWindow().detach();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

}
