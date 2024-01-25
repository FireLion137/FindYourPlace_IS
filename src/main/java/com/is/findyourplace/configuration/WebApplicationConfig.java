package com.is.findyourplace.configuration;

import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebApplicationConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(final ViewControllerRegistry registry) {
        registry.addViewController("/notFound").setViewName("forward:/error");
    }

    /**
     * Adds an error page for every 404 page.
     * @return Error Page corresponding to 404
     */
    @Bean
    public WebServerFactoryCustomizer<ConfigurableServletWebServerFactory>
    containerCustomizer() {
        return container ->
                container.addErrorPages(new ErrorPage(
                        HttpStatus.NOT_FOUND, "/notFound")
                );
    }
}
