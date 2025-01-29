package org.example.loginspring_adriansaavedra.ui.filters;

import org.example.loginspring_adriansaavedra.common.Constantes;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class ConfigurationFilter {

    @Bean
    public FilterRegistrationBean<SessionFilter> sessionFilter() {
        FilterRegistrationBean<SessionFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new SessionFilter());
        registrationBean.addUrlPatterns(Constantes.PLAYERS_BEANS, Constantes.UPDATE_BEANS);
        return registrationBean;
    }
}