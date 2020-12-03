package com.blz.addressbook;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class AddressBookMain {

	static Map<String, AddressBookMain> addressBookObj = new HashMap<>();
	static AddressBookMain addressObj = new AddressBookMain();
	static String addressBookName;

	public static void addMultipleAddressBook() {
		Scanner input = new Scanner(System.in);
		System.out
				.println("Enter choice \n1.Create new addressbook \n2.Adding contacts in existing register \n3.Exit ");
		int entry = input.nextInt();
		if (entry != 3) {
			switch (entry) {
			case 1:
				Scanner input1 = new Scanner(System.in);
				System.out.println(" Enter name of address book ");
				String nameOfNewBook = input1.nextLine();
				if (addressBookObj.containsKey(nameOfNewBook)) {
					System.out.println(" Given address book name already exists");
					break;
				}
				addressBookObj.put(nameOfNewBook, addressObj);
				System.out.println(" Given address book " + " " + nameOfNewBook + " " + "has been added");
				AddressBookMain.options();
				break;
			case 2:
				Scanner existingAddressName = new Scanner(System.in);
				System.out.println(" Enter name of address book ");
				String nameOfExistingRegister = existingAddressName.nextLine();
				if (addressBookObj.containsKey(nameOfExistingRegister)) {
					addressBookObj.get(nameOfExistingRegister);
					AddressBookMain.options();
				} else
					System.out.println(" address book is not found ");
			case 3:
				entry = 3;
				break;
			default:
				System.out.println(" Enter valid input ");
				break;
			}
		}
	}

	static void options() {
		AddressBook addressBook = new AddressBook();
		Scanner sc = new Scanner(System.in);
		int flag = 1;
		while (flag == 1) {
			System.out.println("Welcome to address book program ");
			System.out.println(
					"Choose options: \n1.AddContact \n2.EditContact \n3.DeleteContact \n4.SearchPersonInCity \n5.ViewPersonByCity \n6.CountContactByCity \n7.SortByName \n8.SortByState \n9.AddDataToFile \n10.ReadDataFromFile \n11.Exit to main menu  ");
			int choice = sc.nextInt();
			switch (choice) {
			case 1:
				addressBook.addContact(addressBookName);
				break;
			case 2:
				if (AddressBook.contact.isEmpty()) {
					System.out.println(" Address book is empty ");
					break;
				}
				addressBook.editContact();
				break;
			case 3:
				if (AddressBook.contact.isEmpty()) {
					System.out.println(" Address book is empty ");
					break;
				}
				addressBook.deleteContact();
				break;
			case 4:
				if (AddressBook.contact.isEmpty()) {
					System.out.println("Address book is empty ");
					break;
				}
				addressBook.searchPersonByCity();
				break;
			case 5:
				if (AddressBook.contact.isEmpty()) {
					System.out.println("Address book is empty ");
					break;
				}
				addressBook.viewContactByCity();
				break;
			case 6:
				if (AddressBook.contact.isEmpty()) {
					System.out.println("Address book is empty ");
					break;
				}
				addressBook.countContactByCity();
				break;
			case 7:
				if (AddressBook.contact.isEmpty()) {
					System.out.println("Address book is empty ");
					break;
				}
				addressBook.sortByName();
				break;
			case 8:
				if (AddressBook.contact.isEmpty()) {
					System.out.println("Address book is empty ");
					break;
				}
				addressBook.sortByState();
				break;
			case 9:
				addressBook.addContact(addressBookName);
				System.out.println("Successfully Added to text file");
				break;
			case 10:
				addressBook.readDataFromFile();
				break;
			case 11:
				addMultipleAddressBook();
				break;
			default:
				System.out.println("Invalid Choice");
			}
		}
	}

	public static void main(String[] args) {
		addMultipleAddressBook();
	}

}
