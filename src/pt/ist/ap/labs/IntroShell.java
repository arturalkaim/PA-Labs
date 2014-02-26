package pt.ist.ap.labs;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.*;
import java.io.*;

class IntroShell {


	private TreeMap<String,Object> commands = new TreeMap<String,Object>();

	void execute() throws IOException {
		Boolean go = true;	
			
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    	Class<?> c = null;
    	String cmd;
		while (go) {
			System.out.print("sish:>");
		    cmd =in.readLine();

  			if (cmd.equals("") || cmd.equalsIgnoreCase("help") || cmd.equalsIgnoreCase("h")) {
  				System.out.println();
  				System.out.println("Simple introspection shell");
  				System.out.println();
  				System.out.println("*********************************");
  				System.out.println();
  				System.out.println("help - to see this message");
  				System.out.println("Class <name> 	: obtain instance of Class<name>");
  				System.out.println("Set <name> 		: save object from last result with name <name>");
  				System.out.println("Get <name> 		: select object previously saved with <name>");
  				System.out.println("Index <int> 	: select object within an array. The array should have been obtained as the result of the previous command.");
  				System.out.println("method [args] 	: try to invoke the method on the result of the previous command");
  				System.out.println("exit or q - to exit");  
   				System.out.println();
  				System.out.println("*********************************");
  				System.out.println();				
  				continue;
  			} 
			if (cmd.equalsIgnoreCase("exit") || cmd.equalsIgnoreCase("q")) {
  				System.out.println("Goodbye");
  				System.exit(0);
			}

			if(cmd.startsWith("Class")){
				String args[] = cmd.split(" ");
				try{
					c = Class.forName(args[1]);
		 			
		 			System.out.println("Class: " +  c.getCanonicalName());
					
				} catch (ClassNotFoundException x) {
			    	x.printStackTrace();
				} 
				continue;				
					
			}

			if(cmd.startsWith("Set")){
				String args[] = cmd.split(" ");

				
					commands.put(args[1],c);
					commands.put("last",c);
					System.out.println("Saved name for object of type: class " + c.getCanonicalName());
					System.out.println("Class: " +  c.getCanonicalName());
				continue;
			}
			if(cmd.startsWith("Get")){
				String args[] = cmd.split(" ");

				try{
					
					commands.put("last",commands.get(args[1]));

					c = (Class<?>) commands.get("last");
					System.out.println("class " + c.getCanonicalName());


				} catch (Exception x) {
			    	x.printStackTrace();
				}
				continue;
			}

			if(cmd.startsWith("Index")){
				String args[] = cmd.split(" ");
				try{
					int index = Integer.parseInt(args[1]);

					c = (Class<?>) commands.get("last");
					if(c.isArray()){
						
					}
					System.out.println("class " + c.getCanonicalName());


				} catch (Exception x) {
			    	x.printStackTrace();
				}
				continue;
			} else{

				String args[] = cmd.split(" ");
				
				System.out.println("Trying generic command: " + cmd);

				try{
					Method[] allMethods = c.getDeclaredMethods();

					for (Method m : allMethods) {
						String mname = m.getName();
						if (mname.equalsIgnoreCase(args[0])) {

							try {
							    m.setAccessible(true);
							    Object o = m.invoke(c);

							    commands.put("result",o);

								System.out.println(o);
							// Handle any exceptions thrown by method to be invoked.
							} catch (InvocationTargetException x) {
							    Throwable cause = x.getCause();
							    System.err.println("invocation of " + mname + " failed: " + cause.getMessage());
							}
						}else {
						}

					}
				} catch (Exception x) {
			    	x.printStackTrace();
				}

			}




		}
	}
}