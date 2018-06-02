package com.longmaple.oauth2.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientRegistrationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.longmaple.oauth2.clientreg.ClientRegistrationRequest;
import com.longmaple.oauth2.clientreg.ClientRegistrationResponse;
import com.longmaple.oauth2.clientreg.DynamicClientDetails;
import com.longmaple.oauth2.clientreg.DynamicClientDetailsFactory;
import com.longmaple.oauth2.clientreg.RedirectFlowSpecification;
import com.longmaple.oauth2.clientreg.RegistrationError;

@RestController
public class DynamicClientRegistrationController {

    @Autowired
    private ClientRegistrationService clientRegistration;

    @Autowired
    private DynamicClientDetailsFactory clientDetailsFactory;

    @Autowired
    private RedirectFlowSpecification redirectFlowSpecification;

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody ClientRegistrationRequest clientMetadata) {

        if (!redirectFlowSpecification.isSatisfiedBy(clientMetadata)) {
            RegistrationError error = new RegistrationError(RegistrationError.INVALID_REDIRECT_URI);
            error.setErrorDescription("You must specify redirect_uri when using flows with redirection");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        DynamicClientDetails clientDetails = clientDetailsFactory.create(clientMetadata);
        clientRegistration.addClientDetails(clientDetails);

        return new ResponseEntity<>(createResponse(clientDetails), HttpStatus.CREATED);
    }

    private ClientRegistrationResponse createResponse(DynamicClientDetails clientDetails) {
        ClientRegistrationResponse response = new ClientRegistrationResponse();
        response.setClientId(clientDetails.getClientId());
        response.setClientSecret(clientDetails.getClientSecret());
        response.setClientName(clientDetails.getClientName());
        response.setClientUri(clientDetails.getClientUri());
        response.setGrantTypes(clientDetails.getAuthorizedGrantTypes());
        response.setRedirectUris(clientDetails.getRegisteredRedirectUri());
        response.setResponseTypes(clientDetails.getResponseTypes());
        response.setScope(clientDetails.getScope().stream().reduce((a, b) -> a + " " + b).get());
        response.setSoftwareId(clientDetails.getSoftwareId());
        response.setTokenEndpointAuthMethod(clientDetails.getTokenEndpointAuthMethod());
        response.setClientSecretExpiresAt(clientDetails.getClientSecretExpiresAt());
        return response;
    }

    @GetMapping("/clients")
    public ResponseEntity<List<ClientDetails>> list() {
        return new ResponseEntity<>(clientRegistration.listClientDetails(), HttpStatus.OK);
    }

}
