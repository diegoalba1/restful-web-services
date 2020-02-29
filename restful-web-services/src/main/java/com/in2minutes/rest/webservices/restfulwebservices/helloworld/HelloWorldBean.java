package com.in2minutes.rest.webservices.restfulwebservices.helloworld;

import org.springframework.stereotype.Component;


public class HelloWorldBean {

	private String name;
	
	public HelloWorldBean(String theName) {
		this.name=theName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "HelloWorldBean [name=" + name + "]";
	}
	
}
