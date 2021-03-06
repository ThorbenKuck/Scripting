package com.github.thorbenkuck.scripting.test;

import com.github.thorbenkuck.scripting.parsing.Parser;
import com.github.thorbenkuck.scripting.script.Script;
import com.github.thorbenkuck.scripting.exceptions.ExecutionFailedException;
import com.github.thorbenkuck.scripting.exceptions.ParsingFailedException;
import com.github.thorbenkuck.scripting.io.IOModule;
import com.github.thorbenkuck.scripting.math.MathModule;
import com.github.thorbenkuck.scripting.packages.Package;
import com.github.thorbenkuck.scripting.system.SystemModule;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class ScriptingTest {

	private static String toEvaluate = "var y = subtract(add(add(add(x, x), x), add(x, x), add(x, x)), add(x, x), x);" +
	        "require(y);println(\"y=\", y);" +
	        "println(\"y^2=\", y, \"^2=\", pow(y, 2));" +
	        "--y;println(\"y=\", y);" +
	        "println(\"y^3=\", y, \"^3=\", pow(y, 3));" +
			"println(x, \"+\", x, \"=\", add(x, x));" +
			"println(\"x=\", x);";

	private static String toEvaluateHarder =
			"println(\"DEMO-SCRIPT!\");" +
					"require(x);" +
					"convertToInt(x);" +
					"var y = x;" +
					"require(y);" +
					"loop i 1:5;" +
					"    loop j 1:3;" +
					"        println(\"i=\", i);" +
					"        println(\"j=\", j);" +
					"        println(\"y=\", y);" +
					"        ++y;" +
					"    endLoop j;" +
					"endLoop i;" +
					"println(\"x should still be what you selected: \", x);" +
					"println(\"i=\", i);" +
					"println(\"j=\", j);" +
					"var z;" +
					"println(\"z=\", z);" +
					"delete z;" +
					"println(\"z=\", z);" +
					"println(\"END\");";

	public static void main(String[] args) {
		Parser parser = new Parser();

		Package foundation = Package.build()
				.add(IOModule.getPackage())
				.add(SystemModule.getPackage())
				.add(MathModule.getPackage())
				.buildInMemory();

		parser.add(foundation);

		System.out.println("parsing executable script ..");
		Script script;
		try {
			script = parser.parse(toEvaluateHarder);
		} catch (ParsingFailedException e) {
			e.printStackTrace();
			return;
		}
		System.out.println("script parsed!");

		System.out.println();
		System.out.println(script);
		System.out.println();

		Scanner scanner = new Scanner(System.in);
		String entered;
		try {
			do {
				System.out.println();
				System.out.println();
				System.out.println();
				System.out.print("Set initial value of x to: ");
				entered = scanner.nextLine();
				if (!entered.equals("stop")) {

					System.out.println();
					script.setValue("x", entered);
					try {
						script.run();
					} catch (ExecutionFailedException e) {
						e.printStackTrace(System.out);
					}
				}
			} while (!entered.equals("stop"));
		} catch (IllegalStateException | NoSuchElementException e) {
			// System.in has been closed
			System.out.println("System.in was closed; exiting");
		}
	}
}
