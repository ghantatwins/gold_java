package main;
import java.io.File;
import java.io.IOException;

import api.IApi;
import buysell.ProfitMaker;
import goldapi.Api;

public class Main {

	public Main() {
		
	}

	public static void main(String[] args) throws IOException {
		
		
		File directory = new File("data.csv");
		   
		   String fileName=directory.getAbsolutePath();
		   System.out.println(fileName);
		IApi api= new Api(fileName);
		ProfitMaker maker= new ProfitMaker();
		double profit=maker.GetProfitValue(api);
		
		System.out.println("Expected is "+profit);
	}
	/*
	 * private File GetFileFromResources(String fileName) {
	 * 
	 * ClassLoader classLoader = getClass().getClassLoader();
	 * 
	 * java.net.URL resource = classLoader.getResource(fileName);
	 * 
	 * if (resource == null) { throw new
	 * IllegalArgumentException("file is not found!"); } else { return new
	 * File(resource.getFile()); }
	 * 
	 * }
	 */

}
