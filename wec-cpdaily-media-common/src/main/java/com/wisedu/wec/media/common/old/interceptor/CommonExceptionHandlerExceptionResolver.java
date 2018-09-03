package com.wisedu.wec.media.common.old.interceptor;

import com.wisedu.wec.media.common.base.response.BaseRestResponse;
import com.wisedu.wec.media.common.exception.BusinessException;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class CommonExceptionHandlerExceptionResolver extends ExceptionHandlerExceptionResolver {

	private String errorDefaultView;

	@Override
	protected ModelAndView doResolveHandlerMethodException(HttpServletRequest request, HttpServletResponse response,
			HandlerMethod handlerMethod, Exception exception) {
		if (logger.isDebugEnabled()) {
			logger.debug("Resolving exception from handler: " + handlerMethod, exception);
		}
		if (exception instanceof BusinessException) {
			logger.warn("Invoking request method resulted in URI: " + request.getRequestURI(), exception);
		} else {
			logger.error("Invoking request method resulted in URI: " + request.getRequestURI(), exception);
		}
		ModelAndView returnValue = super.doResolveHandlerMethodException(request, response, handlerMethod, exception);
		if (containsResponseBodyAnnotation(handlerMethod)) {
			try {
				ResponseStatus responseStatus = AnnotatedElementUtils.findMergedAnnotation(handlerMethod.getMethod(),
						ResponseStatus.class);
				if (responseStatus != null) {
					HttpStatus statusCode = responseStatus.code();
					String reason = responseStatus.reason();
					if (!StringUtils.hasText(reason)) {
						response.setStatus(statusCode.value());
					} else {
						response.sendError(statusCode.value(), reason);
					}
				}
				if (returnValue == null) {
					//ResponseResult<?> responseResult = ResponseResult.failure(exception.getMessage());
					BaseRestResponse restResponse = new BaseRestResponse().error(exception.getMessage());
					handleReturnValue(restResponse, request, response);
					return new ModelAndView();
				}
				return handleReturnValue(returnValue.getModelMap(), request, response);
			} catch (Exception e) {
				logger.error("Invalid handler method return value: " + returnValue, e);
				return null;
			}
		}

		if (null == returnValue) {
			returnValue = new ModelAndView(getErrorDefaultView());
		}
		return returnValue;
	}

	@SuppressWarnings({ "unchecked", "rawtypes", "resource" })
	private ModelAndView handleReturnValue(Object returnValue, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpInputMessage inputMessage = new ServletServerHttpRequest(request);
		List<MediaType> acceptedMediaTypes = inputMessage.getHeaders().getAccept();
		if (acceptedMediaTypes.isEmpty()) {
			acceptedMediaTypes = Collections.singletonList(MediaType.ALL);
		}
		MediaType.sortByQualityValue(acceptedMediaTypes);
		HttpOutputMessage outputMessage = new ServletServerHttpResponse(response);
		Class<?> returnValueType = returnValue.getClass();
		List<HttpMessageConverter<?>> messageConverters = getMessageConverters();
		if (messageConverters != null) {
			for (MediaType acceptedMediaType : acceptedMediaTypes) {
				for (HttpMessageConverter messageConverter : messageConverters) {
					if (messageConverter.canWrite(returnValueType, acceptedMediaType)) {
						messageConverter.write(returnValue, acceptedMediaType, outputMessage);
						return new ModelAndView();
					}
				}
			}
		}
		if (logger.isWarnEnabled()) {
			logger.warn("Could not find HttpMessageConverter that supports return type [" + returnValueType + "] and "
					+ acceptedMediaTypes);
		}
		return null;
	}

	private boolean containsResponseBodyAnnotation(HandlerMethod handlerMethod) {
		return handlerMethod.getMethodAnnotation(ResponseBody.class) != null
				|| handlerMethod.getBeanType().getAnnotation(RestController.class) != null;
	}

	public String getErrorDefaultView() {
		return errorDefaultView;
	}

	public void setErrorDefaultView(String errorDefaultView) {
		this.errorDefaultView = errorDefaultView;
	}

}
