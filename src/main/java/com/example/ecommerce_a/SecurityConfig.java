package com.example.ecommerce_a;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * SpringSecurityの設定を行う.
 * 
 * @author yuki.maekawa
 *
 */
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService memberDetailService;

	/**
	 *静的リソースを許可する.
	 */
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/css/**", "/img/**", "/js/**", "/fonts/**");
	}

	/**
	 * 認証の有無でアクセスの可否を決定する.
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
		        .antMatchers("/toLogin/", "/regist/", "/regist/regist", "/item/**","/detail/**","/cart/**","/order/**","/user/**").permitAll()
		        .anyRequest().authenticated();
        
        http.formLogin()
        		.loginPage("/toLogin/")
        		.loginProcessingUrl("/login")
        		.failureUrl("/?error=true")
        		.defaultSuccessUrl("/item/showList", true)
        		.usernameParameter("mailAddress")
        		.passwordParameter("password");
        
        http.logout()
        		.logoutRequestMatcher(new AntPathRequestMatcher("/logout**"))
        		.logoutSuccessUrl("/item/showList")
        		.deleteCookies("JSESSIONID")
        		.invalidateHttpSession(true);
//		http.formLogin().disable();

	}

	/**
	 * ハッシュ化されたパスワードの照合.
	 */
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(memberDetailService).passwordEncoder(new BCryptPasswordEncoder());
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
