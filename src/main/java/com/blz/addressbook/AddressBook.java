package com.blz.addressbook;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class AddressBook {

	Scanner sc = new Scanner(System.in);

	static List<ContactDetails> contact = new ArrayList<ContactDetails>();
	ContactDetails newEntry;
	boolean isExist;

	public void addContact(String addressBookName) {
		isExist = false;
		System.out.println("Enter your firstName : ");
		String firstName = sc.nextLine();
		System.out.println("Enter your lastName : ");
		String lastName = sc.nextLine();
		System.out.println("Enter your address : ");
		String address = sc.nextLine();
		System.out.println("Enter your city : ");
		String city = sc.nextLine();
		System.out.println("Enter your state : ");
		String state = sc.nextLine();
		System.out.println("Enter your zipCode : ");
		int zip = sc.nextInt();
		System.out.println("Enter your phoneNo : ");
		long phoneNo = sc.nextLong();
		sc.nextLine();
		System.out.println("Enter your emailId : ");
		String email = sc.nextLine();
		if (contact.size() > 0) {
			for (ContactDetails details : contact) {
				newEntry = details;
				if (firstName.equals(newEntry.firstName) && lastName.equals(newEntry.lastName)) {
					System.out.println("Contact " + newEntry.firstName + " " + newEntry.lastName + " already exists");
					isExist = true;
					break;
				}
			}
		}
		if (!isExist) {
			newEntry = new ContactDetails(firstName, lastName, address, city, state, zip, phoneNo, email);
			contact.add(newEntry);
			addDataToFile(firstName, lastName, address, city, state, phoneNo, zip, email, addressBookName);
		}
		System.out.println(contact);
	}

	public void editContact() {
		System.out.println(" Enter the first name ");
		String fName = sc.nextLine();
		for (int i = 0; i < contact.size(); i++) {
			if (contact.get(i).getFirstName().equals(fName)) {
				System.out.println("index : " + contact.get(i));
				Scanner input = new Scanner(System.in);
				System.out.println(
						"Choose option to edit:\n 1.first name\n 2.last name\n 3.city\n 4.state\n 5.zip\n 6.phone\n 7.email ");
				int select = sc.nextInt();
				switch (select) {
				case 1:
					System.out.println(" Enter first name ");
					String firstName = input.nextLine();
					contact.get(i).setFirstName(firstName);
					break;
				case 2:
					System.out.println(" Enter last name ");
					String lastName = input.nextLine();
					contact.get(i).setLastName(lastName);
					break;
				case 3:
					System.out.println(" Enter city name ");
					String city = input.nextLine();
					contact.get(i).setCity(city);
					break;
				case 4:
					System.out.println(" Enter State ");
					String state = input.nextLine();
					contact.get(i).setState(state);
					break;
				case 5:
					System.out.println(" Enter pincode ");
					int zip = input.nextInt();
					contact.get(i).setZip(zip);
					break;
				case 6:
					System.out.println(" Enter Mobile number ");
					long phone = input.nextLong();
					contact.get(i).setPhoneNo(phone);
					break;
				case 7:
					System.out.println(" Enter Email id ");
					String email = input.nextLine();
					contact.get(i).setEmail(email);
					break;
				default:
					System.out.println(" Enter valid input ");
					break;
				}
			}
		}
		System.out.println(contact);
		System.out.println("*****Successfully edited****");
	}

	public void deleteContact() {
		for (int i = 0; i < contact.size(); i++) {
			System.out.println("Enter First Name : ");
			Scanner sc = new Scanner(System.in);
			String fname = sc.nextLine();
			if (contact.get(i).getFirstName().equalsIgnoreCase(fname)) {
				contact.remove(i);
			} else {
				System.out.println("Contact Not Found");
			}
		}
		System.out.println(contact);
		System.out.println("Contact deleted!");
	}

	public void searchPersonByCity() {
		System.out.println("Enter City Name : ");
		Scanner sc = new Scanner(System.in);
		String city = sc.nextLine();
		contact.stream().filter(n -> n.getCity().equals(city)).forEach(i -> System.out.println(i));
	}

	public void viewContactByCity() {
		System.out.println("Enter City Name : ");
		Scanner sc = new Scanner(System.in);
		String city = sc.nextLine();
		contact.stream().filter(n -> n.getCity().equals(city)).forEach(i -> System.out.println(i));
	}

	public void countContactByCity() {
		int count = 0;
		System.out.println("Enter City Name : ");
		Scanner sc = new Scanner(System.in);
		String city = sc.nextLine();
		count = (int) contact.stream().filter(n -> n.getCity().equals(city)).count();
		System.out.println(count);
	}

	public void sortByName() {
		contact = contact.stream().sorted(Comparator.comparing(ContactDetails::getFirstName))
				.collect(Collectors.toList());
		contact.forEach(i -> System.out.println(i));
	}

	public void sortByState() {
		contact = contact.stream().sorted(Comparator.comparing(ContactDetails::getState)).collect(Collectors.toList());
		contact.forEach(i -> System.out.println(i));
	}

	public void addDataToFile(String firstName, String lastName, String address, String city, String state,
			long phoneNumber, int zip, String email, String addressBookName) {
		System.out.println("Enter name for text file to add data: ");
		String fileName = sc.nextLine();
		File file = new File("D:\\" + fileName + ".txt");
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write("Contact:" + "\n1.First name: " + firstName + "\n2.Last name: " + lastName + "\n3.Address: "
					+ address + "\n4.City: " + city + "\n5.State: " + state + "\n6.Phone number: " + phoneNumber
					+ "\n7.Zip: " + zip + "\n8.email: " + email + "\n");
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void readDataFromFile() {
		System.out.println("Enter text filename to read data: ");
		String fileName = sc.nextLine();
		Path filePath = Paths.get("D:\\" + fileName + ".txt");
		try {
			Files.lines(filePath).map(line -> line.trim()).forEach(line -> System.out.println(line));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
