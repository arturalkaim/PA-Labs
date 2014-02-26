package pt.ist.ap.labs;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Locale;
import java.io.*;

public class App1{

	public static void main(String[] args) {
		try{
			BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
			String className = in.readLine();
			Class<?> c = Class.forName(className);
 			Object t = c.newInstance();
 			System.out.println("Class: " +  c.getCanonicalName());
			

			Method[] allMethods = c.getDeclaredMethods();
			for (Method m : allMethods) {
				String mname = m.getName();
				
				System.out.println("invoking " + mname);
				try {
				    m.setAccessible(true);
				    Object o = m.invoke(t);

				// Handle any exceptions thrown by method to be invoked.
				} catch (InvocationTargetException x) {
				    Throwable cause = x.getCause();
				    System.err.println("invocation of " + mname + " failed: " + cause.getMessage());
				}
			}

 		} catch (ClassNotFoundException x) {
	    	x.printStackTrace();
		} catch (InstantiationException x) {
	    	x.printStackTrace();
		} catch (IllegalAccessException x) {
	    	x.printStackTrace();
		} catch (IOException x) {
	    	x.printStackTrace();
		}

	}
	
}