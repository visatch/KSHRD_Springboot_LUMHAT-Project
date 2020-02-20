package com.kshrd.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

@Configuration
@PropertySource("classpath:ams.properties")
public class MultipleLanguagesConfiguration implements WebMvcConfigurer {

    @Value("languages/staticPage/messages")
    private String[] baseNames;

    @Bean
    public LocaleResolver localeResolver(){
        SessionLocaleResolver slr=new SessionLocaleResolver();
        slr.setDefaultLocale(new Locale("km"));
        return slr;
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor(){
        LocaleChangeInterceptor lci=new LocaleChangeInterceptor();
        lci.setParamName("lang");
        return lci;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(this.localeChangeInterceptor());
    }

    @Bean
    public MessageSource messageSource(){
        ResourceBundleMessageSource msg=new ResourceBundleMessageSource();
        msg.setDefaultEncoding("UTF-8");
        msg.setBasenames(baseNames);
        return msg;
    }
}
