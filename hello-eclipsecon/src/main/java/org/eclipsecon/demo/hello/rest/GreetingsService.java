/**
 * JBoss, Home of Professional Open Source
 * Copyright 2012, Red Hat, Inc., and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.eclipsecon.demo.hello.rest;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

@Path("/greetings")
public class GreetingsService {
	
	@Context 
	ServletContext context;
	
    @GET
    @Path("/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> sayHi(@PathParam("name") String name) {
    	long start = System.currentTimeMillis();    
        name = (name == null)?"World":name;
        
        String mode = context.getInitParameter("mode");
        boolean debug = "debug".equals(mode);
		Map<String, Object> result = new HashMap<String, Object>();
        result.put("greeting", "Hello "+name+"!!!");
        result.put("mode", mode);
       	if (debug) {
       		result.put("memory", getMemoryUsed());
        }
       	long elapsed = System.currentTimeMillis() - start;
       	result.put("elapsed", elapsed);
    	return result ;
    }
    
	private long getMemoryUsed() {
		Runtime runtime = Runtime.getRuntime();  
		return runtime.totalMemory () - runtime.freeMemory ();
	}
}