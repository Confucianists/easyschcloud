package com.ymy.mixinSource;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(value = { "resource" })
public interface ResourceMixin_resource {

}
