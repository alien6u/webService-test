package cody.soap.demo.service.impl;

import javax.jws.WebService;

import cody.soap.demo.service.BookService;

@WebService(endpointInterface = "cody.soap.demo.service.BookService")  
public class BookServiceImpl implements BookService {

	public String helloWebService(String ws) {
		// TODO Auto-generated method stub
		return "hello webservice ,this is my first webservice. this is " + ws;
	}

}
