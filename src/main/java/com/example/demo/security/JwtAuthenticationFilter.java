package com.example.demo.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{ // OncePerRequestFilter : 한 요청당 반드시 한 번만 실행
	
	@Autowired
	private TokenProvider tokenProvider;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
			// 요청해서 토큰 가져오기 
			String token = parseBearerToken(request);
			log.info("=====Filter is running====");
			// 토큰 검사하기. JWT이므로 인가 서버에 요청하지 않고도 검증 가능
			if(token != null && !token.equalsIgnoreCase("null")) { // 토큰인 있는 경우
				String userId = tokenProvider.validateAndGetUserId(token);// userId 가져오기. 위조된 경우 예외 처리된다.
				log.info("=== Authenticated user ID : " + userId);
				AbstractAuthenticationToken authentication = new UsernamePasswordAuthenticationToken( // 인증완료. SecurityContextHolder에 등록해야 인증된 사용자
						userId,  // 인증된 사용자의 정보. 문자열이 아니어도 아무것이나 가능. UserDetails 오브젝트를 통상적으로 넣는다.
						null, 
						AuthorityUtils.NO_AUTHORITIES
						);
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
				securityContext.setAuthentication(authentication);;
				SecurityContextHolder.setContext(securityContext);
			}
		}catch (Exception e) {
			logger.error("Could not set user authentication in security context", e);
		}
		
		filterChain.doFilter(request, response); // 필터에 통과시킨다 	
	}
	
	private String parseBearerToken(HttpServletRequest request) { 
		String bearerToken = request.getHeader("Authorization"); //Http 요청의 헤더를 파싱해 Bearer 토큰을 리턴한다.
		
		if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7); // 앞에서 7글자 지운다. 
		}
		
		return null;
	}
}
