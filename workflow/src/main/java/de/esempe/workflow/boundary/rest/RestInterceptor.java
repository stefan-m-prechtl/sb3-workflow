package de.esempe.workflow.boundary.rest;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class RestInterceptor implements HandlerInterceptor
{
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception
	{
		// Logic before the controller method is called
		System.out.println("Pre-handle:" + request.getRequestURI());
		return true; // Return false to block the request
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception
	{
		// Logic after the controller method is executed but before the view is rendered
		System.out.println("Post-handle logic executed");
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception
	{
		// Logic after the complete request is finished
		System.out.println("After completion logic executed");

		final int status = response.getStatus();
		System.out.println("After completion logic executed with state: " + status);

	}
}
