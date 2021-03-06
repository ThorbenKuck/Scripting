package com.github.thorbenkuck.scripting.components;

import com.github.thorbenkuck.scripting.parsing.Parser;
import com.github.thorbenkuck.scripting.Register;
import com.github.thorbenkuck.scripting.VariableEvaluation;
import com.github.thorbenkuck.scripting.exceptions.RuntimeExecutionException;

public interface Function extends VariableEvaluation {

    String NO_RETURN_VALUE = "void";

    String getFunctionName();

    String calculate(String[] args, Register register) throws RuntimeExecutionException;

    default void onParse(String[] args, Parser parser, int lineNumber) {
    }

    default String hintReturnValue() {
        return "";
    }
}
