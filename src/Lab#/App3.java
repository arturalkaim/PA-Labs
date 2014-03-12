import java.lang.reflect.*;

public class App3 {
	static int passed = 0, failed = 0;
	
	public static void main(String[] args) throws Exception {
		xpto(args[0]);
		System.out.printf("Passed: %d, Failed %d%n", passed, failed);
	}

	public static void xpto(String arg) throws ClassNotFoundException {
		if(!Class.forName(arg).getSuperclass().isInstance(new Object())){
			xpto(Class.forName(arg).getSuperclass().getCanonicalName());
		}
		for (Method m : Class.forName(arg).getDeclaredMethods()) {
			if (m.isAnnotationPresent(Test.class) && Modifier.isStatic(m.getModifiers())) {
				try {
					String[] val = m.getAnnotation(Test.class).value();

					System.out.println("Metodo: " + m.getName())		;
					
					for (Method n : Class.forName(arg).getSuperclass().getDeclaredMethods()) {

						if (n.isAnnotationPresent(Setup.class)) {
							for (String s : val) {
								if (s.equalsIgnoreCase("*")
										|| n.getAnnotation(Setup.class).value()
												.equalsIgnoreCase(s)) {
									n.setAccessible(true);
									n.invoke(null);
								}

							}
						}

					}
					
					for (Method n : Class.forName(arg).getDeclaredMethods()) {

						if (n.isAnnotationPresent(Setup.class)) {
							for (String s : val) {
								if (s.equalsIgnoreCase("*")
										|| n.getAnnotation(Setup.class).value()
												.equalsIgnoreCase(s)) {
									n.setAccessible(true);
									n.invoke(null);
								}

							}
						}

					}

					m.setAccessible(true);
					m.invoke(null);
					passed++;
				} catch (Throwable ex) {
					System.out
							.printf("Test %s failed: %s %n", m, ex.getCause());
					failed++;
				}
			}
		}
	}
}