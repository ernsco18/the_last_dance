package ernesto.the_last_dance.security;

import ernesto.the_last_dance.entities.Utente;
import ernesto.the_last_dance.exceptions.UnauthorizedException;
import ernesto.the_last_dance.services.UtentiService;
import ernesto.the_last_dance.security.JWTools;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Component
public class JWTTCheckerFilter extends OncePerRequestFilter {
    private final JWTools jwtTools;
    private final UtentiService utentiService;

    public JWTTCheckerFilter(JWTools jwtTolls, UtentiService utentiService) {
        this.jwtTools = jwtTolls;
        this.utentiService = utentiService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authorization = request.getHeader("Authorization");
        if (authorization == null || !authorization.startsWith("Bearer")) {
            throw new UnauthorizedException("Inserire un token valido nell'header");
        }
        String accessToken = authorization.replace("Bearer ", "");
        this.jwtTools.verifyToken(accessToken);

        UUID utenteId = this.jwtTools.getIdByToken(accessToken);
        Utente utente = this.utentiService.findById(utenteId);

        Authentication authentication = new UsernamePasswordAuthenticationToken(utente, null, utente.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return new AntPathMatcher().match("/auth/**", request.getServletPath());
    }
}
