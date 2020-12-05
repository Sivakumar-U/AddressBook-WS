package com.blz.addressbook;

import java.time.LocalDate;
import java.util.List;

public class AddressBookService {

	public enum IOService {
		DB_IO, FILE_IO
	}

	private List<ContactData> addressBookList;
	private static AddressBookDBService addressBookDBService;

	public AddressBookService() {
		addressBookDBService = AddressBookDBService.getInstance();
	}

	public List<ContactData> readAddressBookData(IOService ioService) throws AddressBookException {
		if (ioService.equals(IOService.DB_IO))
			return this.addressBookList = addressBookDBService.readData();
		return this.addressBookList;
	}

	public void updateRecord(String firstname, String address) throws AddressBookException {
		int result = addressBookDBService.updateAddressBookData(firstname, address);
		if (result == 0)
			return;
		ContactData addressBookData = this.getAddressBookData(firstname);
		if (addressBookData != null)
			addressBookData.address = address;
	}

	public boolean checkUpdatedRecordSyncWithDB(String firstname) throws AddressBookException {
		try {
			List<ContactData> addressBookData = addressBookDBService.getAddressBookData(firstname);
			return addressBookData.get(0).equals(getAddressBookData(firstname));
		} catch (AddressBookException e) {
			throw new AddressBookException(e.getMessage(), AddressBookException.ExceptionType.DATABASE_EXCEPTION);
		}
	}

	private ContactData getAddressBookData(String firstname) {
		return this.addressBookList.stream().filter(addressBookItem -> addressBookItem.firstName.equals(firstname))
				.findFirst().orElse(null);
	}

	public List<ContactData> readAddressBookData(IOService ioService, String start, String end)
			throws AddressBookException {
		try {
			LocalDate startLocalDate = LocalDate.parse(start);
			LocalDate endLocalDate = LocalDate.parse(end);
			if (ioService.equals(IOService.DB_IO))
				return addressBookDBService.readData(startLocalDate, endLocalDate);
			return this.addressBookList;
		} catch (AddressBookException e) {
			throw new AddressBookException(e.getMessage(), AddressBookException.ExceptionType.DATABASE_EXCEPTION);
		}
	}

	public int readAddressBookData(String function, String city) throws AddressBookException {
		return addressBookDBService.readDataBasedOnCity(function, city);
	}
	
	public void addNewContact(String firstName, String lastName, String address, String city, String state, int zip,
			long phoneNo, String email) throws AddressBookException {
		addressBookList.add(addressBookDBService.addNewContact(firstName, lastName, address, city, state, zip, phoneNo,
				email));
	}


}
