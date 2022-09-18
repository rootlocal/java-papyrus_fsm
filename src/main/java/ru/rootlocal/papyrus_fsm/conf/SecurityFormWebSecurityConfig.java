package ru.rootlocal.papyrus_fsm.conf;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.rootlocal.papyrus_fsm.jpa.PrivilegeEnum;

@Configuration
@Profile(value = {"!test-jpa"})
@EnableWebSecurity
@Order(102)
public class SecurityFormWebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;


    @Autowired
    public SecurityFormWebSecurityConfig(PasswordEncoder passwordEncoder, UserDetailsService userDetailsService) {
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void configure(@NotNull AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(this.userDetailsService)
                .passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(@NotNull final HttpSecurity http) throws Exception {
        // CSRF & CORS
        http.csrf().disable();
        http.cors();

        http.antMatcher("/**")
                .authorizeRequests()
                .antMatchers("/user/**").hasAuthority(
                        PrivilegeEnum.WRITE_PRIVILEGE.name()
                )
                .antMatchers("/date").hasAnyAuthority(
                        PrivilegeEnum.READ_PRIVILEGE.name(),
                        PrivilegeEnum.BOT_PRIVILEGE.name()
                )
                .antMatchers("/", "/assets/**", "/webjars/**", "/error")
                .permitAll()
                .and()

                .formLogin()
                .defaultSuccessUrl("/", true)
                //.loginPage("/login")
                .permitAll()

                .and().logout().logoutSuccessUrl("/").permitAll();
    }
}