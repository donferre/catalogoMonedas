package com.viewmodel.home;

import java.util.ArrayList;
import java.util.List;

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

	@AfterCompose
	public void afterCompose() {
		log.info("Ejecutando el método afterCompose()...");
		log.info("Ejecutando el método afterCompose()...");
		daoStandard = new DaoStandard<Moneda>();
		daoStandardPais = new DaoStandard<Pais>();
		paisSeleccionado = new Pais();
		
		paises = obtenerPaisesDesdeBaseDeDatos();
		BindUtils.postNotifyChange(null, null, this, "paises");
		if (formulario.equals("U")) {

		}
	}

	@Init
	public void init(@ContextParam(ContextType.VIEW) Component view, @ExecutionArgParam("OBJETO") Moneda moneda,
			@ExecutionArgParam("PADRE") HomeViewModel vm, @ExecutionArgParam("ACCION") String form) {
		log.info("Ejecutando el método init...");
		this.moneda = new Moneda();
		this.moneda = moneda;
		padre = vm;
		formulario = form;
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
		log.info("Ejecutando el método onGuardarUsuario()...");
		try {
			log.info("paisSeleccionado ::  "+paisSeleccionado);
			moneda.setPais(paisSeleccionado);
			daoStandard.insertarRegistro("guardarMoneda", moneda);
			Notification.show(Constantes.REGISTRO_NUEVO, "info", null, "top_right", 3000);
			padre.cargarPaginadoMonedas();
			padre.getWindow().detach();
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
		log.info("paisSeleccionado"+paisSeleccionado);
		this.paisSeleccionado = paisSeleccionado;
	}

}
