package br.com.alura.forum.controller;

import java.net.URI;
import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import br.com.alura.forum.controller.dto.DetalhesDoTopicoDTO;
import br.com.alura.forum.controller.dto.TopicoDTO;
import br.com.alura.forum.controller.form.AtualizarTopicoForm;
import br.com.alura.forum.controller.form.TopicoForm;
import br.com.alura.forum.modelo.Topico;
import br.com.alura.forum.repository.CursoRepository;
import br.com.alura.forum.repository.TopicoRepository;

@RestController
@RequestMapping("/topicos")
public class TopicosControler {
	
	@Autowired
	private CursoRepository cursoRepository;
	
	@Autowired
	private TopicoRepository repository;

	@GetMapping
	public List<TopicoDTO> lista(String nomeCurso) {
		if (nomeCurso == null) {
			List<Topico> topicos = repository.findAll();
			return TopicoDTO.converter(topicos);			
		}
		List<Topico> topicos = repository.findByCursoNome(nomeCurso);
		return TopicoDTO.converter(topicos);			
	}
	
	@PostMapping
	@Transactional
	public ResponseEntity<TopicoDTO> cadastrar(@RequestBody @Valid TopicoForm topicoForm, UriComponentsBuilder uriBuilder) {
		Topico topico = topicoForm.converter(cursoRepository);
		this.repository.save(topico);
		URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
		return ResponseEntity.created(uri).body(new TopicoDTO(topico));
	}
	
	@GetMapping("/{id}")
	public DetalhesDoTopicoDTO detalhar(@PathVariable Long id) {
		Topico topico = repository.getOne(id);
		return new DetalhesDoTopicoDTO(topico);
	}
	
	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<TopicoDTO> atualizar(@PathVariable Long id, @RequestBody @Valid AtualizarTopicoForm formAtualizacao) {
		Topico topico = formAtualizacao.atualizar(id, repository);
		return ResponseEntity.ok(new TopicoDTO(topico));
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<?> remover(@PathVariable Long id) {
		repository.deleteById(id);
		return ResponseEntity.ok().build();
	}
}
