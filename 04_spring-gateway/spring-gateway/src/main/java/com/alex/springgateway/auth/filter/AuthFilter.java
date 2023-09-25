package com.alex.springgateway.auth.filter;

import com.alex.springgateway.auth.dto.TokenDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;


@Component
@RequiredArgsConstructor
public class AuthFilter implements GatewayFilter {

    @Value("${ms.alx.auth.base-url}")
    private String authMSUrl;

    private final WebClient.Builder webClientBuilder;


    public Mono<Void> onError(ServerWebExchange exchange, HttpStatus status) {
        ServerHttpResponse response = exchange.getResponse();

        response.setStatusCode(status);

        return response.setComplete();
    }


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // validate Authorization header
        if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION))
            return onError(exchange, HttpStatus.BAD_REQUEST);

        // get bearer token
        String authToken = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
        String[] chunks = authToken.split(" ");
        if (chunks.length != 2 || !chunks[0].equals("Bearer")) return onError(exchange, HttpStatus.BAD_REQUEST);

        // build a req to auth ms
        return webClientBuilder.build().post().uri(authMSUrl.concat("/validate?=token=").concat(chunks[1]))
                .retrieve().bodyToMono(TokenDto.class)
                .map(t -> {
                    return exchange;
                })
                .flatMap(chain::filter);
    }

}
