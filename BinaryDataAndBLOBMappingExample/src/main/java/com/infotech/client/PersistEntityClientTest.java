package com.infotech.client;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.infotech.entities.Person;
import org.hibernate.Session;
import org.hibernate.engine.jdbc.BlobProxy;


import com.infotech.util.HibernateUtil;

public class PersistEntityClientTest {

	public static void main(String[] args) {

		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

			Person author1 = new Person();

			session.doWork(conn -> {
				author1.setProfilePic(BlobProxy.generateProxy(getImage()));
			});
			session.beginTransaction();
			session.save(author1);

			session.getTransaction().commit();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static byte[] getImage() {
		Path path = Paths.get("inputProfilePics/gavin.JPG");
		byte[] data = null;
		try {
			data = Files.readAllBytes(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return data;
	}
}