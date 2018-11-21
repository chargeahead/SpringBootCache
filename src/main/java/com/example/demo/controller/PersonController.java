package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Person;
import com.example.demo.service.PersonService;

@RestController
public class PersonController {

	@Autowired
	private PersonService personService;
	
	@CachePut(value="persons",key="#firstName")
	@RequestMapping("/create")
	@ResponseBody
	public Person create(@RequestParam String firstName, @RequestParam String lastName, @RequestParam int age) {
		System.out.println("Creating record");
		Person p = personService.create(firstName, lastName, age);
		return p;
	}
	@Cacheable(value= "persons",key="#firstName",condition="#firstName.length() > 4",unless="#result.age>35")
	@RequestMapping("/get")
	@ResponseBody
	public Person getPerson(@RequestParam String firstName) {
		System.out.println("Getting record");
		return personService.getByFirstName(firstName);
	}
	@Cacheable(value= "persons")
	@RequestMapping("/getAll")
	public List<Person> getAll(){
		System.out.println("Getting all records");
		return personService.getAll();
	}
	@CachePut(value="persons",key="#firstName")
	@RequestMapping("/update")
	@ResponseBody
	public Person update(@RequestParam String firstName, @RequestParam String lastName, @RequestParam int age) {
		System.out.println("Updating record");
		Person p = personService.update(firstName, lastName, age);
		return p;
	}
	@CacheEvict(value="persons",key="#firstName")
	@RequestMapping("/delete")
	public String delete(@RequestParam String firstName) {
		System.out.println("Deleting record");
		personService.delete(firstName);
		return "Deleted "+firstName;
	}
	@CacheEvict(value="persons",allEntries=true)
	@RequestMapping ("/deleteAll")
	public String deleteAll() {
		System.out.println("Deleting all records");
		personService.deleteAll();
		return "Deleted all records";
	}
	@CacheEvict(value="persons",allEntries=true)
	@RequestMapping("/clearCache")
	public String clearCache() {
		return "Cleared cache";
	}
	
}
