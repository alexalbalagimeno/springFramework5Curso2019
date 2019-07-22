package com.bolsadeideas.springboot.app.models.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.bolsadeideas.springboot.app.models.entity.Usuario;

public interface IUsuarioDao extends CrudRepository<Usuario, Long> {

	// A traves del nombre del metodo (Query method name), se ejecutara la consulta
	// JPQL: "select u from Usuario u where u.username=?1"
	public Usuario findByUsername(String username);
	
	//Es lo mismo
	/*@Query("select u from Usuario u where u.username=?1")
	public Usuario fetchByUsername(String username);*/
}
