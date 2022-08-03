package bg.softuni.pochivka.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ui.context.ThemeSource;
import org.springframework.ui.context.support.ResourceBundleThemeSource;
import org.springframework.web.servlet.ThemeResolver;
import org.springframework.web.servlet.theme.CookieThemeResolver;
import org.springframework.web.servlet.theme.ThemeChangeInterceptor;

@Configuration
public class ThemeConfiguration {

    @Bean
    public ThemeResolver themeResolver() {
        CookieThemeResolver ctr = new CookieThemeResolver();
        ctr.setCookieName("theme");
        ctr.setDefaultThemeName("default");
        return ctr;
    }

    @Bean
    public ThemeChangeInterceptor themeChangeInterceptor() {
        ThemeChangeInterceptor tci = new ThemeChangeInterceptor();
        tci.setParamName("theme");
        return tci;
    }

    @Bean
    public ResourceBundleThemeSource resourceBundleThemeSource() {
        ResourceBundleThemeSource resourceBundleThemeSource = new ResourceBundleThemeSource();
        resourceBundleThemeSource.setDefaultEncoding("UTF-8");
        resourceBundleThemeSource.setBasenamePrefix("themes");
        return resourceBundleThemeSource;
    }
}
