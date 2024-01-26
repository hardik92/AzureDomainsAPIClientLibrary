package com.mbsaisa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.mbsaisa.model.Domain;
import com.mbsaisa.service.DomainService;

@RestController
@RequestMapping("/domain")
public class DomainController {
	DomainService domainService;

	@Autowired
	public DomainController(DomainService domainService) {
		this.domainService = domainService;
	}

	@PostMapping(value = "/create")
	@ResponseBody
	public ResponseEntity<Domain> registerDomain(@RequestBody Domain domain) throws Exception {
		if (domain.getDomainName() == null || domain.getDomainName().isEmpty())
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

		return new ResponseEntity<>(domainService.registerDomain(domain), HttpStatus.ACCEPTED);

	}

	@PostMapping(value = "/checkAvailability")
	public boolean checkDomainAvailability(@RequestBody String domainName) throws Exception {
		return domainService.checkDomainAvailability(domainName);
	}
}
