package ${basePackageName};

import com.github.peacetrue.core.OperatorCapableImpl;
import com.github.peacetrue.spring.formatter.date.AutomaticDateFormatter;
import com.github.peacetrue.spring.web.method.support.HandlerMethodArgumentConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.core.MethodParameter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.annotation.ModelAttributeMethodProcessor;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.servlet.ServletRequest;
import java.util.List;

/**
 * @author xiayx
 */
@SpringBootApplication
public class ${name}Application {

    public static void main(String[] args) {
        SpringApplication.run(${name}Application.class, args);
    }

    @Bean
    public AutomaticDateFormatter dateFormatter() {
        return new AutomaticDateFormatter();
    }

    @Bean
    public HandlerMethodArgumentConsumer handlerMethodArgumentConsumer(){
        return (HandlerMethodArgumentConsumer<OperatorCapableImpl>) object -> {
            object.setOperatorId(1);
            object.setOperatorName("1");
        };
    }

    @Bean
    public WebMvcConfigurer corsWebMvcConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowCredentials(true)
                        .allowedOrigins("*")
                        .allowedHeaders("*")
                        .allowedMethods("*");
            }

            @Override
            public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
                argumentResolvers.add(new ModelAttributeMethodProcessor(true) {
                    @Override
                    public boolean supportsParameter(MethodParameter parameter) {
                        return OperatorCapableImpl.class.isAssignableFrom(parameter.getParameterType());
                    }

                    @Override
                    protected void bindRequestParameters(WebDataBinder binder, NativeWebRequest request) {
                        ((ServletRequestDataBinder) binder).bind(request.getNativeRequest(ServletRequest.class));
                    }

                    @Override
                    protected Object createAttribute(String attributeName, MethodParameter parameter, WebDataBinderFactory binderFactory, NativeWebRequest webRequest) throws Exception {
                        OperatorCapableImpl attribute = (OperatorCapableImpl) super.createAttribute(attributeName, parameter, binderFactory, webRequest);
                        attribute.setOperatorId(1);
                        attribute.setOperatorName("1");
                        return attribute;
                    }
                });
            }
        };
    }

    @Bean
    public HttpMessageConverters httpMessageConverters(@Autowired List<HttpMessageConverter<?>> httpMessageConverters) {
        return new HttpMessageConverters(false, httpMessageConverters);
    }

    @ControllerAdvice
    public static class StringTrimmerControllerAdvice {
        @InitBinder
        public void registerCustomEditors(WebDataBinder binder) {
            // configure for empty string change to null
            binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
        }
    }
}
