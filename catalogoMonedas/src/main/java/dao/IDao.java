package dao;

import java.util.Collection;

public interface IDao<T> {
	
	abstract Collection<T> obtenerListado (String  sqlName, Object objeto) throws Exception;
	abstract Collection<T> obtenerListado (String  sqlName) throws Exception;
	abstract Object obtenerRegistro (String sqlName,  Object objeto ) throws Exception;
	abstract Object insertarRegistro (String sqlName, Object objeto ) throws Exception;
	abstract Object actualizarRegistro (String sqlName, Object objeto ) throws Exception;	
	abstract Object borrarRegistro (String sqlName, Object objeto ) throws Exception;	

}
