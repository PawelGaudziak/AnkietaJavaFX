package pl.pwn.reaktor.ankieta.model;

public class AnkietaFilter {

	private String mail;
	private String java;
	private String language;

	public AnkietaFilter(String mail, String java, String language) {
		super();
		this.mail = mail;
		this.java = java;
		this.language = language;
	}
//gettery i settery
	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getJava() {
		return java;
	}

	public void setJava(String java) {
		this.java = java;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

}
