package com.chen.authorization.deviceAuthentication;

import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2ErrorCodes;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.security.web.util.matcher.AndRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.StringUtils;

public class DeviceClientAuthenticationConverter implements AuthenticationConverter {
    private final RequestMatcher deviceAuthorizationRequestMatcher;
    private final RequestMatcher deviceAccessTokenRequestMatcher;


    public DeviceClientAuthenticationConverter(String deviceAuthorizationEndpointUrl) {
        RequestMatcher clientIdParameterMatcher = request ->
                request.getParameter(OAuth2ParameterNames.CLIENT_ID)!=null;
        this.deviceAuthorizationRequestMatcher=new AndRequestMatcher(
                new AntPathRequestMatcher(
                        deviceAuthorizationEndpointUrl, HttpMethod.POST.name()),
                clientIdParameterMatcher
                );
        this.deviceAccessTokenRequestMatcher=request ->
                AuthorizationGrantType.DEVICE_CODE.getValue().equals(request.getParameter(OAuth2ParameterNames.GRANT_TYPE))&&
                        request.getParameter(OAuth2ParameterNames.DEVICE_CODE)!=null&&
                        request.getParameter(OAuth2ParameterNames.CLIENT_ID)!=null;

    }

    @Nullable
    @Override
    public Authentication convert(HttpServletRequest request) {
        if(!this.deviceAuthorizationRequestMatcher.matches(request)&&
            !this.deviceAccessTokenRequestMatcher.matches(request)){
            return null;
        }

        String clientId=request.getParameter(OAuth2ParameterNames.CLIENT_ID);
        if(!StringUtils.hasText(clientId) || request.getParameterValues(OAuth2ParameterNames.CLIENT_ID).length!=1){
            throw new OAuth2AuthenticationException(OAuth2ErrorCodes.INVALID_REQUEST);
        }

        return new DeviceClientAuthenticationToken(clientId, ClientAuthenticationMethod.NONE,null,null);
    }
}
