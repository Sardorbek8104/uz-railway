package uz.pdp.bookingservice.controller.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import uz.pdp.bookingservice.controller.dto.TokenValidationResponseDto;
import uz.pdp.bookingservice.service.JwtService;
import uz.pdp.bookingservice.service.UserService;

import java.io.IOException;
import java.time.Duration;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenValidationFilter extends OncePerRequestFilter {
    private final UserService userService;
    private final RedisTemplate<String, TokenValidationResponseDto> redisTemplate;
    private final JwtService jwtService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        String token = authorization.substring(7);
        String username = jwtService.extractUsernameFromToken(token);
        TokenValidationResponseDto userData = redisTemplate.opsForValue().get(username);

        if (userData == null) {
            log.info("Redis empty and request is sending to user-service to validate token");
            userData = userService.validateToken(token);
            if (userData == null) {
                filterChain.doFilter(request, response);
                return;
            }
            redisTemplate.opsForValue().set(username, userData, Duration.ofSeconds(120));
        }

        log.info("Redis is not empty and request is not sending to user-service to validate token");

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userData.getUsername(),
                null,
                userData.getAuthorities().stream().map(SimpleGrantedAuthority::new).collect(Collectors.toSet())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }
}
