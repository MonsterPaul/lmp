package com.zjht.jfmall.common.web.threadvariable;

import com.zjht.jfmall.entity.User;



public class MemberThread {
	private static ThreadLocal<User> instance = new ThreadLocal<User>();

	public static User get() {
		return instance.get();
	}

	public static void set(User member) {
		instance.set(member);
	}

	public static void remove() {
		instance.remove();
	}
}
