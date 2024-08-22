package com.viewmodel.home;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.util.Notification;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Window;

import com.dto.Moneda;
import com.dto.Usuario;
import com.viewmodel.LoginViewModel;

import dao.impl.DaoStandard;
import util.Constantes;
import util.SessionUtils;

public class HomeViewModel {
	private static final Logger log = LogManager.getLogger(HomeViewModel.class);
	private List<Moneda> listaMonedas;
	private int pageSize = 10;
	private ListModelList<Moneda> listadoPaginado;
	private DaoStandard<Moneda> daoStandard;
	private Moneda moneda;
	private Moneda monedaSelect;
	private Usuario usuario;
	private String botones;
	private Window window;

	@Init
	public void init() {
		log.info("Ejecutando el método init()...");
		// Llama al método estático creado para que no puedan acceder
		if(SessionUtils.checkSession()) {
			usuario = (Usuario) Sessions.getCurrent().getAttribute("user");
			log.info("El ID del usuario logueado es: " + usuario.getUsuario_id());
		};
		daoStandard = new DaoStandard<Moneda>();
		monedaSelect  = new Moneda();
		cargarPaginadoMonedas();
	}


    @Command
    @NotifyChange("*")
    public void selectMoneda(@BindingParam("item") Moneda moneda) {
    	botones = "S";
        monedaSelect = moneda;
        // Aquí puedes manejar la lógica de lo que quieres hacer con la moneda seleccionada
        log.info("Moneda seleccionada: " + moneda.getNombre());
    }

	@NotifyChange("*")
	public void cargarPaginadoMonedas() {
		log.info("Ejecutando el método cargarPaginadoMonedas...");
		try {
			moneda = new Moneda();
			moneda.setUsuario(usuario);
			listaMonedas = daoStandard.obtenerListado("listadoPaginadoXMoneda", moneda);
			listadoPaginado = new ListModelList<>(listaMonedas.subList(0, Math.min(pageSize, listaMonedas.size())));
			  BindUtils.postNotifyChange(null, null, this, "listadoPaginado");
		} catch (Exception e) {
			e.printStackTrace();
			log.info(e);
		}
	}

	 public String getRowClass(Moneda moneda) {
		 log.info("Ejecutando el método getRowClass...");
	        if (moneda.equals(monedaSelect)) {
	            return "selected-row";
	        }
	        return "";
	    }
	 
	@Command
	@NotifyChange("listadoPaginado")
	public void paginar(Paging paging) {
		int start = paging.getActivePage() * pageSize;
		int end = Math.min(start + pageSize, listaMonedas.size());
		listadoPaginado = new ListModelList<>(listaMonedas.subList(start, end));
	BindUtils.postNotifyChange(paging, botones);
	}
	
	@Command
	public void onNuevo() {
		log.info("Ejecutando el método onNuevo...");
		HashMap<String, Object> map = new HashMap<String,Object>();
		map.put("OBJETO", moneda);
		map.put("PADRE", this);
		map.put("ACCION", Constantes.NUEVO);
		window = (Window) Executions.createComponents("manageRecord.zul", null, map);
		window.doModal();
	}
	
	@Command
	public void onEditar() {
		log.info("Ejecutando el método onEditar...");
		HashMap<String, Object> map = new HashMap<String,Object>();
		map.put("OBJETO", moneda);
		map.put("PADRE", this);
		map.put("ACCION", Constantes.EDITAR);
		window = (Window) Executions.createComponents("manageRecord.zul", null, map);
		window.doModal();
	}

	@Command
	public void logout() {
		log.info("Ejecutando el método logout()...");
		Sessions.getCurrent().invalidate();
		Executions.sendRedirect("/login.zul");
		Notification.show("Has cerrado sesión exitosamente.");
		/*
		 * Invalidar la sesión del usuario para que no quede logeado por debajo.
		 * Redirigir al usuario a la página de inicio de sesión login. Mostrar mensaje
		 * para alertar.
		 */
	}

	public List<Moneda> getListaMonedas() {
		return listaMonedas;
	}

	public void setListaMonedas(List<Moneda> listaMonedas) {
		this.listaMonedas = listaMonedas;
	}

	public ListModelList<Moneda> getListadoPaginado() {
		return listadoPaginado;
	}

	public void setListadoPaginado(ListModelList<Moneda> listadoPaginado) {
		this.listadoPaginado = listadoPaginado;
	}

	public Moneda getMoneda() {
		return moneda;
	}

	public void setMoneda(Moneda moneda) {
		this.moneda = moneda;
	}

	public int getPageSize() {
		return pageSize;
	}

	public int getTotalSize() {
		return listaMonedas.size();
	}

	public String getBotones() {
		return botones;
	}

	public void setBotones(String botones) {
		this.botones = botones;
	}

	public Window getWindow() {
		return window;
	}

	public void setWindow(Window window) {
		this.window = window;
	}

	public Moneda getMonedaSelect() {
		return monedaSelect;
	}

	public void setMonedaSelect(Moneda monedaSelect) {
		this.monedaSelect = monedaSelect;
	}

	
	
	
}
