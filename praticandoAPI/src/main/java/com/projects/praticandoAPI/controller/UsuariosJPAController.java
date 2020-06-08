package com.projects.praticandoAPI.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import com.projects.praticandoAPI.controller.dto.UsuarioDto;
import com.projects.praticandoAPI.controller.form.UsuarioForm;
import com.projects.praticandoAPI.modelo.Usuario;
import com.projects.praticandoAPI.repository.UsuarioRepository;

@RestController
@RequestMapping("user")
public class UsuariosJPAController {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@GetMapping("/all")
	public List<UsuarioDto> lista(String nome) {
		if (nome == null) {
			List<Usuario> usuarios = usuarioRepository.findAll();
			return UsuarioDto.converter(usuarios);
		} else {
			List<Usuario> usuarios = usuarioRepository.findByNome(nome);
			return UsuarioDto.converter(usuarios);
		}
	}
	
	@PostMapping
	public ResponseEntity<UsuarioDto> cadastrar(@RequestBody UsuarioForm form, UriComponentsBuilder uriBuilder) {
		Usuario usuario = form.converter();
		usuarioRepository.save(usuario);
		
		URI uri = uriBuilder.path("/user/{id}").buildAndExpand(usuario.getId()).toUri();
		return ResponseEntity.created(uri).body(new UsuarioDto(usuario));
	}
}
