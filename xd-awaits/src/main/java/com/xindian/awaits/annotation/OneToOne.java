package com.xindian.awaits.annotation;

public @interface OneToOne
{
	boolean optional() default true;
	// cascade = CascadeType.ALL, fetch = FetchType.LAZY)
}
