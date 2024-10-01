package com.viewmodel.home;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.util.Notification;

import com.dto.Moneda;
import com.dto.Pais;
import com.dto.Usuario;

import dao.impl.DaoStandard;
import util.Constantes;

public class ManageRecordViewModel {
	private static final Logger log = LogManager.getLogger(ManageRecordViewModel.class);
	private Moneda monedaDetalle;
	private Usuario usuario;
	/* Dao */
	private DaoStandard<Moneda> daoStandard;
	private DaoStandard<Pais> daoStandardPais;

	private HomeViewModel padre;
	private String formulario;
	private boolean camposForm = true;
	private List<Pais> paises;
	private Pais paisSeleccionado;
	private String consulta;
	private List<Pais> paisesFiltrados;
	private Map<String, Object> parametros;

	@SuppressWarnings("unchecked")
	@Init
	public void init(@ContextParam(ContextType.VIEW) Component view) {
		log.info("Ejecutando el método init...");
		parametros = (Map<String, Object>) Executions.getCurrent().getArg();
		monedaDetalle = new Moneda();
		monedaDetalle = (Moneda) this.parametros.get("OBJETO");
		padre = (HomeViewModel) this.parametros.get("PADRE");
		formulario = (String) this.parametros.get("ACCION");
		usuario = (Usuario) this.parametros.get("USUARIO");

	}

	@AfterCompose
	public void afterCompose() {
		log.info("Ejecutando el método afterCompose()...");
		daoStandard = new DaoStandard<Moneda>();
		daoStandardPais = new DaoStandard<Pais>();
		paisSeleccionado = new Pais();
		paises = obtenerPaisesDesdeBaseDeDatos();
		if (formulario.equals("U")) {
			log.info("ACTUALIZANDO REGISTRO...");
			seleccion(monedaDetalle);
		} else if (formulario.equals("N")) {
			log.info("NUEVO REGISTRO...");
			monedaDetalle = new Moneda();
		}
		BindUtils.postNotifyChange(null, null, this, "paises");
	}

	@NotifyChange("*")
	public void seleccion(Moneda moneda) {
		log.info("Ejecutando el método seleccion...");
		monedaDetalle = moneda;
		paisSeleccionado = monedaDetalle.getPais();

		BindUtils.postNotifyChange(null, null, this, "*");
	}

	private List<Pais> obtenerPaisesDesdeBaseDeDatos() {
		log.info("Ejecutando el método obtenerPaisesDesdeBaseDeDatos...");
		List<Pais> paises = new ArrayList<>();
		try {
			paises = daoStandardPais.obtenerListado("listadoPaginadoXPais");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return paises;
	}

	@NotifyChange("*")
	public void onCancelar() {
		log.info("Ejecutando el método onCancelar...");
		monedaDetalle = new Moneda();
		padre.cargarPaginadoMonedas();
		padre.getWindow().detach();
		BindUtils.postNotifyChange(null, null, this, "*");
	}

	@Command
	@NotifyChange("*")
	public void onGuardarMoneda() {
		log.info("Ejecutando el método onGuardarMoneda()...");
		try {
			monedaDetalle.setPais(paisSeleccionado);
			monedaDetalle.setUsuario(usuario);
			if (!validaFormulario()) {
				Notification.show(Constantes.FORMULARIO_VACIO, "info", null, "top_right", 3000);
			} else {
				if (formulario.equals("N")) {
					daoStandard.insertarRegistro("guardarMoneda", monedaDetalle);
					Notification.show(Constantes.REGISTRO_NUEVO, "info", null, "top_right", 3000);
				} else if (formulario.equals("U")) {
					daoStandard.actualizarRegistro("ActualizarMoneda", monedaDetalle);
					Notification.show(Constantes.REGISTRO_ACTUALIZADO, "info", null, "top_right", 3000);
				}
				padre.cargarPaginadoMonedas();
				padre.getWindow().detach();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Command
	@NotifyChange("paisesFiltrados")
	public void filtrarPaises() {
		log.info("Ejecutando el método filtrarPaises()...");
		if (consulta == null || consulta.isEmpty()) {
			paisesFiltrados = new ArrayList<>(paises);
		} else {
			try {
				paisesFiltrados = daoStandardPais.obtenerListado("listadoPaginadoXPais");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public boolean validaFormulario() {
		log.info("Ejecutando el método validaFormulario()...");
		if (monedaDetalle.getNombre() != null && monedaDetalle.getAnoCreacion() != null && monedaDetalle.getPais().getId() != null) {
			log.info("Ejecutando el método validaFormulario()...");
			return true;
		}
		return false;
	}

	public String getConsulta() {
		return consulta;
	}

	public void setConsulta(String consulta) {
		this.consulta = consulta;
	}

	public List<Pais> getPaisesFiltrados() {
		return paisesFiltrados;
	}


	public Moneda getMonedaDetalle() {
		return monedaDetalle;
	}

	public void setMonedaDetalle(Moneda monedaDetalle) {
		this.monedaDetalle = monedaDetalle;
	}

	public boolean isCamposForm() {
		return camposForm;
	}

	public void setCamposForm(boolean camposForm) {
		this.camposForm = camposForm;
	}

	public List<Pais> getPaises() {
		return paises;
	}

	public void setPaises(List<Pais> paises) {
		this.paises = paises;
	}

	public Pais getPaisSeleccionado() {
		return paisSeleccionado;
	}

	@NotifyChange("*")
	public void setPaisSeleccionado(Pais paisSeleccionado) {
		log.info("paisSeleccionado" + paisSeleccionado);
		this.paisSeleccionado = paisSeleccionado;
	}

}
