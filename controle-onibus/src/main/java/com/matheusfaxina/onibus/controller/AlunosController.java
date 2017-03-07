package com.matheusfaxina.onibus.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.matheusfaxina.onibus.filter.AlunoFilter;
import com.matheusfaxina.onibus.model.Aluno;
import com.matheusfaxina.onibus.model.TipoFaculdade;
import com.matheusfaxina.onibus.repository.Alunos;

@Controller
@RequestMapping("/onibus")
public class AlunosController {

	@Autowired
	private Alunos alunos;

	@GetMapping("/novo")
	public ModelAndView novo(Aluno aluno) {
		ModelAndView mv = new ModelAndView("onibus/cadastro-aluno");
		mv.addObject(aluno);
		mv.addObject("tipos", TipoFaculdade.values());
		return mv;
	}
	
	@PostMapping("/novo")
	public ModelAndView salvar(@Valid Aluno aluno, BindingResult result, 
			RedirectAttributes attributes) {
		if (result.hasErrors()) {
			return novo(aluno);
		}
		
		alunos.save(aluno);
		attributes.addFlashAttribute("mensagem", "Aluno salvo com sucesso!");
		return new ModelAndView("redirect:/onibus/novo");
	}
	
	@GetMapping
	public ModelAndView pesquisar(AlunoFilter alunoFilter) {
		ModelAndView mv = new ModelAndView("onibus/pesquisa-alunos");
		mv.addObject("alunos", alunos.findByNomeContainingIgnoreCase(
				Optional.ofNullable(alunoFilter.getNome()).orElse("%")));
		return mv;
	}
	
	@GetMapping("/{codigo}")
	public ModelAndView editar(@PathVariable Long codigo) {
		Aluno aluno = alunos.findOne(codigo);
		return novo(aluno);
	}
	
	@DeleteMapping("/{codigo}")
	public String apagar(@PathVariable Long codigo, RedirectAttributes attributes) {
		alunos.delete(codigo);
		attributes.addFlashAttribute("mensagem", "Aluno removido com sucesso");
		return "redirect:/onibus";
	}
	
}







