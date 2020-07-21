package com.revature.controllers;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class RequestHelper {

	public boolean isLoggedOut(HttpServletRequest req, HttpServletResponse res) throws IOException {
		// Cannot Do Action Because User is not logged in
		HttpSession ses = req.getSession(false);
		if (ses == null) {
			
			return true;
		}

		return false;
	}

	public String readBody(HttpServletRequest req, HttpServletResponse res) throws IOException {

		// Reading Body of Request into string
		BufferedReader reader = req.getReader();
		StringBuilder s = new StringBuilder();
		String line = reader.readLine();
		while (line != null) {
			s.append(line);
			line = reader.readLine();
		}
		String body = new String(s);
		return body;
	}
}
