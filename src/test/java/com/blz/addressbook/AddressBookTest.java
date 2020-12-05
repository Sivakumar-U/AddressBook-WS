package com.blz.addressbook;

import static org.junit.Assert.assertEquals;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.blz.addressbook.AddressBookService.IOService;
import com.google.gson.Gson;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class AddressBookTest {

	private static final IOService REST_IO = null;
	private static AddressBookService addressBook;

	@BeforeClass
	public static void createAddressBookObj() {
		addressBook = new AddressBookService();
		System.out.println("Welcome to the Address Book System.. ");
	}

	@AfterClass
	public static void nullObj() {
		addressBook = null;
	}

	@Before
	public void setup() {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = 3000;
	}

	@Test
	public void givenAddressBook_WhenRetrieved_ShouldMatchCount() throws AddressBookException, SQLException {
		List<ContactData> data = addressBook.readAddressBookData(IOService.DB_IO);
		Assert.assertEquals(6, data.size());
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

	@Test
	public void givenAddresBookDetails_WhenAdded_ShouldSyncWithDB() throws AddressBookException {
		addressBook.readAddressBookData(IOService.DB_IO);
		addressBook.addNewContact("Prudhvi", "Dandi", "Muchivolu", "Tirupati", "AP", 517586, 789654120,
				"prudhvi@gmail.com");
		boolean result = addressBook.checkUpdatedRecordSyncWithDB("Prudhvi");
		Assert.assertTrue(result);
	}

	@Test
	public void givenMultipleContact_WhenAdded_ShouldSyncWithDB() throws AddressBookException {
		ContactData[] contactArray = {
				new ContactData("Tharun", "Pulluru", "Mcl", "Tirupati", "AP", 517536, 897565874, "tharun@gmail.com"),
				new ContactData("Nani", "Kalthireddy", "Mr palli", "Tirupati", "AP", 517533, 987456320,
						"nani@gmail.com") };
		addressBook.addMultipleContactsToDBUsingThreads(Arrays.asList(contactArray));
		boolean result1 = addressBook.checkUpdatedRecordSyncWithDB("Tharun");
		boolean result2 = addressBook.checkUpdatedRecordSyncWithDB("Nani");
		Assert.assertTrue(result1);
		Assert.assertTrue(result2);

	}

	private ContactData[] getAddressbookList() {
		Response response = RestAssured.get("/addressBookWS");
		System.out.println("Adddressbook entries in JsonServer :\n" + response.asString());
		ContactData[] arrayOfPerson = new Gson().fromJson(response.asString(), ContactData[].class);
		return arrayOfPerson;
	}

	private Response addContactToJsonServer(ContactData addressbookData) {
		String contactJson = new Gson().toJson(addressbookData);
		RequestSpecification request = RestAssured.given();
		request.header("Content-Type", "application/json");
		request.body(contactJson);
		return request.post("/addressBookWS");
	}

	@Test
	public void givenAddressbookDataInJsonServer_WhenRetrieved_ShouldMatchCount() {
		ContactData[] arrayOfPerson = getAddressbookList();
		addressBook = new AddressBookService(Arrays.asList(arrayOfPerson));
		long entries = addressBook.countEntries(REST_IO);
		assertEquals(2, entries);
	}

	@Test
	public void givenMultiplePerson_WhenAdded_ShouldMatch201ResponseAndCount() {
		ContactData[] arrayOfContacts = getAddressbookList();
		addressBook = new AddressBookService(Arrays.asList(arrayOfContacts));
		ContactData[] arrayOfPerson = {
				new ContactData(0, "Yuvan", "Nagireddy", "Kothuru", "Nellore", "AP", 517548, 987654421,
						"yuva@gmail.com"),
				new ContactData(0, "Narendra", "Konduru", "Mangalagiri", "Guntur", "AP", 547896, 987754321,
						"nari@gmail.com"),
				new ContactData(0, "Sai", "Mallela", "Ecospace", "Bangalore", "KA", 510016, 987954321,
						"sai@gmail.com") };

		for (ContactData addressbookData : arrayOfPerson) {

			Response response = addContactToJsonServer(addressbookData);
			int statusCode = response.getStatusCode();
			assertEquals(201, statusCode);

			addressbookData = new Gson().fromJson(response.asString(), ContactData.class);
			addressBook.addContactToAddressbook(addressbookData, REST_IO);
		}
		long entries = addressBook.countEntries(REST_IO);
		assertEquals(5, entries);
	}

}
