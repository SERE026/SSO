package com.bs.web.util;

import java.util.UUID;

public class TokenUtils {
	/**
	 * ticket if not exist create 
	 * @param tkey
	 * @return
	 */
	public static String createTicket(String tkey) {
		if (tkey == null || "".equals(tkey))
			return UUID.randomUUID().toString();
		else
			return tkey;
	}

}
