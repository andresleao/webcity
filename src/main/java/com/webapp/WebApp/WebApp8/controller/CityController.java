package com.webapp.WebApp.WebApp8.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.webapp.WebApp.WebApp8.model.City;
import com.webapp.WebApp.WebApp8.repository.CityRepository;

@Controller
@RequestMapping("/cities")
public class CityController {

	private CityRepository cityRepository;
	
	public CityController(CityRepository cityRepository) {
		this.cityRepository = cityRepository;
	}

	@GetMapping
	public String index(Model model) {
		model.addAttribute("cities", this.cityRepository.findAll());
		return "cities/index";
	}
	
	@GetMapping("/new")
	public String newCity(Model model) {
		model.addAttribute("city", new City());
		return "cities/new";
	}
	
	@PostMapping("/")
	public String create(@ModelAttribute("city") City city, Model model) {
		this.cityRepository.save(city);
		return "redirect:/cities";
	}
	
	@GetMapping("/{id}")
	public String editCity(@PathVariable("id") Long id, Model model) {
		model.addAttribute("city", this.cityRepository.findById(id).orElse(null));
		return "cities/edit";
	}
	
	@PutMapping("{id}")
	public String update(@PathVariable("id") Long id, @ModelAttribute("city") City city, Model model) {
		City findCity = this.cityRepository.findById(id).orElse(null);
		
		if (findCity != null) {
			findCity.setId(city.getId());
			findCity.setName(city.getName());
			
			this.cityRepository.save(findCity);
			
			return "redirect:/cities";
		}
		
		return "redirect:/cities";
	}
	
	@DeleteMapping("{id}")
	public String remove(@PathVariable("id") Long id, Model model) {
		City city = this.cityRepository.findById(id).orElse(null);
		
		if (city != null) {
			this.cityRepository.delete(city);
			return "redirect:/cities";
		}
		return "redirect:/cities";
	}
}
