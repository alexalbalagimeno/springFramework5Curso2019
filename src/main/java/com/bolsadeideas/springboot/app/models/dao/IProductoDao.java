package com.bolsadeideas.springboot.app.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.bolsadeideas.springboot.app.models.entity.Producto;

public interface IProductoDao extends CrudRepository<Producto, Long> {

	// hace una query de cuando el nombre sea como el parametro %?1%
	@Query("select p from Producto p where p.nombre like %?1%")
	public List<Producto> findByNombre(String term);

	// equivale a lo mismo que arriba utilizando convenciones del crudRepository
	public List<Producto> findByNombreLikeIgnoreCase(String term);
}