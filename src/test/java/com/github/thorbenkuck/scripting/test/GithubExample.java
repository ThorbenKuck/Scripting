package com.github.thorbenkuck.scripting.test;

import com.github.thorbenkuck.scripting.*;
import com.github.thorbenkuck.scripting.components.Function;
import com.github.thorbenkuck.scripting.exceptions.ExecutionFailedException;
import com.github.thorbenkuck.scripting.exceptions.ParsingFailedException;
import com.github.thorbenkuck.scripting.exceptions.RuntimeExecutionException;
import com.github.thorbenkuck.scripting.math.MathModule;
import com.github.thorbenkuck.scripting.parsing.Parser;
import com.github.thorbenkuck.scripting.script.Script;
import com.github.thorbenkuck.scripting.system.VariableDefinitionRule;
import com.github.thorbenkuck.scripting.system.VariableInitializerRule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GithubExample {

	public static void main(String[] args) {
		GithubExample githubExample = new GithubExample();
		githubExample.run();
	}

	public void run() {
		final List<Double> results = new ArrayList<>();
		String rawScript = "var y = add(2, x);"
				+ "var result = pow(y, y);"
				+ "export(result);";

		Parser parser = new Parser();

		parser.add(new VariableInitializerRule());
		parser.add(new VariableDefinitionRule());
		parser.add(MathModule.getPackage());
		parser.add(new ExportFunction(results));

		try {
			Script script = parser.parse(rawScript);
			script.run(Collections.singletonMap("x", "1"));
			script.run(Collections.singletonMap("x", "2"));
			script.run(Collections.singletonMap("x", "3"));
		} catch (ParsingFailedException | ExecutionFailedException e) {
			e.printStackTrace();
		}
	}

	private class ExportFunction implements Function {

		private final List<Double> results;

		private ExportFunction(final List<Double> results) {
			this.results = results;
		}

		@Override
		public String getFunctionName() {
			return "export";
		}

		@Override
		public String calculate(final String[] args, final Register register) {
			if (! isDouble(args[0], register)) {
				throw new RuntimeExecutionException("Given " + Arrays.toString(args));
			}
			String value = args[0];

			results.add(toDouble(value, register));
			System.out.println(results);

			return NO_RETURN_VALUE;
		}

		@Override
		public void onParse(final String[] args, final Parser parser, final int lineNumber) {
			if (args.length != 1) {
				parser.error(getFunctionName() + " requires on parameter, " + args.length + " given.");
			}
		}

		@Override
		public String hintReturnValue() {
			return NO_RETURN_VALUE;
		}
	}
}
