package com.mbsaisa.model;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.OffsetDateTime;
import java.util.Map;

import com.azure.resourcemanager.appservice.fluent.models.DomainProperties;

import com.azure.resourcemanager.appservice.models.Contact;
import com.azure.resourcemanager.appservice.models.DomainPurchaseConsent;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class Domain implements Serializable {
	@JsonProperty(value = "domainName", required = true)
	private String domainName;

	@JsonProperty(value = "resourceGroupName", required = true)
	private String resourceGroupName;

	@JsonProperty(value = "location", required = true)
	private String location;

	@JsonProperty(value = "properties", required = true)
	private DomainProperties properties;

	public Domain() throws UnknownHostException {
		this.location = "global";

		this.resourceGroupName = "domain-resource-group";

		this.properties = new DomainProperties();

		Contact contact = new Contact();
		contact.withPhone("+370.64444977");
		contact.withEmail("zilvinas.juraska@gmail.com");
		contact.withNameFirst("Zilvinas");
		contact.withNameLast("Juraska");

		this.properties.withContactAdmin(contact);
		this.properties.withContactBilling(contact);
		this.properties.withContactRegistrant(contact);
		this.properties.withContactTech(contact);

		DomainPurchaseConsent consent = new DomainPurchaseConsent();
		consent.withAgreedAt(OffsetDateTime.now());
		consent.withAgreedBy(String.valueOf(InetAddress.getLocalHost()));

		this.properties.withConsent(consent);
	}

}
