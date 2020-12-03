package com.blz.addressbook;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AddressBook {

	Scanner sc = new Scanner(System.in);

	static List<ContactDetails> contact = new ArrayList<ContactDetails>();

	public void addContact() {
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
		ContactDetails obj = new ContactDetails(firstName, lastName, address, city, state, zip, phoneNo, email);
		contact.add(obj);
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

}
