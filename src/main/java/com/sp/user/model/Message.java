package com.sp.user.model;

public class Message {
	
	String mensaje;
	
	

	public Message() {
	}
	
	public Message(String msg) {
		this.mensaje = msg;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	@Override
	public String toString() {
		return "Message [mensaje=" + mensaje + "]";
	}

}
