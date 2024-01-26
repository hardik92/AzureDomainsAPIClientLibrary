package com.mbsaisa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mbsaisa.model.Domain;
import com.mbsaisa.exception.DomainCreationFailed;
import com.mbsaisa.exception.DomainNotAvailableException;

@Service
public class DomainService {
	private final AzureDomainsAPI azureDomainsAPI;

	@Autowired
	public DomainService(AzureDomainsAPI azureDomainsAPI) {
		this.azureDomainsAPI = azureDomainsAPI;
	}

	public Domain registerDomain(Domain domain) throws Exception {
		if (this.azureDomainsAPI.checkDomain(domain.getDomainName())) {
			try {
				this.azureDomainsAPI.createAppServiceDomain(domain);
				return domain;
			} catch (Exception e) {
				throw new DomainCreationFailed("Domain creation failed", e);
			}
		} else {
			throw new DomainNotAvailableException("Domain name not available");
		}
	}

	public boolean checkDomainAvailability(String domainName) throws Exception {
		return this.azureDomainsAPI.checkDomain(domainName);
	}
}
