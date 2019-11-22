package com.tensquare;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import util.JwtUtil;

import javax.servlet.http.HttpServletRequest;

@Component
public class WebFilter extends ZuulFilter {
    @Autowired
    private JwtUtil jwtUtil;
    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return false;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext context = RequestContext.getCurrentContext();
        HttpServletRequest request = context.getRequest();
        if(request.getMethod().equals("OPTIONS")){
            return null;
        }

        String url = request.getRequestURL().toString();
        if(url.indexOf("/login")>0){
            return null;
        }

        String authorization = request.getHeader("Authorization");
        if(authorization !=null && authorization.startsWith("Bearer ")){
            String token = authorization.substring(7);
            Claims claims = jwtUtil.parseJWT(token);
            if(claims!=null){
                if ("admin".equals(claims.get("roles"))){
                    context.addZuulRequestHeader("Authorization",authorization);
                }
            }
        }

        context.setSendZuulResponse(false);//终止运行
        context.setResponseStatusCode(401);//http状态码
        context.setResponseBody("无权访问");
        context.getResponse().setContentType("text/html;charset=UTF-8");

        return null;
    }
}
