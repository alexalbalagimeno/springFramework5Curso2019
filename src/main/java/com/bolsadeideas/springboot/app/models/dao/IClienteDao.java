package com.bolsadeideas.springboot.app.models.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.bolsadeideas.springboot.app.models.entity.Cliente;

// PagingAndSortingRepository tambien hereda de CrudRepository
public interface IClienteDao extends PagingAndSortingRepository<Cliente, Long>{

	//Para hacerlo todo en una sola consulta y optimizar (left join por si no tiene factura)
	// f.id=?1 es el parametro el Long id como es el primero es 1
	@Query("select c from Cliente c left join fetch c.facturas f where c.id=?1")
	public Cliente fetchByIdWithFacturas(Long id);
}

