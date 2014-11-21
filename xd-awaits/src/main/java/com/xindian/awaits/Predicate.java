package com.xindian.awaits;

import java.util.List;

public interface Predicate
{
	enum BooleanOperator
	{
		AND, OR
	}

	List<Expression<Boolean>> getExpressions();

	BooleanOperator getOperator();

	boolean isNegated();

	Predicate not();

}
