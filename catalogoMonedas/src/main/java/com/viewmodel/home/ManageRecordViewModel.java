package com.viewmodel.home;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
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
	private Moneda moneda;
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

	@AfterCompose
	public void afterCompose() {
		log.info("Ejecutando el método afterCompose()...");
		daoStandard = new DaoStandard<Moneda>();
		daoStandardPais = new DaoStandard<Pais>();
		paisSeleccionado = new Pais();
		paises = obtenerPaisesDesdeBaseDeDatos();
		BindUtils.postNotifyChange(null, null, this, "paises");

	}

	@SuppressWarnings("unchecked")
	@Init
	public void init(@ContextParam(ContextType.VIEW) Component view) {
		log.info("Ejecutando el método init...");
		parametros = (Map<String, Object>) Executions.getCurrent().getArg();
		moneda = new Moneda();
		moneda = (Moneda) this.parametros.get("OBJETO");
		padre = (HomeViewModel) this.parametros.get("PADRE");
		formulario = (String) this.parametros.get("ACCION");
		usuario = (Usuario) this.parametros.get("USUARIO");
		if (formulario.equals("U")) {
			log.info("ACTUALIZANDO REGISTRO...");
			seleccion(moneda);
		} else if (formulario.equals("N")) {
			log.info("NUEVO REGISTRO...");
			moneda = new Moneda();
		}
	}

	@NotifyChange("*")
	public void validaGuardar() {
		log.info("Ejecutando el método validaGuardar...");
		if (moneda != null) {
			if ((moneda.getNombre() != null || !moneda.getNombre().isEmpty()) && moneda.getAnoCreacion() != null
					&& this.paisSeleccionado != null && moneda.getValor() != null) {
				camposForm = false;
			} else {
				camposForm = true;
			}
		}
	}

	@NotifyChange("*")
	public void seleccion(Moneda moneda) {
		log.info("Ejecutando el método seleccion...");
		this.moneda = moneda;

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
		moneda = new Moneda();
		moneda.setNombre("");
		padre.getWindow().detach();
		BindUtils.postNotifyChange(null, null, this, "*");
		log.info("Ejecutando método onCancelar..." + moneda.getNombre());
	}

	@Command
	@NotifyChange("*")
	public void onGuardarMoneda() {
		log.info("Ejecutando el método onGuardarMoneda()...");
		try {
			if (!validaFormulario()) {
				Notification.show(Constantes.FORMULARIO_VACIO, "info", null, "top_right", 3000);
			} else {
				log.info("paisSeleccionado ::  " + paisSeleccionado);
				log.info("paisSeleccionado ::  " + usuario);
				moneda.setUsuario(usuario);
				moneda.setPais(paisSeleccionado);
				if (formulario.equals("N")) {
					daoStandard.insertarRegistro("guardarMoneda", moneda);
					Notification.show(Constantes.REGISTRO_NUEVO, "info", null, "top_right", 3000);
				} else if (formulario.equals("U")) {
					daoStandard.actualizarRegistro("ActualizarMoneda", moneda);
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
		if (moneda.getNombre() != null  && moneda.getAnoCreacion() != null  && moneda.getPais().getId() != null) {
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

	public Moneda getMoneda() {
		return moneda;
	}

	public void setMoneda(Moneda moneda) {
		this.moneda = moneda;
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
