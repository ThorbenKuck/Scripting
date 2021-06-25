package com.github.thorbenkuck.scripting.system;

import com.github.thorbenkuck.scripting.components.Function;
import com.github.thorbenkuck.scripting.Register;
import com.github.thorbenkuck.scripting.exceptions.RuntimeExecutionException;

public class RequireIsSetFunction implements Function {
	@Override
	public String getFunctionName() {
		return "require";
	}

	@Override
	public String calculate(String[] args, Register register) {
		for(String arg : args) {
			if(!register.has(arg)) {
				throw new RuntimeExecutionException("The value of " + arg + " is never set!");
			}
		}
		return NO_RETURN_VALUE;
	}

	@Override
	public String hintReturnValue() {
		return NO_RETURN_VALUE;
	}
}
