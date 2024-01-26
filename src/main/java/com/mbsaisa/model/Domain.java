package com.mbsaisa.model;

import java.io.Serializable;
import java.util.Map;

import com.azure.resourcemanager.appservice.fluent.models.DomainProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Domain implements Serializable {
	@JsonProperty(value = "domainName", required = true)
	private String domainName;

	@JsonProperty(value = "resourceGroupName", required = true)
	private String resourceGroupName;

	@JsonProperty(value = "location", required = true)
	private String location;

	@JsonProperty(value = "properties", required = true)
	private DomainProperties properties;

	@JsonProperty(value = "kind")
	private String kind;

	@JsonProperty(value = "tags")
	private Map<String, String> tags;
}
