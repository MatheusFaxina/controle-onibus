package com.matheusfaxina.onibus.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.matheusfaxina.onibus.model.Despesa;
import com.matheusfaxina.onibus.model.TipoDespesa;
import com.matheusfaxina.onibus.repository.Despesas;

@Controller
public class DespesasController {
	
	@Autowired
	private Despesas despesas;

	@GetMapping("/onibus/despesas")
	public ModelAndView novo(Despesa despesa) {
		ModelAndView mv = new ModelAndView("onibus/CadastroDespesas");
		mv.addObject("tipos", TipoDespesa.values());
		return mv;
	}
	
	@PostMapping("/onibus/despesas")
	public ModelAndView salvar(@Valid Despesa despesa, BindingResult result) {
		if (result.hasErrors()) {
			return novo(despesa);
		}
		
		despesas.save(despesa);
		return new ModelAndView("redirect:/onibus/despesas");
	}
	
}
