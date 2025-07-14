package medatt.com.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


//class to enable all the http endpoints .


@Configuration
public class ActuatorConfig extends WebSecurityConfigurerAdapter {


    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.requestMatcher(EndpointRequest.toAnyEndpoint()).authorizeRequests()
                .anyRequest().permitAll();
    }
}
