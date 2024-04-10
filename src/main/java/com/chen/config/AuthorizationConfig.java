package com.chen.config;

import com.chen.authorization.FederatedIdentityIdTokenCustomizer;
import com.chen.authorization.smsAuthentication.SmsCaptchaGrantAuthenticationConverter;
import com.chen.authorization.smsAuthentication.SmsCaptchaGrantAuthenticationProvider;

import com.chen.utils.util.CustomSecurityProperties;
import com.chen.utils.util.SecurityConstants;
import com.chen.utils.util.SecurityUtils;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.oauth2.jwt.JwtDecoder;

import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;

import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;

import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenGenerator;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.filter.CorsFilter;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.*;


@RequiredArgsConstructor
@Configuration(proxyBeanMethods = false)
public class AuthorizationConfig {

    private final CorsFilter corsFilter;
    private final CustomSecurityProperties customSecurityProperties;

    @Bean
    public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http,
                                                                      RegisteredClientRepository registeredClientRepository,
                                                                      AuthorizationServerSettings authorizationServerSettings) throws Exception{

        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);

        //基础认证配置
        SecurityUtils.applyBasicSecurity(http,corsFilter,customSecurityProperties);

        //设备码配置
        SecurityUtils.applyDeviceSecurity(http,customSecurityProperties,registeredClientRepository,authorizationServerSettings);

        //自定义短信认证登录转换器
        SmsCaptchaGrantAuthenticationConverter converter=new SmsCaptchaGrantAuthenticationConverter();

        //自定义短信认证登录认证提供
        SmsCaptchaGrantAuthenticationProvider provider=new SmsCaptchaGrantAuthenticationProvider();

        http.getConfigurer(OAuth2AuthorizationServerConfigurer.class)
                .tokenEndpoint(tokenEndpoint->tokenEndpoint
                        .accessTokenRequestConverter(converter)
                        .authenticationProvider(provider));

//
        DefaultSecurityFilterChain build=http.build();

//
        OAuth2TokenGenerator<?> tokenGenerator=http.getSharedObject(OAuth2TokenGenerator.class);
        AuthenticationManager authenticationManager=http.getSharedObject(AuthenticationManager.class);
        OAuth2AuthorizationService authorizationService=http.getSharedObject(OAuth2AuthorizationService.class);

        provider.setTokenGenerator(tokenGenerator);
        provider.setAuthorizationService(authorizationService);
        provider.setAuthenticationManager(authenticationManager);





        return build;
    }


    /**
     * 自定义Jwt ，将权限信息放入jwt中
     * @return
     */
    @Bean
    public OAuth2TokenCustomizer<JwtEncodingContext> oAuth2TokenCustomizer(){
        return new FederatedIdentityIdTokenCustomizer();
    }

    /**
     * 自定义jwt解析器，设置解析出来的权限信息前缀与在jwt中的key
     * @return
     */
    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter(){
        JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter=new JwtGrantedAuthoritiesConverter();

        grantedAuthoritiesConverter.setAuthorityPrefix("");

        grantedAuthoritiesConverter.setAuthoritiesClaimName(SecurityConstants.AUTHORITIES_KEY);

        JwtAuthenticationConverter jwtAuthenticationConverter=new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);
        return jwtAuthenticationConverter;

    }

    /**
     * 配置客户端Repository
     * @return
     */
//    @Bean
//    public RegisteredClientRepository registeredClientRepository(JdbcTemplate jdbcTemplate, PasswordEncoder passwordEncoder){
//        RegisteredClient registeredClient=RegisteredClient.withId(UUID.randomUUID().toString())
//                //客户端id
//                .clientId("messaging-client")
//                //客户端密钥
//                .clientSecret(passwordEncoder.encode("123456"))
//                //客户端认证方式，基于请求头的认证
//                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
//                //配置资源服务器使用该客户端获取授权时支持的方式
//                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
//                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
//                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
//                //客户端添加自定义认证
////                .authorizationGrantType(new AuthorizationGrantType(SecurityConstants.GRANT_TYPE_SMS_CODE))
//                //授权码模式回调地址，oauth2.1已改为精准匹配，不能只设置域名，并且屏蔽了localhost
//                .redirectUri("http://127.0.0.1:3000/Oauth2Redirect")
//                .redirectUri("http://127.0.0.1:8081/login/oauth2/code/messaging-client-oidc")
//
//                //该客户端的授权范围，OPENID与PROFILE是IdToken的scope
//                .scope(OidcScopes.OPENID)
//                .scope(OidcScopes.PROFILE)
//
//                .scope("message.read")
//                .scope("message.write")
//
//                .clientSettings(ClientSettings.builder().requireAuthorizationConsent(false).build())
//                .build();
//
//
//
//        //基于db存储客户端，还有一个基于内存的实现 InMemoryRegisteredClientRepository
//        JdbcRegisteredClientRepository registeredClientRepository=new JdbcRegisteredClientRepository(jdbcTemplate);
//
//        //初始化客户端
//        RegisteredClient repositoryByClientId=registeredClientRepository.findByClientId(registeredClient.getClientId());
//        if(repositoryByClientId==null){
//            registeredClientRepository.save(registeredClient);
//        }
//
////        //设备授权客户端
////        RegisteredClient deviceClient=RegisteredClient.withId(UUID.randomUUID().toString())
////                .clientId("device-message-client")
////
////                //公共客户端
////                .clientAuthenticationMethod(ClientAuthenticationMethod.NONE)
////                //设备授权码
////                .authorizationGrantType(AuthorizationGrantType.DEVICE_CODE)
////                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
////
////                .scope("message.read")
////                .scope("message.write")
////                .build();
////        RegisteredClient byClientId=registeredClientRepository.findByClientId(deviceClient.getClientId());
////        if(byClientId==null){
////            registeredClientRepository.save(deviceClient);
////        }
////
////        //PKCE客户端
////        RegisteredClient pkceClient=RegisteredClient.withId(UUID.randomUUID().toString())
////                .clientId("pkce-message-client")
////                //公共客户端
////                .clientAuthenticationMethod(ClientAuthenticationMethod.NONE)
////                //授权码模式，因为是扩展授权码流程，所以流程还是授权码的流程，改变的只是参数
////                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
////                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
////                //授权码模式回调地址，oauth2.1已改为精准匹配，不能只设置域名，并且屏蔽了localhost
////                .redirectUri("http://127.0.0.1:8081/login/oauth2/code/messaging-client-oidc")
////                .clientSettings(ClientSettings.builder().requireProofKey(Boolean.TRUE).build())
////
////                .scope("message.read")
////                .scope("message.write")
////
////                .build();
////
////        RegisteredClient findPkceClient=registeredClientRepository.findByClientId(pkceClient.getClientId());
////        if(findPkceClient==null){
////            registeredClientRepository.save(pkceClient);
////        }
//
//        return registeredClientRepository;
//
//    }
//
//
//
//
//    /**
//     * 基于db的oauth2的授权管理服务
//     * @param jdbcTemplate
//     * @param registeredClientRepository
//     * @return
//     */
//    @Bean
//    public OAuth2AuthorizationService authorizationService(JdbcTemplate jdbcTemplate, RegisteredClientRepository registeredClientRepository){
//
//        return new JdbcOAuth2AuthorizationService(jdbcTemplate,registeredClientRepository);
//    }
//
//    /**
//     * 配置基于db的授权确认管理服务
//     * @param jdbcTemplate
//     * @param registeredClientRepository
//     * @return
//     */
//    @Bean
//    public OAuth2AuthorizationConsentService authorizationConsentService(JdbcTemplate jdbcTemplate, RegisteredClientRepository registeredClientRepository){
//
//        return new JdbcOAuth2AuthorizationConsentService(jdbcTemplate,registeredClientRepository);
//    }


    @Bean
    @SneakyThrows
    public JWKSource<SecurityContext> jwkSource(){
        KeyPair keyPair=generateRsaKey();
        RSAPublicKey publicKey=(RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey=(RSAPrivateKey) keyPair.getPrivate();
        RSAKey rsaKey=new RSAKey.Builder(publicKey)
                .privateKey(privateKey)
                .keyID(UUID.randomUUID().toString())
                .build();
        JWKSet jwkSet=new JWKSet(rsaKey);
        return new ImmutableJWKSet<>(jwkSet);
    }


    /**
     * 生成rsa密钥对，提供给jwk
     * @return
     */
    private static KeyPair generateRsaKey(){
        KeyPair keyPair;
        try{
            KeyPairGenerator keyPairGenerator=KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            keyPair=keyPairGenerator.generateKeyPair();
        }catch (Exception e){
            throw new IllegalStateException(e);
        }
        return keyPair;
    }

    /**
     * jwt解析器
     * @param jwkSource
     * @return
     */
    @Bean
    public JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource){
        return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
    }


    @Bean
    @SneakyThrows
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){return new BCryptPasswordEncoder();
    }

}
