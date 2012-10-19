/**
 * 
 */
package org.eclipsecon.demo.hello.rest;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

/**
 * @author xcoulon
 *
 */
@Produces(MediaType.APPLICATION_JSON)
@Provider
public class HashMapMessageBodyWriter implements MessageBodyWriter<Map<String, Object>> {

	@Override
	public boolean isWriteable(Class<?> type, Type genericType,
			Annotation[] annotations, MediaType mediaType) {
		return (genericType instanceof ParameterizedType) && 
				((ParameterizedType)genericType).getRawType().equals(Map.class);
	}

	@Override
	public long getSize(Map<String, Object> t, Class<?> type, Type genericType,
			Annotation[] annotations, MediaType mediaType) {
		return -1;
	}

	@Override
	public void writeTo(Map<String, Object> t, Class<?> type, Type genericType,
			Annotation[] annotations, MediaType mediaType,
			MultivaluedMap<String, Object> httpHeaders,
			OutputStream entityStream) throws IOException,
			WebApplicationException {
		entityStream.write("{".getBytes("UTF-8"));
		for(Iterator<Entry<String, Object>> iterator=  t.entrySet().iterator(); iterator.hasNext();) {
			Entry<String, Object> entry = iterator.next();
			entityStream.write(entry.getKey().getBytes("UTF-8"));
			entityStream.write(":".getBytes("UTF-8"));
			if(entry.getValue() != null) {
			entityStream.write(entry.getValue().toString().getBytes("UTF-8"));
			} else {
				entityStream.write("null".getBytes("UTF-8"));
			}
			if(iterator.hasNext()) {
				entityStream.write(",".getBytes("UTF-8"));
			}
		}
		entityStream.write("}".getBytes("UTF-8"));
	}

}
