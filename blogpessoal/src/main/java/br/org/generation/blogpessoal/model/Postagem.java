package br.org.generation.blogpessoal.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity //inserir tabela
@Table(name="tb_postagens") //criar nome da tabela
public class Postagem {
    @Id //primary key(id)
    @GeneratedValue(strategy=GenerationType.IDENTITY) //auto increment
	private Long id;

    @NotBlank(message="A inserção do título é obrigatória.") //título obrigatório
    @Size(min=5, max=100, message="Título deve conter entre 5 e 100 caracteres.") //tamanho dos caracteres
	private String titulo;

    @NotBlank(message="A inserção do texto é obrigatória.")
    @Size(min=10, max=1000, message="Texto deve conter entre 10 e 1000 caracteres.")
	private String texto;

    @UpdateTimestamp //inserir data local
	private LocalDateTime data;
    
    @ManyToOne
    @JsonIgnoreProperties("postagem")
    private Tema tema;
	
    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public LocalDateTime getData() {
		return data;
	}

	public void setData(LocalDateTime data) {
		this.data = data;
	}

	public Tema getTema() {
		return tema;
	}

	public void setTema(Tema tema) {
		this.tema = tema;
	}
}


