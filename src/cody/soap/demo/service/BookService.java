package cody.soap.demo.service;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

@WebService
public interface BookService {

	//http://127.0.0.1:8080/test(工程名)/ws/bookService?wsdl
	
	@WebMethod(operationName = "helloWebService")  
	@WebResult(name = "result")  
	public String helloWebService(@WebParam(name = "ws") String ws);
}
