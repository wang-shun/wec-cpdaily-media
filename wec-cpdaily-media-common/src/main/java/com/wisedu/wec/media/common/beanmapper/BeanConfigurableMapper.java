package com.wisedu.wec.media.common.beanmapper;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import ma.glasnost.orika.Converter;
import ma.glasnost.orika.Mapper;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;

@Component
public class BeanConfigurableMapper extends ConfigurableMapper {

	private ApplicationContext context;

	private MapperFactory factory;

    @Autowired
	public void setApplicationContext(ApplicationContext applicationContext) {
		this.context = applicationContext;
		addConverters();
		addMappers();
	}

	@Override
	protected void configure(MapperFactory factory) {
		super.configure(factory);
		this.factory = factory;
	}

	@SuppressWarnings("rawtypes")
	private void addConverters() {
		final Map<String, Converter> converters = context.getBeansOfType(Converter.class);
		for (final Converter<?, ?> converter : converters.values()) {
			addConverter(converter);
		}
	}

	@SuppressWarnings("rawtypes")
	private void addMappers() {
		final Map<String, Mapper> mappers = context.getBeansOfType(Mapper.class);
		for (final Mapper<?, ?> mapper : mappers.values()) {
			addMapper(mapper);
		}
	}

	private void addConverter(final Converter<?, ?> converter) {
		this.factory.getConverterFactory().registerConverter(converter);
	}

	private void addMapper(final Mapper<?, ?> mapper) {
		this.factory.registerMapper(mapper);
	}

}

