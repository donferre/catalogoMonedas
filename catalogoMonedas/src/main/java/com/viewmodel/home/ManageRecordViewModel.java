package com.viewmodel.home;

import java.util.ArrayList;
import java.util.List;

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

import dao.impl.DaoStandard;
import util.Constantes;

public class ManageRecordViewModel {

	private Moneda moneda = new Moneda();
	/* Dao */
	private DaoStandard<Moneda> daoStandard;
	private DaoStandard<Pais> daoStandardPais;
	private HomeViewModel padre;
	private String formulario;
	private boolean camposForm = true;
	private List<Pais> paises;
	private Pais paisSeleccionado;

	@AfterCompose
	public void afterCompose() {
		System.out.println("Ejecutando el método afterCompose()...");
		daoStandard = new DaoStandard<Moneda>();
		daoStandardPais = new DaoStandard<Pais>();
		paises = obtenerPaisesDesdeBaseDeDatos();
		  System.out.println("PAISES:: " + paises);
		BindUtils.postNotifyChange(null, null, this, "paises"); 
		if (formulario.equals("U")) {

		}
	}

	@Init
	public void init(@ContextParam(ContextType.VIEW) Component view, @ExecutionArgParam("OBJETO") Moneda moneda,
			@ExecutionArgParam("PADRE") HomeViewModel vm, @ExecutionArgParam("ACCION") String form) {
		System.out.println("Ejecutando el método init...");
		this.moneda = moneda;
		padre = vm;
		formulario = form;
	}

	@NotifyChange("*")
	public void validaGuardar() {
		System.out.println("Ejecutando el método validaGuardar...");
		if (moneda != null) {
			if ((moneda.getNombre() != null || !moneda.getNombre().isEmpty()) && moneda.getAnoCreacion() != null
					&& moneda.getPais() != null && moneda.getValor() != null) {
				camposForm = false;
			} else {
				camposForm = true;
			}
		}
	}
	
	private List<Pais> obtenerPaisesDesdeBaseDeDatos() {
		System.out.println("Ejecutando el método obtenerPaisesDesdeBaseDeDatos...");
	    List<Pais> paises = new ArrayList<>();
	    try {
	        paises = daoStandardPais.obtenerListado("listadoPaginadoXPais");
	    	System.out.println("PAISES:: "+paises);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return paises; 
	}
	
	//Esta era de prueba porque no me esta sirviendo la de arriba...
//	private List<Pais> obtenerPaisesDesdeBaseDeDatos() {
//	    List<Pais> paises = new ArrayList<>();
//	    // Datos de prueba
//	    paises.add(new Pais(1, "Argentina"));
//	    paises.add(new Pais(2, "Brasil"));
//	    paises.add(new Pais(3, "Chile"));
//	    return paises; // Retorna la lista estática
//	}

	public void setPaises(List<Pais> paises) {
		this.paises = paises;
	}

	@NotifyChange("*")
	public void onCancelar() {
		System.out.println("Ejecutando método onCancelar...");
		moneda = new Moneda();
		moneda.setNombre("");
		padre.getWindow().detach();
		BindUtils.postNotifyChange(null, null, this, "*");
		System.out.println("Ejecutando método onCancelar..." + moneda.getNombre());
	}

	@Command
	@NotifyChange("*")
	public void onGuardarMoneda() {
		System.out.println("Ejecutando método onGuardarUsuario()...");
		try {
			daoStandard.insertarRegistro("guardarMoneda", moneda);
			Notification.show(Constantes.REGISTRO_NUEVO, "info", null, "top_right", 3000);
			padre.cargarPaginadoMonedas();
			padre.getWindow().detach();
		} catch (Exception e) {
			e.printStackTrace();
		}

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

	public Pais getPaisSeleccionado() {
		return paisSeleccionado;
	}

	@NotifyChange("paisSeleccionado")
	public void setPaisSeleccionado(Pais paisSeleccionado) {
		this.paisSeleccionado = paisSeleccionado;
		if (paisSeleccionado != null) {
			this.moneda.setPais(paisSeleccionado); // Seteamos el código del país en el objeto Moneda
		}
	}

}
