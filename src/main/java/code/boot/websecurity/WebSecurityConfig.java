package code.boot.websecurity;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
    public HttpFirewall looseHttpFirewall() {
        StrictHttpFirewall firewall = new StrictHttpFirewall();
        firewall.setAllowedHttpMethods(Arrays.asList("GET", "POST","PUT","CONNECT","HEAD", "DELETE"));
//        firewall.setAllowSemicolon(true);
//        firewall.setAllowUrlEncodedSlash(true);
//        firewall.setAllowBackSlash(true);
//        firewall.setAllowUrlEncodedPercent(true);
//        firewall.setAllowUrlEncodedPeriod(true);
        return firewall;
    }
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.formLogin().loginProcessingUrl("/login").loginPage("/login.html").usernameParameter("email").and().csrf()
				.disable().authorizeRequests().antMatchers("/api/user/**").permitAll()
				.antMatchers("/api/user/**").permitAll().antMatchers("/api/user/**").permitAll()
				.antMatchers("*.html").permitAll().anyRequest().authenticated().and().exceptionHandling()
				.and().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		// http.addFilterBefore(authenticationJwtTokenFilter(),
		// UsernamePasswordAuthenticationFilter.class);
	}
	
}
