package org.pautib.config;

import javax.ws.rs.container.DynamicFeature;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.FeatureContext;
import javax.ws.rs.ext.Provider;

@Provider
public class DynamicFilterFeature implements DynamicFeature {

    @Override
    public void configure(ResourceInfo resourceInfo, FeatureContext context) {

        MaxAge annotation = resourceInfo.getResourceMethod().getAnnotation(MaxAge.class);

        if (annotation != null) {
            DynamicFilter dynamicFilter = new DynamicFilter(annotation.age());
            context.register(dynamicFilter);
        }

    }
}
