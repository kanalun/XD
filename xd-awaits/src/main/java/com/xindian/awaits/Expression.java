package com.xindian.awaits;

import java.util.Collection;

public interface Expression<T>
{
	<T> Expression<T> as(Class<T> type);

	Predicate in(Collection<?> values);

	Predicate in(Expression<?>... values);

	Predicate in(Expression<java.util.Collection<?>> values);

	Predicate in(java.lang.Object... values);

	Predicate isNotNull();

	Predicate isNull();
}
