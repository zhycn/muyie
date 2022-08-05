/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.muyie.framework.retrofit.boot;

import org.muyie.framework.retrofit.annotations.RetrofitClient;
import org.muyie.framework.retrofit.annotations.RetrofitClientScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.ClassUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

public class RetrofitServiceFactoryBeanRegistrar implements ImportBeanDefinitionRegistrar {

  private final static Logger log = LoggerFactory.getLogger(RetrofitServiceFactoryBeanRegistrar.class);

  @Override
  public void registerBeanDefinitions(AnnotationMetadata annotationMetadata,
                                      BeanDefinitionRegistry registry) {
    if (!registry.containsBeanDefinition(RetrofitServiceBeanPostProcessorAdapter.BEAN_NAME)) {
      registry.registerBeanDefinition(RetrofitServiceBeanPostProcessorAdapter.BEAN_NAME,
        new RootBeanDefinition(RetrofitServiceBeanPostProcessorAdapter.class));
    }

    doRegisterRetrofitServiceBeanDefinitions(annotationMetadata, registry);
  }

  private void doRegisterRetrofitServiceBeanDefinitions(AnnotationMetadata annotationMetadata,
                                                        BeanDefinitionRegistry registry) {
    RetrofitServiceComponentProvider provider = RetrofitServiceComponentProvider.getInstance();

    Set<String> packagesToScan = getPackagesToScan(annotationMetadata);

    for (String packageToScan : packagesToScan) {
      log.debug("Trying to find candidates from package {}", packageToScan);

      Set<BeanDefinition> candidates = provider.findCandidateComponents(packageToScan);

      if (!candidates.isEmpty()) {
        processCandidates(candidates, registry);
      }
    }
  }

  private void processCandidates(Set<BeanDefinition> candidates, BeanDefinitionRegistry registry) {
    log.debug("Found {} Retrofit Service candidate(s)", candidates.size());

    for (BeanDefinition beanDefinition : candidates) {
      String beanName = generateBeanName(beanDefinition);

      log.debug("Processing candidate class {} with bean name {}",
        beanDefinition.getBeanClassName(), beanName);

      registry.registerBeanDefinition(beanName, beanDefinition);
    }
  }

  private Set<String> getPackagesToScan(AnnotationMetadata metadata) {
    AnnotationAttributes attributes = AnnotationAttributes
      .fromMap(metadata.getAnnotationAttributes(RetrofitClientScan.class.getName()));

    String[] value = attributes.getStringArray("value");
    Class<?>[] basePackageClasses = attributes.getClassArray("basePackageClasses");
    Set<String> packagesToScan = new LinkedHashSet<String>();

    if (!ObjectUtils.isEmpty(value)) {
      packagesToScan.addAll(Arrays.asList(value));
    }

    for (Class<?> basePackageClass : basePackageClasses) {
      packagesToScan.add(ClassUtils.getPackageName(basePackageClass));
    }

    if (packagesToScan.isEmpty()) {
      return Collections.singleton(ClassUtils.getPackageName(metadata.getClassName()));
    }

    return packagesToScan;
  }

  private String generateBeanName(BeanDefinition beanDefinition) {
    // Try obtaining the client specified bean name if available in the annotated interface
    try {
      Class<?> beanClass = Class.forName(beanDefinition.getBeanClassName());
      RetrofitClient retrofitService = beanClass.getAnnotation(RetrofitClient.class);

      if (retrofitService != null && StringUtils.hasText(retrofitService.name())) {
        return retrofitService.name();
      }

      // Support @Qualifier retrofitService
      Qualifier qualifier = beanClass.getAnnotation(Qualifier.class);
      if (qualifier != null && !"".equals(qualifier.value())) {
        return qualifier.value();
      }

      // Reduce the conflict of same endpoint class name, use full package class name instead
      // So we wouldn't prefer to use AnnotationBeanNameGenerator
      return beanClass.getName();

    } catch (ClassNotFoundException e) {
      throw new RuntimeException("Cannot obtain bean name for Retrofit service interface", e);
    }
  }
}
