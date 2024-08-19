package dao.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import config.MyBatisConfig;
import dao.IDao;

public class DaoStandard<K> implements IDao<K> {
	
   private SqlSession session;
		 
	@Override
	public List<K> obtenerListado(String sqlName, Object objeto) throws Exception {
		try{
			session = new MyBatisConfig().getSession();
			return session.selectList(sqlName, objeto);
		}finally{
			session.close();
		}
	}

	@Override
	public K obtenerRegistro(String sqlName, Object objeto) throws Exception {
		try{
			session = new MyBatisConfig().getSession();
			return session.selectOne(sqlName, objeto);
		}finally{
			session.close();
		}
	}

	@Override
	public Object insertarRegistro(String sqlName, Object objeto) throws Exception {
		System.out.println("Ejecutando método afterCompose()...");
		try{
			session = new MyBatisConfig().getSession();
			return session.insert(sqlName, objeto);
		}finally{
			session.commit();
			session.close();
		}
	}

	@Override
	public Object actualizarRegistro(String sqlName, Object objeto) throws Exception {		
		try{
			session = new MyBatisConfig().getSession();
			return session.update(sqlName, objeto);
		}finally{
			session.commit();
			session.close();
		}
	}

	@Override
	public Object borrarRegistro(String sqlName, Object objeto) throws Exception {
		try{
			session = new MyBatisConfig().getSession();
			return session.delete(sqlName, objeto);
		}finally{
			session.commit();
			session.close();
		}
	}

}
