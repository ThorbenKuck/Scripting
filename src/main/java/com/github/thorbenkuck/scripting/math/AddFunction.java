package com.github.thorbenkuck.scripting.math;

import com.github.thorbenkuck.scripting.components.Function;
import com.github.thorbenkuck.scripting.parsing.Parser;
import com.github.thorbenkuck.scripting.Register;

import java.util.Arrays;

public class AddFunction implements Function {
	@Override
	public String getFunctionName() {
		return "add";
	}

	private String calculateDoubles(final String[] args, Register register) {
		double count = 0;
		for (String arg : args) {
			if (isDouble(arg, register)) {
				count += toDouble(arg, register);
			} else {
				System.out.println("Unknown type provided to add: " + arg);
			}
		}
		return String.valueOf(count);
	}

	@Override
	public String calculate(final String[] args, final Register register) {
		boolean doubleValueContained = false;
		for (String arg : args) {
			if (isDouble(arg, register)) {
				doubleValueContained = true;
				break;
			}
		}

		if (doubleValueContained) {
			return calculateDoubles(args, register);
		} else {
			return calculateInt(args, register);
		}

	}

	private String calculateInt(final String[] args, final Register register) {
		int count = 0;
		for (String arg : args) {
			if (isInteger(arg, register)) {
				count += toInt(arg, register);
			} else {
				System.out.println("Unknown type provided to add: " + arg);
			}
		}
		return String.valueOf(count);
	}

	@Override
	public void onParse(final String[] args, final Parser parser, final int lineNumber) {
		if (args.length <= 1) {
			parser.error("at least 2 numbers have to be provided.. Provided: " + Arrays.toString(args), lineNumber);
		}
	}
}
