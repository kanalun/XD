package com.xindian.awaits;

import com.xindian.awaits.Predicate.BooleanOperator;

public interface Selection
{
	String getName();

	BooleanOperator getOperator();

	boolean isNegated();

	Predicate not();
}
