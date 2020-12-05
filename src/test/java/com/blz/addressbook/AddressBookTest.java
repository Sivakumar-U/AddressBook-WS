package com.blz.addressbook;

import java.sql.SQLException;
import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.blz.addressbook.AddressBookService.IOService;

public class AddressBookTest {

	private static AddressBookService addressBook;

	@BeforeClass
	public static void createAddressBookObj() {
		addressBook = new AddressBookService();
		System.out.println("Welcome to the Address Book System.. ");
	}

	@Test
	public void givenAddressBook_WhenRetrieved_ShouldMatchCount() throws AddressBookException, SQLException {
		List<ContactData> data = addressBook.readAddressBookData(IOService.DB_IO);
		Assert.assertEquals(2, data.size());
	}

	@Test
	public void givenAddressBook_WhenUpdate_ShouldSyncWithDB() throws AddressBookException {
		List<ContactData> contactDetails = addressBook.readAddressBookData(IOService.DB_IO);
		addressBook.updateRecord("Raja", "Paravolu");
		boolean result = addressBook.checkUpdatedRecordSyncWithDB("Raja");
		Assert.assertTrue(result);
	}

	@Test
	public void givenAddressBook_WhenRetrieved_ShouldMatchCountInGivenRange() throws AddressBookException {
		List<ContactData> addressBookData = addressBook.readAddressBookData(IOService.DB_IO, "2018-02-14",
				"2020-06-02");
		Assert.assertEquals(1, addressBookData.size());
	}

	@Test
	public void givenAddresBook_WhenRetrieved_ShouldReturnCountOfCity() throws AddressBookException {
		Assert.assertEquals(2, addressBook.readAddressBookData("count", "Nellore"));
	}

}
