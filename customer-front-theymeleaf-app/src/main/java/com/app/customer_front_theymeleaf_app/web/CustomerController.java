package com.app.customer_front_theymeleaf_app.web;

import com.app.customer_front_theymeleaf_app.entities.Customer;
import com.app.customer_front_theymeleaf_app.model.Product;
import com.app.customer_front_theymeleaf_app.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class CustomerController {

    private CustomerRepository customerRepository;

    @Autowired
    private ClientRegistrationRepository clientRegistrationRepository;

    public CustomerController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @GetMapping("/customers")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String customer(Model model){
        List<Customer> customersList = customerRepository.findAll();
        model.addAttribute("customers",customersList);
        return "customers";
    }

    @GetMapping("/products")
    public String products(Model model){
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        OAuth2AuthenticationToken oAuth2AuthenticationToken= (OAuth2AuthenticationToken) authentication;
        DefaultOidcUser oidcUser = (DefaultOidcUser) oAuth2AuthenticationToken.getPrincipal();
        String jwtTokenValue=oidcUser.getIdToken().getTokenValue();
        RestClient restClient = RestClient.create("http://localhost:8585");
        List<Product> products = restClient.get()
                .uri("/products")
                .headers(httpHeaders -> httpHeaders.set(HttpHeaders.AUTHORIZATION, "Bearer "+jwtTokenValue))
                .retrieve()
                .body(new ParameterizedTypeReference<>(){});
        model.addAttribute("products",products);

        return "products";
    }

    @GetMapping("/auth")
    @ResponseBody
    public Authentication authentication(Authentication authentication){
        return authentication;
    }

    @GetMapping("/index")
    public String index(){

        return "index";
    }

    @GetMapping("/notAuthorized")
    public String notAuthorized (){

        return "notAuthorized";
    }

    @GetMapping("/oauth2login")
    public String oauth2Login(Model model) {
        String authorizationRequestBaseUri = "oauth2/authorization";
        Map<String, String> oauth2AuthenticationUrls = new HashMap();
        Iterable<ClientRegistration> clientRegistrations = (Iterable<ClientRegistration>) clientRegistrationRepository;
        clientRegistrations.forEach(registration ->{
            oauth2AuthenticationUrls.put(registration.getClientName(),
                    authorizationRequestBaseUri + "/" + registration.getRegistrationId());
        });
        model.addAttribute("urls", oauth2AuthenticationUrls);
        return "oauth2Login";
    }
}
