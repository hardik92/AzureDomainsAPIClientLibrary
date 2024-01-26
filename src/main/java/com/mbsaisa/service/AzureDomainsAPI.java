package com.mbsaisa.service;

import org.springframework.stereotype.Service;

import com.azure.core.credential.TokenCredential;
import com.azure.core.management.AzureEnvironment;
import com.azure.core.management.profile.AzureProfile;
import com.azure.core.util.Context;
import com.azure.identity.DefaultAzureCredentialBuilder;
import com.azure.resourcemanager.AzureResourceManager;
import com.azure.resourcemanager.appservice.fluent.models.DomainInner;
import com.azure.resourcemanager.appservice.fluent.models.NameIdentifierInner;
import com.azure.resourcemanager.appservice.models.DnsType;
import com.mbsaisa.model.Domain;

@Service
public class AzureDomainsAPI {

	private AzureResourceManager azure;

	public AzureDomainsAPI() {
		try {
			this.azure = this.authenticate();
		} catch (Exception e) {
			this.azure = null;
		}
	}

	private AzureResourceManager authenticate() throws Exception {
		try {
			AzureProfile profile = new AzureProfile(AzureEnvironment.AZURE);
			TokenCredential credential = new DefaultAzureCredentialBuilder()
					.authorityHost(profile.getEnvironment().getActiveDirectoryEndpoint())
					.build();
			return AzureResourceManager
					.authenticate(credential, profile)
					.withDefaultSubscription();
		} catch (Exception e) {
			throw new Exception("Azure authentication failed", e);
		}
	}

	boolean checkDomain(String domainName) throws Exception {
		if (domainName == null || domainName.isEmpty()) {
			throw new Exception("Domain name is empty");
		}
		try {
			if (this.azure == null) {
				try {
					this.azure = this.authenticate();
				} catch (Exception e) {
					throw new Exception("Azure authentication failed", e);
				}
			}

			return this.azure
					.webApps()
					.manager()
					.serviceClient()
					.getDomains()
					.checkAvailabilityWithResponse(new NameIdentifierInner().withName(domainName), Context.NONE)
					.getValue()
					.available();
		} catch (Exception e) {
			throw new Exception("Domain check failed", e);
		}
	}

	/**
	 * Sample code: Create App Service Domain.
	 *
	 * @param azure The entry point for accessing resource management APIs in Azure.
	 */
	void createAppServiceDomain(Domain domain) {
		this.azure
				.webApps()
				.manager()
				.serviceClient()
				.getDomains()
				.createOrUpdate(
						domain.getResourceGroupName(),
						domain.getDomainName(),
						new DomainInner()
								.withLocation(domain.getLocation())
								.withTags(domain.getTags())
								.withContactAdmin(domain.getProperties().contactAdmin())
								.withContactBilling(domain.getProperties().contactBilling())
								.withContactRegistrant(domain.getProperties().contactRegistrant())
								.withContactTech(domain.getProperties().contactTech())
								.withPrivacy(domain.getProperties().privacy())
								.withAutoRenew(domain.getProperties().autoRenew())
								.withConsent(domain.getProperties().consent())
								.withDnsType(domain.getProperties().dnsType() == null ?
										DnsType.DEFAULT_DOMAIN_REGISTRAR_DNS : domain.getProperties().dnsType())
								.withAuthCode(domain.getProperties().authCode())
								.withDnsZoneId(domain.getProperties().dnsZoneId())
								.withKind(domain.getKind()),
						Context.NONE);
	}
}
