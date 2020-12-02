package com.weblearn.bytebuddy.delegating;

public interface Forwarder<T, S> {

	T to(S target);
}
