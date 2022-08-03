package bg.softuni.pochivka.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.theme.ThemeChangeInterceptor;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    private ThemeChangeInterceptor themeChangeInterceptor;

    public WebConfiguration(ThemeChangeInterceptor themeChangeInterceptor) {
        this.themeChangeInterceptor = themeChangeInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(themeChangeInterceptor);
    }
}
