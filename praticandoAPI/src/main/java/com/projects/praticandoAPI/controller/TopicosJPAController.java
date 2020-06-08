package com.projects.praticandoAPI.controller;

import java.net.URI;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import com.projects.praticandoAPI.controller.dto.TopicoDto;
import com.projects.praticandoAPI.controller.form.TopicoForm;

import com.projects.praticandoAPI.modelo.Topico;
import com.projects.praticandoAPI.repository.CursoRepository;
import com.projects.praticandoAPI.repository.TopicoRepository;

@RestController
@RequestMapping("/topic")
public class TopicosJPAController {
	
	@Autowired
	private TopicoRepository topicoRepository;
	
	@Autowired
	private CursoRepository cursoRepository;

	@GetMapping("/all")
	public List<TopicoDto> lista(String nomeCurso) {
		List<Topico> topicos;
		if (nomeCurso == null) {
			topicos = topicoRepository.findAll();
		} else {
			topicos = topicoRepository.findByCursoNome(nomeCurso);
		}
		return TopicoDto.converter(topicos);
	}
	
	@PostMapping
	public ResponseEntity<TopicoDto> cadastrar(@RequestBody TopicoForm form, UriComponentsBuilder uriBuilder) {
		Topico topico = form.converter(cursoRepository);
		topicoRepository.save(topico);
		
		URI uri = uriBuilder.path("/topic/{id}").buildAndExpand(topico.getId()).toUri();
		return ResponseEntity.created(uri).body(new TopicoDto(topico));
	}
}