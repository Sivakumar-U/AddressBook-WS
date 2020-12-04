package com.blz.addressbook;

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

}
