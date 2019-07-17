package com.bolsadeideas.springboot.app.models.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "clientes")
public class Cliente implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	//not empty para validar que los campos sean mayor que 0 en strings
	@NotEmpty
	private String nombre;
	
	@NotEmpty
	private String apellido;
	
	@NotEmpty
	@Email
	private String email;

	//not null para validar que los campos sean mayor que 0 en diferente de strings
	@NotNull
	// create_at es el campo en la tabla mysql
	@Column(name = "create_at")
	@Temporal(TemporalType.DATE)
	// se define patron al escribir la fecha
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date createAt;

	//un cliente muchas facturas
	//FetchType.LAZY es la recomendada para cargar lo justo
	//CascadeType.ALL opearciones delete y persist en cadena
	//mappedBy="cliente" mapea el nombre del atributo cliente en la clase factura
	@OneToMany(mappedBy="cliente", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Factura> facturas;
	
	
	public Cliente() {
		//se inicia arraylist Factura cuando creamos cliente
		this.facturas = new ArrayList<Factura>();
	}

	private String foto;
	/*// se llama este metodo justo antes del persist para crear la fecha  
	// automaticamente en el justo momento antes de persistir
	@PrePersist
	public void prePersist() {
		createAt = new Date();
	}*/
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public List<Factura> getFacturas() {
		return facturas;
	}

	public void setFacturas(List<Factura> facturas) {
		this.facturas = facturas;
	}

	public void addFactura(Factura factura) {
		facturas.add(factura);
	}
	
	
	@Override
	public String toString() {
		return nombre + " " + apellido;
	}

	private static final long serialVersionUID = 1L;
}
